package edu.kbtu.university.users;

import java.io.*;
import java.util.*;

import edu.kbtu.university.academics.Course;
import edu.kbtu.university.academics.Mark;
import edu.kbtu.university.academics.Transcript;
import edu.kbtu.university.enums.Major;
import edu.kbtu.university.enums.Role;
import edu.kbtu.university.enums.StudentYear;
import edu.kbtu.university.news.News;
import edu.kbtu.university.research.ResearchPaper;
import edu.kbtu.university.research.ResearchProfile;
import edu.kbtu.university.research.ResearchProject;

/**
 * 
 */
public class Student extends User implements Researcher, NewsObserver {

    /**
     * Default constructor
     */
    public Student() {
        this.failedCourses = new ArrayList<>();
        this.transcript = new Transcript();
        this.transcript.setStudent(this);
    }

    /**
     *
     */
    private StudentYear year;

    /**
     * 
     */
    private Major major;

    /**
     * 
     */
    private double gpa;

    /**
     * 
     */
    private int currentCredits;

    /**
     * 
     */
    private Researcher supervisor;

    /**
     * 
     */
    private List<Course> failedCourses;

    /**
     * 
     */
    private ResearchProfile profile;

    /**
     * 
     */
    private Transcript transcript;

    /**
     * @param c
     */
    public void registerForCourse(Course c) {
        // TODO implement here
    }

    /**
     * @return
     */
    public List<Mark> viewMarks() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Transcript getTranscript() {
        return transcript;
    }

    public List<Course> getFailedCourses() {
        return failedCourses;
    }

    /**
     * @param t 
     * @param rating
     */
    public void rateTeacher(Teacher t, int rating) {
        // TODO implement here
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
     * @param news
     */
    public void update(News news) {
        // TODO implement NewsObserver.update() here
    }

    /**
     * Returns the role of this user.
     * @return Role.STUDENT
     */
    @Override
    public Role getRole() {
        return Role.STUDENT;
    }

}
