package edu.kbtu.university.exceptions;

/**
 * Generic course-registration failure that does not fall into one of the
 * specific subcategories ({@link CreditLimitExceededException},
 * {@link MaxFailuresException}, {@link PrerequisiteNotMetException}) —
 * for example, a full course or a null course argument.
 */
public class CourseRegistrationException extends RuntimeException {
    public CourseRegistrationException() {
    }

    public CourseRegistrationException(String message) {
        super(message);
    }
}
