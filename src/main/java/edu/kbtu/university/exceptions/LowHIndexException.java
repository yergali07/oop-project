package edu.kbtu.university.exceptions;

/**
 * Thrown when an attempt is made to assign a research supervisor whose
 * h-index is below the required threshold (default 3).
 */
public class LowHIndexException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LowHIndexException() {
        super("Researcher h-index is too low for this role");
    }

    public LowHIndexException(String message) {
        super(message);
    }
}
