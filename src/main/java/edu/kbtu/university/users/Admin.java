package edu.kbtu.university.users;

import java.io.*;
import java.util.*;

import edu.kbtu.university.enums.Role;
import edu.kbtu.university.system.LogEntry;

/**
 * 
 */
public class Admin extends Employee {

    /**
     * Default constructor
     */
    public Admin() {
    }

    /**
     * @param u
     */
    public void addUser(User u) {
        // TODO implement here
    }

    /**
     * @param id
     */
    public void removeUser(String id) {
        // TODO implement here
    }

    /**
     * @param u
     */
    public void updateUser(User u) {
        // TODO implement here
    }

    /**
     * @return
     */
    public List<LogEntry> viewLogs() {
        // TODO implement here
        return null;
    }

    /**
     * Returns the role of this user.
     * @return Role.ADMIN
     */
    @Override
    public Role getRole() {
        return Role.ADMIN;
    }

}