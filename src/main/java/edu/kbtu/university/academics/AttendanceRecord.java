package edu.kbtu.university.academics;

import java.io.Serializable;
import java.time.LocalDate;

import edu.kbtu.university.users.Student;

/**
 * Single attendance entry: did the given {@link Student} show up to the
 * given {@link Course} on the given date? Created by
 * {@link edu.kbtu.university.users.Teacher#markAttendance} and aggregated
 * via {@link edu.kbtu.university.users.Student#getAttendanceRate}.
 */
public class AttendanceRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private Student student;
    private Course course;
    private LocalDate date;
    private boolean present;

    /** Default constructor (used by serialization). */
    public AttendanceRecord() {
    }

    /**
     * Full-state constructor.
     *
     * @param student the student
     * @param course  the course
     * @param date    the date of the lesson
     * @param present whether the student was present
     */
    public AttendanceRecord(Student student, Course course, LocalDate date, boolean present) {
        this.student = student;
        this.course = course;
        this.date = date;
        this.present = present;
    }

    /** @return the student */
    public Student getStudent() { return student; }

    /** @param student the student */
    public void setStudent(Student student) { this.student = student; }

    /** @return the course */
    public Course getCourse() { return course; }

    /** @param course the course */
    public void setCourse(Course course) { this.course = course; }

    /** @return the date of the lesson */
    public LocalDate getDate() { return date; }

    /** @param date the date of the lesson */
    public void setDate(LocalDate date) { this.date = date; }

    /** @return {@code true} if the student was present */
    public boolean isPresent() { return present; }

    /** @param present whether the student was present */
    public void setPresent(boolean present) { this.present = present; }

    @Override
    public String toString() {
        return "Attendance{" + (student == null ? "?" : student.getId())
                + " in " + (course == null ? "?" : course.getId())
                + " on " + date + (present ? " present" : " absent") + "}";
    }
}
