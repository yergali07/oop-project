package edu.kbtu.university.exceptions;

public class MaxFailuresException extends RuntimeException {
    public MaxFailuresException() {
    }

    public MaxFailuresException(String message) {
        super(message);
    }
}
