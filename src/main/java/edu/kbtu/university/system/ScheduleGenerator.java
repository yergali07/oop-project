package edu.kbtu.university.system;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.kbtu.university.academics.Lesson;
import edu.kbtu.university.enums.LessonType;
import edu.kbtu.university.enums.RoomType;

/**
 * Generates a weekly timetable for a batch of {@link Lesson} objects.
 * Bonus feature.
 *
 * <p>The caller supplies a pool of rooms (room name → {@link RoomType}); the
 * generator picks for each lesson a compatible room and an
 * {@code (day, startTime)} slot such that:
 * <ul>
 *   <li>no two lessons share the same room at overlapping times,</li>
 *   <li>no instructor is double-booked,</li>
 *   <li>{@link LessonType#LECTURE} lessons are placed in
 *       {@link RoomType#LECTURE_HALL} only, while
 *       {@link LessonType#PRACTICE} lessons accept {@link RoomType#LAB} or
 *       {@link RoomType#SEMINAR_ROOM}.</li>
 * </ul>
 *
 * <p>Search order: Mon–Fri, 09:00 → 17:00, 90-minute slots. The first
 * lesson that cannot be placed throws {@link ScheduleConflictException}.
 */
public final class ScheduleGenerator {

    /** Thrown when no conflict-free slot exists for some lesson. */
    public static class ScheduleConflictException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public ScheduleConflictException(String message) { super(message); }
    }

    private static final DayOfWeek[] DAYS = {
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
    };
    private static final LocalTime EARLIEST = LocalTime.of(9, 0);
    private static final LocalTime LATEST = LocalTime.of(17, 0);
    private static final int SLOT_MINUTES = 90;

    /**
     * Builds a timetable by assigning room, day, and start time to each
     * lesson in {@code lessons}. The lessons are mutated in place.
     *
     * @param lessons lessons to schedule (each must have a non-{@code null}
     *                {@link LessonType}, instructor and duration)
     * @param rooms   pool of available rooms (name → {@link RoomType});
     *                must be non-empty
     * @return the scheduled lessons (the same list, returned for chaining)
     * @throws ScheduleConflictException if any lesson cannot be placed
     * @throws IllegalArgumentException  on {@code null} / empty input
     */
    public List<Lesson> generate(List<Lesson> lessons, Map<String, RoomType> rooms) {
        if (lessons == null || rooms == null || rooms.isEmpty()) {
            throw new IllegalArgumentException("Lessons and rooms must be supplied");
        }
        List<Lesson> placed = new ArrayList<>();
        for (Lesson lesson : lessons) {
            place(lesson, rooms, placed);
            placed.add(lesson);
        }
        return lessons;
    }

    private void place(Lesson lesson, Map<String, RoomType> rooms, List<Lesson> placed) {
        if (lesson.getType() == null) {
            throw new IllegalArgumentException("Lesson type must be set");
        }
        if (lesson.getDurationMinutes() <= 0) {
            lesson.setDurationMinutes(SLOT_MINUTES);
        }

        for (Map.Entry<String, RoomType> roomEntry : rooms.entrySet()) {
            if (!compatible(lesson.getType(), roomEntry.getValue())) continue;

            for (DayOfWeek day : DAYS) {
                LocalTime start = EARLIEST;
                while (!start.plusMinutes(lesson.getDurationMinutes()).isAfter(LATEST)) {
                    lesson.setRoom(roomEntry.getKey());
                    lesson.setRoomType(roomEntry.getValue());
                    lesson.setDay(day);
                    lesson.setStartTime(start);
                    if (!hasConflict(lesson, placed)) {
                        return;
                    }
                    start = start.plusMinutes(SLOT_MINUTES);
                }
            }
        }
        lesson.setRoom(null);
        lesson.setDay(null);
        lesson.setStartTime(null);
        throw new ScheduleConflictException(
                "Could not schedule lesson: " + lesson.getType()
                + " (" + lesson.getDurationMinutes() + " min)");
    }

    private boolean compatible(LessonType lessonType, RoomType roomType) {
        if (lessonType == LessonType.LECTURE) return roomType == RoomType.LECTURE_HALL;
        return roomType == RoomType.LAB || roomType == RoomType.SEMINAR_ROOM;
    }

    private boolean hasConflict(Lesson candidate, List<Lesson> placed) {
        for (Lesson p : placed) {
            if (candidate.conflictsWith(p)) return true;
            if (candidate.getInstructor() != null
                    && candidate.getInstructor().equals(p.getInstructor())
                    && candidate.getDay() == p.getDay()
                    && candidate.getStartTime().isBefore(p.getEndTime())
                    && p.getStartTime().isBefore(candidate.getEndTime())) {
                return true;
            }
        }
        return false;
    }
}
