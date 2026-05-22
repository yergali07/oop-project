package edu.kbtu.university.academics;

import java.time.DayOfWeek;
import java.time.LocalTime;

import edu.kbtu.university.enums.LessonType;
import edu.kbtu.university.users.Teacher;

/**
 * Represents one scheduled lesson for an academic course.
 * Stores lesson type, room, day, start time, duration and instructor.
 */
public class Lesson {
    private LessonType type;
    private String room;
    private DayOfWeek day;
    private LocalTime startTime;
    private int durationMinutes;
    private Teacher instructor;

    /**
     * Creates an empty lesson.
     */
    public Lesson() {
    }

    /**
     * Creates a lesson with all scheduling fields.
     *
     * @param type lesson type
     * @param room classroom or room name
     * @param day day of week
     * @param startTime lesson start time
     * @param durationMinutes lesson duration in minutes
     * @param instructor teacher conducting the lesson
     */
    public Lesson(LessonType type, String room, DayOfWeek day, LocalTime startTime,
                  int durationMinutes, Teacher instructor) {
        this.type = type;
        this.room = room;
        this.day = day;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.instructor = instructor;
    }

    /**
     * Returns the lesson type.
     *
     * @return lesson type
     */
    public LessonType getType() {
        return type;
    }

    /**
     * Sets the lesson type.
     *
     * @param type lesson type
     */
    public void setType(LessonType type) {
        this.type = type;
    }

    /**
     * Returns the lesson room.
     *
     * @return lesson room
     */
    public String getRoom() {
        return room;
    }

    /**
     * Sets the lesson room.
     *
     * @param room lesson room
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Returns the lesson day.
     *
     * @return day of week
     */
    public DayOfWeek getDay() {
        return day;
    }

    /**
     * Sets the lesson day.
     *
     * @param day day of week
     */
    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    /**
     * Returns the lesson start time.
     *
     * @return start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the lesson start time.
     *
     * @param startTime start time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the lesson duration in minutes.
     *
     * @return duration in minutes
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * Sets the lesson duration in minutes.
     *
     * @param durationMinutes duration in minutes
     */
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    /**
     * Returns the instructor assigned to the lesson.
     *
     * @return lesson instructor
     */
    public Teacher getInstructor() {
        return instructor;
    }

    /**
     * Sets the instructor assigned to the lesson.
     *
     * @param instructor lesson instructor
     */
    public void setInstructor(Teacher instructor) {
        this.instructor = instructor;
    }

    /**
     * Calculates the lesson end time from start time and duration.
     *
     * @return lesson end time
     */
    public LocalTime getEndTime() {
        return startTime.plusMinutes(durationMinutes);
    }

    /**
     * Checks whether this lesson conflicts with another lesson.
     * A conflict exists when both lessons are on the same day, in the same room,
     * and their time intervals overlap.
     *
     * @param other lesson to compare with
     * @return {@code true} if lessons conflict; otherwise {@code false}
     */
    public boolean conflictsWith(Lesson other) {
        return this.day == other.day
                && this.room.equals(other.room)
                && this.startTime.isBefore(other.getEndTime())
                && other.getStartTime().isBefore(this.getEndTime());
    }

    /**
     * Returns a text representation of the lesson.
     *
     * @return lesson description
     */
    @Override
    public String toString() {
        return "";
    }
}
