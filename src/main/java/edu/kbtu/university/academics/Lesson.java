package edu.kbtu.university.academics;

import java.io.*;
import java.time.*;
import java.util.*;

import edu.kbtu.university.enums.LessonType;
import edu.kbtu.university.users.Teacher;

/**
 * 
 */
public class Lesson {

    /**
     * Default constructor
     */
    public Lesson() {
    }

    /**
     * 
     */
    private LessonType type;

    /**
     * 
     */
    private String room;

    /**
     * 
     */
    private DayOfWeek day;

    /**
     * 
     */
    private LocalTime startTime;

    /**
     * 
     */
    private int durationMinutes;

    /**
     * 
     */
    private Teacher instructor;

    /**
     * Returns the teacher conducting this lesson.
     */
    public Teacher getInstructor() {
        // TODO implement here
        return null;
    }

    /**
     * Returns the duration of this lesson in minutes.
     */
    public int getDurationMinutes() {
        // TODO implement here
        return 0;
    }

    /**
     * Returns the start time of this lesson.
     */
    public LocalTime getStartTime() {
        // TODO implement here
        return null;
    }

    /**
     * Returns the computed end time (startTime + durationMinutes).
     */
    public LocalTime getEndTime() {
        // TODO implement here
        return null;
    }

    /**
     * Checks whether this lesson conflicts with another lesson.
     * Two lessons conflict if they overlap in time AND share the same room
     * OR share the same instructor.
     */
    public boolean conflictsWith(Lesson other) {
        // TODO implement here
        return false;
    }

    @Override
    public String toString() {
        // TODO implement here
        return super.toString();
    }

}