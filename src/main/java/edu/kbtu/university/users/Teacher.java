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
 * 
 */
public class Teacher extends Employee implements Researcher {

    /**
     * Default constructor
     */
    public Teacher() {
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
     * @return
     */
    public List<Course> viewCourses() {
        // TODO implement here
        return null;
    }

    /**
     * @param c
     */
    public void manageCourse(Course c) {
        // TODO implement here
    }

    /**
     * @param s 
     * @param c 
     * @param m
     */
    public void putMark(Student s, Course c, Mark m) {
        // TODO implement here
    }

    /**
     * @param c 
     * @return
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