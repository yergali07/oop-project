package edu.kbtu.university.academics;

import java.io.*;
import java.util.*;

import edu.kbtu.university.enums.Major;
import edu.kbtu.university.enums.Semester;
import edu.kbtu.university.enums.StudentYear;
import edu.kbtu.university.users.Student;
import edu.kbtu.university.users.Teacher;

/**
 * 
 */
public class Course {

    /**
     * Default constructor
     */
    public Course() {
    }

    /**
     * 
     */
    private String id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private int credits;

    /**
     * 
     */
    private StudentYear intendedYear;

    /**
     * 
     */
    private Major intendedMajor;

    /**
     * 
     */
    private Semester semester;

    /**
     * 
     */
    private List<Teacher> instructors;

    /**
     * 
     */
    private List<Student> enrolled;

    /**
     * 
     */
    private List<Course> prerequisites;

    /**
     * 
     */
    private List<Lesson> lessons;

    /**
     * 
     */
    private int maxStudents;

    /**
     * @param t
     */
    public void addInstructor(Teacher t) {
        // TODO implement here
    }

    /**
     * @param s
     * @return
     */
    public boolean hasPrerequisitesMet(Student s) {
        // TODO
        return false;
    }

    /**
     * @return
     */
    public boolean isFull() {
        // TODO implement here
        return false;
    }

}