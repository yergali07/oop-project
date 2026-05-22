package edu.kbtu.university.system;

import java.io.*;
import java.time.*;
import java.util.*;

import edu.kbtu.university.users.User;

/**
 *
 */
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public Report() {
        this.generatedAt = LocalDateTime.now();
    }

    /**
     *
     */
    private String title;

    /**
     *
     */
    private LocalDateTime generatedAt;

    /**
     *
     */
    private String content;

    /**
     *
     */
    private User generatedBy;

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return
     */
    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    /**
     * @param generatedAt
     */
    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    /**
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return
     */
    public User getGeneratedBy() {
        return generatedBy;
    }

    /**
     * @param generatedBy
     */
    public void setGeneratedBy(User generatedBy) {
        this.generatedBy = generatedBy;
    }

    /**
     * @return
     */
    public String toString() {
        String author = generatedBy == null ? "system" : generatedBy.getId();
        return String.format("Report{title='%s', generatedAt=%s, generatedBy=%s, content='%s'}",
                title, generatedAt, author, content);
    }

}
