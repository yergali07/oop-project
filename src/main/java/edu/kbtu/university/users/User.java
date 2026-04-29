package edu.kbtu.university.users;

import java.io.*;
import java.time.*;
import java.util.*;

import edu.kbtu.university.enums.Role;

/**
 * 
 */
public abstract class User {

    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * 
     */
    protected String id;

    /**
     * 
     */
    protected String firstName;

    /**
     * 
     */
    protected String lastName;

    /**
     * 
     */
    protected String email;

    /**
     * 
     */
    protected String password;

    /**
     * 
     */
    protected LocalDate dateOfBirth;


    /**
     * @param pwd 
     * @return
     */
    public boolean login(String pwd) {
        // TODO implement here
        return false;
    }

    /**
     * 
     */
    public void logout() {
        // TODO implement here
    }

    /**
     * @return
     */
    public abstract Role getRole();

    /**
     * @param o 
     * @return
     */
    public boolean equals(Object o) {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public int hashCode() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public String toString() {
        // TODO implement here
        return "";
    }

}