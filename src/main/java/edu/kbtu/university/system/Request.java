package edu.kbtu.university.system;

import java.io.*;
import java.time.*;
import java.util.*;

import edu.kbtu.university.enums.RequestStatus;
import edu.kbtu.university.users.User;

/**
 *
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public Request() {
        this.id = UUID.randomUUID().toString();
        this.status = RequestStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    /**
     *
     */
    private String id;

    /**
     *
     */
    private User sender;

    /**
     *
     */
    private String subject;

    /**
     *
     */
    private String content;

    /**
     *
     */
    private RequestStatus status;

    /**
     *
     */
    private LocalDateTime createdAt;

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
    public User getSender() {
        return sender;
    }

    /**
     * @param sender
     */
    public void setSender(User sender) {
        this.sender = sender;
    }

    /**
     * @return
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
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
    public RequestStatus getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    /**
     * @return
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return
     */
    public String toString() {
        String senderId = sender == null ? "unknown" : sender.getId();
        return String.format("Request{id='%s', sender=%s, subject='%s', status=%s, createdAt=%s}",
                id, senderId, subject, status, createdAt);
    }

}
