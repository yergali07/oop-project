package edu.kbtu.university.exceptions;

/**
 * Thrown when a student tries to register for a new course while having
 * already failed three or more courses, the maximum allowed by ТЗ.
 */
public class MaxFailuresException extends RuntimeException {
    public MaxFailuresException() {
    }

    public MaxFailuresException(String message) {
        super(message);
    }
}
