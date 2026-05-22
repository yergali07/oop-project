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
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public Course() {
        this.instructors = new ArrayList<>();
        this.enrolled = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
        this.lessons = new ArrayList<>();
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
        if (t == null) return;
        if (instructors == null) instructors = new ArrayList<>();
        if (!instructors.contains(t)) instructors.add(t);
    }

    /**
     * @param s
     * @return
     */
    public boolean hasPrerequisitesMet(Student s) {
        return prerequisites == null || prerequisites.isEmpty();
    }

    /**
     * @return
     */
    public boolean isFull() {
        return maxStudents > 0 && enrolled != null && enrolled.size() >= maxStudents;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public StudentYear getIntendedYear() { return intendedYear; }
    public void setIntendedYear(StudentYear intendedYear) { this.intendedYear = intendedYear; }

    public Major getIntendedMajor() { return intendedMajor; }
    public void setIntendedMajor(Major intendedMajor) { this.intendedMajor = intendedMajor; }

    public Semester getSemester() { return semester; }
    public void setSemester(Semester semester) { this.semester = semester; }

    public List<Teacher> getInstructors() {
        if (instructors == null) instructors = new ArrayList<>();
        return instructors;
    }

    public List<Student> getEnrolled() {
        if (enrolled == null) enrolled = new ArrayList<>();
        return enrolled;
    }

    public List<Course> getPrerequisites() {
        if (prerequisites == null) prerequisites = new ArrayList<>();
        return prerequisites;
    }

    public List<Lesson> getLessons() {
        if (lessons == null) lessons = new ArrayList<>();
        return lessons;
    }

    public int getMaxStudents() { return maxStudents; }
    public void setMaxStudents(int maxStudents) { this.maxStudents = maxStudents; }

    @Override
    public String toString() {
        return name == null ? String.valueOf(id) : name + " (" + id + ")";
    }

}
