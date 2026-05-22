package edu.kbtu.university.academics;

import java.util.List;
import java.util.Map;

import edu.kbtu.university.users.Student;

public class Transcript {
    private Student student;
    private Map<Course, Mark> marks;
    private double gpa;
    private int totalCredits;

    public Transcript() {
    }

    public Transcript(Student student, Map<Course, Mark> marks, double gpa, int totalCredits) {
        this.student = student;
        this.marks = marks;
        this.gpa = gpa;
        this.totalCredits = totalCredits;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Map<Course, Mark> getMarks() {
        return marks;
    }

    public void setMarks(Map<Course, Mark> marks) {
        this.marks = marks;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    /**
     * @return
     */
    public double calculateGPA() {
        if (marks == null || marks.isEmpty()) {
            gpa = 0.0;
            return gpa;
        }

        double totalPoints = 0.0;
        int creditsSum = 0;

        for (Map.Entry<Course, Mark> entry : marks.entrySet()) {
            Course course = entry.getKey();
            Mark mark = entry.getValue();

            totalPoints += mark.getGpaPoints() * course.getCredits();
            creditsSum += course.getCredits();
        }

        if (creditsSum == 0) {
            gpa = 0.0;
            return gpa;
        }

        gpa = totalPoints / creditsSum;
        return gpa;
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
