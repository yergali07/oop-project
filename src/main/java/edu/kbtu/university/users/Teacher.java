package edu.kbtu.university.users;

import java.io.*;
import java.util.*;

import edu.kbtu.university.academics.Course;
import edu.kbtu.university.academics.Mark;
import edu.kbtu.university.enums.Role;
import edu.kbtu.university.enums.TeacherTitle;
import edu.kbtu.university.research.ResearchPaper;
import edu.kbtu.university.research.ResearchProfile;
import edu.kbtu.university.research.ResearchProject;

/**
 * Represents a teacher user in the university system.
 * In the academic module, the teacher manages courses and assigns marks.
 */
public class Teacher extends Employee implements Researcher {

    /**
     * Creates a teacher with an initialized course list.
     */
    public Teacher() {
        this.courses = new ArrayList<>();
    }

    /**
     * 
     */
    private TeacherTitle title;

    /**
     * 
     */
    private List<Course> courses;

    /**
     * 
     */
    private ResearchProfile profile;


    /**
     * Returns the courses assigned to the teacher.
     *
     * @return list of courses taught by the teacher
     */
    public List<Course> viewCourses() {
        // TODO implement here
        return null;
    }

    /**
     * Adds or manages a course assigned to the teacher.
     *
     * @param c course to manage
     */
    public void manageCourse(Course c) {
        // TODO implement here
    }

    /**
     * Assigns a mark to a student for a course if this teacher teaches the course.
     *
     * @param s student receiving the mark
     * @param c course for which the mark is assigned
     * @param m mark to assign
     * @throws IllegalArgumentException if any argument is null, the teacher does not teach
     *                                  the course, or the student transcript is unavailable
     */
    public void putMark(Student s, Course c, Mark m) {
        if (s == null || c == null || m == null) {
            throw new IllegalArgumentException("Student, course and mark must not be null");
        }

        boolean teachesCourse = courses.contains(c) || c.getInstructors().contains(this);
        if (!teachesCourse) {
            throw new IllegalArgumentException("Teacher does not teach this course");
        }

        if (s.getTranscript() == null) {
            throw new IllegalArgumentException("Student transcript is not available");
        }

        s.getTranscript().addMark(c, m);
    }

    /**
     * Returns students enrolled in a course visible to the teacher.
     *
     * @param c course whose students are requested
     * @return list of enrolled students
     */
    public List<Student> viewStudents(Course c) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public List<ResearchPaper> getPapers() {
        // TODO implement Researcher.getPapers() here
        return null;
    }

    /**
     * @return
     */
    public List<ResearchProject> getProjects() {
        // TODO implement Researcher.getProjects() here
        return null;
    }

    /**
     * @return
     */
    public int getHIndex() {
        // TODO implement Researcher.getHIndex() here
        return 0;
    }

    /**
     * @param p
     */
    public void publishPaper(ResearchPaper p) {
        // TODO implement Researcher.publishPaper() here
    }

    /**
     * @param pr
     */
    public void joinProject(ResearchProject pr) {
        // TODO implement Researcher.joinProject() here
    }

    /**
     * @param c
     */
    public void printPapers(Comparator<ResearchPaper> c) {
        // TODO implement Researcher.printPapers() here
    }

    /**
     * Returns the role of this user.
     * @return Role.TEACHER
     */
    @Override
    public Role getRole() {
        return Role.TEACHER;
    }

}
