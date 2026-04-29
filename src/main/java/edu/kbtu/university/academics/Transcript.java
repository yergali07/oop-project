package edu.kbtu.university.academics;

import java.io.*;
import java.util.*;

import edu.kbtu.university.users.Student;

/**
 * 
 */
public class Transcript {

    /**
     * Default constructor
     */
    public Transcript() {
    }

    /**
     * 
     */
    private Student student;

    /**
     * 
     */
    private Map<Course, Mark> marks;

    /**
     * 
     */
    private double gpa;

    /**
     * 
     */
    private int totalCredits;

    /**
     * @return
     */
    public double calculateGPA() {
        // TODO implement here
        return 0.0d;
    }

    /**
     * @param c
     * @param m
     */
    public void addMark(Course c, Mark m) {
        // TODO implement here
    }

    /**
     * @return
     */
    public String generate() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public List<Course> getFailedCourses() {
        // TODO implement here
        return null;
    }

}