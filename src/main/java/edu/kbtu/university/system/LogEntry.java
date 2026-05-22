package edu.kbtu.university.system;

import java.io.*;
import java.time.*;
import java.util.*;

import edu.kbtu.university.users.User;

/**
 *
 */
public class LogEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public LogEntry() {
    }

    /**
     *
     */
    private String id;

    /**
     *
     */
    private User user;

    /**
     *
     */
    private String action;

    /**
     *
     */
    private String details;

    /**
     *
     */
    private LocalDateTime timestamp;

    /**
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * @return
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return
     */
    public String toString() {
        String actor = user == null ? "system" : user.getId();
        return String.format("[%s] %s by %s: %s", timestamp, action, actor, details);
    }

}
