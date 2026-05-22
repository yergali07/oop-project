package edu.kbtu.university.exceptions;

public class CourseRegistrationException extends RuntimeException {
    public CourseRegistrationException() {
    }

    public CourseRegistrationException(String message) {
        super(message);
    }
}
