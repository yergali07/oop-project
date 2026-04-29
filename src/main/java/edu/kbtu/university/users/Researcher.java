package edu.kbtu.university.users;

import java.io.*;
import java.util.*;

import edu.kbtu.university.research.ResearchPaper;
import edu.kbtu.university.research.ResearchProject;

/**
 * 
 */
public interface Researcher {




    /**
     * @return
     */
    public List<ResearchPaper> getPapers();

    /**
     * @return
     */
    public List<ResearchProject> getProjects();

    /**
     * @return
     */
    public int getHIndex();

    /**
     * @param p
     */
    public void publishPaper(ResearchPaper p);

    /**
     * @param pr
     */
    public void joinProject(ResearchProject pr);

    /**
     * @param c
     */
    public void printPapers(Comparator<ResearchPaper> c);

}