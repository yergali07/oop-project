package edu.kbtu.university.users;

import java.io.*;
import java.util.*;

import edu.kbtu.university.academics.Course;
import edu.kbtu.university.academics.Mark;
import edu.kbtu.university.academics.Transcript;
import edu.kbtu.university.enums.Major;
import edu.kbtu.university.enums.Role;
import edu.kbtu.university.enums.StudentYear;
import edu.kbtu.university.exceptions.CourseRegistrationException;
import edu.kbtu.university.exceptions.CreditLimitExceededException;
import edu.kbtu.university.exceptions.MaxFailuresException;
import edu.kbtu.university.exceptions.PrerequisiteNotMetException;
import edu.kbtu.university.news.News;
import edu.kbtu.university.research.ResearchPaper;
import edu.kbtu.university.research.ResearchProfile;
import edu.kbtu.university.research.ResearchProject;

/**
 * Represents a student user in the university system.
 * In the academic module, the student can register for courses and owns a transcript.
 */
public class Student extends User implements Researcher, NewsObserver {

    /**
     * Creates a student with initialized academic collections and transcript.
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
     * Registers the student for a course after validating academic constraints.
     *
     * @param c course to register for
     * @throws PrerequisiteNotMetException if the student has not passed course prerequisites
     * @throws CreditLimitExceededException if registration would exceed the 21-credit limit
     * @throws MaxFailuresException if the student already has three or more failed courses
     * @throws CourseRegistrationException if the course is null or full
     */
    public void registerForCourse(Course c) {
        if (c == null) {
            throw new CourseRegistrationException("Курс не найден");
        }
        if (!c.hasPrerequisitesMet(this)) {
            throw new PrerequisiteNotMetException("Пререквизиты не пройдены");
        }
        if (this.currentCredits + c.getCredits() > 21) {
            throw new CreditLimitExceededException("Превышен лимит в 21 кредит");
        }
        if (failedCourses.size() >= 3) {
            throw new MaxFailuresException("Превышен лимит провалов (макс 3)");
        }
        if (c.isFull()) {
            throw new CourseRegistrationException("На курсе нет свободных мест");
        }

        c.addStudent(this);
        this.currentCredits += c.getCredits();
    }

    /**
     * Returns marks visible to the student.
     *
     * @return list of marks
     */
    public List<Mark> viewMarks() {
        // TODO implement here
        return null;
    }

    /**
     * Returns the student's academic transcript.
     *
     * @return student transcript
     */
    public Transcript getTranscript() {
        return transcript;
    }

    /**
     * Returns the list of courses failed by the student.
     *
     * @return failed courses
     */
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
