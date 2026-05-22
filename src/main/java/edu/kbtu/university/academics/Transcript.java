package edu.kbtu.university.academics;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.kbtu.university.users.Student;

public class Transcript {
    private Student student;
    private Map<Course, Mark> marks;
    private double gpa;
    private int totalCredits;

    public Transcript() {
        this.marks = new HashMap<>();
    }

    public Transcript(Student student, Map<Course, Mark> marks, double gpa, int totalCredits) {
        this.student = student;
        this.marks = marks != null ? marks : new HashMap<>();
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
        this.marks = marks != null ? marks : new HashMap<>();
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
            totalCredits = 0;
            return gpa;
        }

        double totalPoints = 0.0;
        int creditsSum = 0;

        for (Map.Entry<Course, Mark> entry : marks.entrySet()) {
            Course course = entry.getKey();
            Mark mark = entry.getValue();

            if (course == null || mark == null) {
                continue;
            }

            totalPoints += mark.getGpaPoints() * course.getCredits();
            creditsSum += course.getCredits();
        }

        totalCredits = creditsSum;

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
        if (c == null || m == null) {
            return;
        }

        marks.put(c, m);
        calculateGPA();

        if (!m.isPassing() && student != null && !student.getFailedCourses().contains(c)) {
            student.getFailedCourses().add(c);
        }
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
        List<Course> failed = new ArrayList<>();

        if (marks == null || marks.isEmpty()) {
            return failed;
        }

        for (Map.Entry<Course, Mark> entry : marks.entrySet()) {
            Course course = entry.getKey();
            Mark mark = entry.getValue();

            if (course != null && mark != null && !mark.isPassing()) {
                failed.add(course);
            }
        }

        return failed;
    }

}
