package edu.kbtu.university.exceptions;

public class CreditLimitExceededException extends RuntimeException {
    public CreditLimitExceededException() {
    }

    public CreditLimitExceededException(String message) {
        super(message);
    }
}
