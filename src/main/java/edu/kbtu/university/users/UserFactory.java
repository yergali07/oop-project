package edu.kbtu.university.users;

import java.io.*;
import java.util.*;

import edu.kbtu.university.enums.Role;

/**
 * 
 */
public class UserFactory {

    /**
     * Default constructor
     */
    public UserFactory() {
    }

    /**
     * @param role 
     * @param args 
     * @return
     */
    public static User createUser(Role role, Map<String,Object> args) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public static Student createStudent() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public static Teacher createTeacher() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public static Manager createManager() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public static Admin createAdmin() {
        // TODO implement here
        return null;
    }

}