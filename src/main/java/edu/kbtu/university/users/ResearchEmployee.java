package edu.kbtu.university.users;

import java.io.*;
import java.util.*;

import edu.kbtu.university.enums.Role;
import edu.kbtu.university.research.ResearchPaper;
import edu.kbtu.university.research.ResearchProfile;
import edu.kbtu.university.research.ResearchProject;

/**
 * 
 */
public class ResearchEmployee extends Employee implements Researcher {

    /**
     * Default constructor
     */
    public ResearchEmployee() {
    }

    /**
     * 
     */
    private ResearchProfile profile;

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
     * @return Role.RESEARCH_EMPLOYEE
     */
    @Override
    public Role getRole() {
        return Role.RESEARCH_EMPLOYEE;
    }

}