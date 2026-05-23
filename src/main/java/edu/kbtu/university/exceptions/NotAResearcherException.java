package edu.kbtu.university.exceptions;

/**
 * Thrown when a non-{@code Researcher} user attempts to join a
 * {@link edu.kbtu.university.research.ResearchProject}.
 */
public class NotAResearcherException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotAResearcherException() {
        super("User is not a researcher");
    }

    public NotAResearcherException(String message) {
        super(message);
    }
}
