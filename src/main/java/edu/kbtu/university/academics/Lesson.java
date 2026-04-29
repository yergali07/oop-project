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

}