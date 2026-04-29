package edu.kbtu.university.users;

import java.io.*;
import java.time.*;
import java.util.*;

import edu.kbtu.university.news.News;
import edu.kbtu.university.system.Request;

/**
 * 
 */
public abstract class Employee extends User implements NewsObserver {

    /**
     * Default constructor
     */
    public Employee() {
    }

    /**
     * 
     */
    protected double salary;

    /**
     * 
     */
    protected LocalDate dateHired;

    /**
     * 
     */
    protected String department;

    /**
     * @param to 
     * @param text
     */
    public void sendMessage(Employee to, String text) {
        // TODO implement here
    }

    /**
     * @param text
     */
    public void sendComplaint(String text) {
        // TODO implement here
    }

    /**
     * @param r
     */
    public void sendRequest(Request r) {
        // TODO implement here
    }

    /**
     * @param news
     */
    public void update(News news) {
        // TODO implement NewsObserver.update() here
    }

}