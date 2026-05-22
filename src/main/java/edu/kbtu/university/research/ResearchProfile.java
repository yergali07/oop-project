package edu.kbtu.university.research;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ResearchProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public ResearchProfile() {
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.hIndex = 0;
    }

    /**
     *
     */
    private List<ResearchPaper> papers;

    /**
     *
     */
    private List<ResearchProject> projects;

    /**
     *
     */
    private int hIndex;

    public List<ResearchPaper> getPapers() {
        return papers;
    }

    public List<ResearchProject> getProjects() {
        return projects;
    }

    public int getHIndex() {
        return hIndex;
    }

    /**
     * @return
     */
    public int calculateHIndex() {
        // TODO implement here (Сержан) — placeholder returns cached hIndex
        return hIndex;
    }

    /**
     * @param p
     */
    public void addPaper(ResearchPaper p) {
        // TODO implement here
    }

    /**
     * @param pr
     */
    public void addProject(ResearchProject pr) {
        // TODO implement here
    }

    /**
     * @param c
     */
    public void printPapers(Comparator<ResearchPaper> c) {
        // TODO implement here
    }

}