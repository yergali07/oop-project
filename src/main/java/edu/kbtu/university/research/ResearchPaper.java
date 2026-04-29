package edu.kbtu.university.research;

import edu.kbtu.university.users.Researcher;

import java.io.*;
import java.time.*;
import java.util.*;

/**
 * 
 */
public class ResearchPaper {

    /**
     * Default constructor
     */
    public ResearchPaper() {
    }

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private List<Researcher> authors;

    /**
     * 
     */
    private String journal;

    /**
     * 
     */
    private String doi;

    /**
     * 
     */
    private LocalDate datePublished;

    /**
     * 
     */
    private int citations;

    /**
     * 
     */
    private int pages;

    /**
     * 
     */
    private String abstractText;

    /**
     * 
     */
    private List<String> keywords;

    /**
     * @param o
     * @return
     */
    public int compareTo(ResearchPaper o) {
        // TODO implement here
        return 0;
    }

}