package edu.kbtu.university.users;

import java.io.*;
import java.util.*;

import edu.kbtu.university.academics.Course;
import edu.kbtu.university.enums.ManagerType;
import edu.kbtu.university.enums.Role;
import edu.kbtu.university.system.Report;
import edu.kbtu.university.system.Request;

/**
 * 
 */
public class Manager extends Employee {

    /**
     * Default constructor
     */
    public Manager() {
    }

    /**
     * 
     */
    private ManagerType managerType;

    /**
     * @param s 
     * @param c
     */
    public void approveRegistration(Student s, Course c) {
        // TODO implement here
    }

    /**
     * @param c
     */
    public void addCourseForRegistration(Course c) {
        // TODO implement here
    }

    /**
     * @param c 
     * @param t
     */
    public void assignCourseToTeacher(Course c, Teacher t) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Report generateAcademicReport() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void manageNews() {
        // TODO implement here
    }

    /**
     * @return
     */
    public List<Request> viewRequests() {
        // TODO implement here
        return null;
    }

    /**
     * Returns the role of this user.
     * @return Role.MANAGER
     */
    @Override
    public Role getRole() {
        return Role.MANAGER;
    }

}