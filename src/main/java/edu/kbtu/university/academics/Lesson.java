package edu.kbtu.university.academics;

import java.time.DayOfWeek;
import java.time.LocalTime;

import edu.kbtu.university.enums.LessonType;
import edu.kbtu.university.users.Teacher;

public class Lesson {
    private LessonType type;
    private String room;
    private DayOfWeek day;
    private LocalTime startTime;
    private int durationMinutes;
    private Teacher instructor;

    public Lesson() {
    }

    public Lesson(LessonType type, String room, DayOfWeek day, LocalTime startTime,
                  int durationMinutes, Teacher instructor) {
        this.type = type;
        this.room = room;
        this.day = day;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.instructor = instructor;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Teacher getInstructor() {
        return instructor;
    }

    public void setInstructor(Teacher instructor) {
        this.instructor = instructor;
    }

    public LocalTime getEndTime() {
        return startTime.plusMinutes(durationMinutes);
    }

    public boolean conflictsWith(Lesson other) {
        return this.day == other.day
                && this.room.equals(other.room)
                && this.startTime.isBefore(other.getEndTime())
                && other.getStartTime().isBefore(this.getEndTime());
    }

    @Override
    public String toString() {
        return "";
    }
}
