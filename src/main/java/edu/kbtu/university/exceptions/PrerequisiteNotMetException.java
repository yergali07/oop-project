package edu.kbtu.university.exceptions;

/**
 * Thrown when a student attempts to register for a course whose
 * prerequisites have not been completed yet.
 */
public class PrerequisiteNotMetException extends RuntimeException {
    public PrerequisiteNotMetException() {
    }

    public PrerequisiteNotMetException(String message) {
        super(message);
    }
}
