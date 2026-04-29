package edu.kbtu.university.news;

import java.io.*;
import java.time.*;
import java.util.*;

import edu.kbtu.university.enums.NewsCategory;
import edu.kbtu.university.enums.UrgencyLevel;
import edu.kbtu.university.users.User;

/**
 * 
 */
public class News {

    /**
     * Default constructor
     */
    public News() {
    }

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private LocalDateTime publishedAt;

    /**
     * 
     */
    private UrgencyLevel urgency;

    /**
     * 
     */
    private NewsCategory category;

    /**
     * 
     */
    private User author;


}