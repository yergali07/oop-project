package edu.kbtu.university.system;

import java.io.*;
import java.util.*;

import edu.kbtu.university.academics.Course;
import edu.kbtu.university.news.News;
import edu.kbtu.university.news.NewsService;
import edu.kbtu.university.research.ResearchPaper;
import edu.kbtu.university.users.Researcher;
import edu.kbtu.university.users.User;

/**
 * 
 */
public class UniversitySystem {

    /**
     * Default constructor
     */
    public UniversitySystem() {
    }

    /**
     * 
     */
    private static UniversitySystem instance;

    /**
     * 
     */
    private List<User> users;

    /**
     * 
     */
    private List<Course> courses;

    /**
     * 
     */
    private List<News> news;

    /**
     * 
     */
    private NewsService newsService;

    /**
     * 
     */
    private List<LogEntry> logs;





    /**
     * @return
     */
    public static UniversitySystem getInstance() {
        // TODO implement here
        return null;
    }

    /**
     * @param email 
     * @param pwd 
     * @return
     */
    public User authenticate(String email, String pwd) {
        // TODO implement here
        return null;
    }

    /**
     * @param c
     */
    public void printAllResearchersPapers(Comparator<ResearchPaper> c) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Researcher topCitedResearcher() {
        // TODO implement here
        return null;
    }

    /**
     * @param year 
     * @return
     */
    public Researcher topCitedOfYear(int year) {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void saveState() {
        // TODO implement here
    }

    /**
     * 
     */
    public void loadState() {
        // TODO implement here
    }

}