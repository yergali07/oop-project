package edu.kbtu.university.academics;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.kbtu.university.users.Student;

/**
 * Represents an academic transcript for a student.
 * Stores course marks, calculated GPA and total completed credits.
 */
public class Transcript implements Serializable {

    private static final long serialVersionUID = 1L;

    private Student student;
    private Map<Course, Mark> marks;
    private double gpa;
    private int totalCredits;

    /**
     * Creates an empty transcript with an initialized mark map.
     */
    public Transcript() {
        this.marks = new HashMap<>();
    }

    /**
     * Creates a transcript with all academic fields.
     *
     * @param student student associated with the transcript
     * @param marks course-to-mark map
     * @param gpa current GPA value
     * @param totalCredits total number of credits in the transcript
     */
    public Transcript(Student student, Map<Course, Mark> marks, double gpa, int totalCredits) {
        this.student = student;
        this.marks = marks != null ? marks : new HashMap<>();
        this.gpa = gpa;
        this.totalCredits = totalCredits;
    }

    /**
     * Returns the student associated with the transcript.
     *
     * @return transcript owner
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student associated with the transcript.
     *
     * @param student transcript owner
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Returns the map of course marks.
     *
     * @return map where keys are courses and values are marks
     */
    public Map<Course, Mark> getMarks() {
        return marks;
    }

    /**
     * Sets the map of course marks.
     *
     * @param marks map where keys are courses and values are marks
     */
    public void setMarks(Map<Course, Mark> marks) {
        this.marks = marks != null ? marks : new HashMap<>();
    }

    /**
     * Returns the calculated GPA.
     *
     * @return GPA value
     */
    public double getGpa() {
        return gpa;
    }

    /**
     * Sets the GPA value.
     *
     * @param gpa GPA value
     */
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    /**
     * Returns the total number of credits in the transcript.
     *
     * @return total credits
     */
    public int getTotalCredits() {
        return totalCredits;
    }

    /**
     * Sets the total number of credits in the transcript.
     *
     * @param totalCredits total credits
     */
    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    /**
     * Calculates the GPA as a weighted average by course credits.
     * Failed courses are included with 0.0 GPA points.
     *
     * @return calculated GPA value, or 0.0 if there are no marks or credits
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
     * Adds or updates a mark for a course in the transcript.
     * After insertion, recalculates total credits and GPA and updates
     * the student's failed course list for non-passing marks.
     *
     * @param c course for which the mark is added
     * @param m mark assigned for the course
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
     * Generates a textual representation of the transcript.
     *
     * @return generated transcript text
     */
    public String generate() {
        // TODO implement here
        return "";
    }

    /**
     * Returns all courses with non-passing marks.
     *
     * @return list of failed courses
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
