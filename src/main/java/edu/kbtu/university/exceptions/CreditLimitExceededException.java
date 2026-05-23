package edu.kbtu.university.exceptions;

/**
 * Thrown when a registration attempt would push the student's enrolled
 * credit count above the 21-credit cap mandated by ТЗ.
 */
public class CreditLimitExceededException extends RuntimeException {
    public CreditLimitExceededException() {
    }

    public CreditLimitExceededException(String message) {
        super(message);
    }
}
