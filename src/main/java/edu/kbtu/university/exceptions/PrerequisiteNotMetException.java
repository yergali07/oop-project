package edu.kbtu.university.exceptions;

public class PrerequisiteNotMetException extends RuntimeException {
    public PrerequisiteNotMetException() {
    }

    public PrerequisiteNotMetException(String message) {
        super(message);
    }
}
