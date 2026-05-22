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
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public News() {
        this.publishedAt = LocalDateTime.now();
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

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }

    public UrgencyLevel getUrgency() { return urgency; }
    public void setUrgency(UrgencyLevel urgency) { this.urgency = urgency; }

    public NewsCategory getCategory() { return category; }
    public void setCategory(NewsCategory category) { this.category = category; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    @Override
    public String toString() {
        return String.format("News{title='%s', category=%s, urgency=%s}", title, category, urgency);
    }


}
