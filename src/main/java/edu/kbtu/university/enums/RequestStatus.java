package edu.kbtu.university.enums;

/**
 * Lifecycle state of a {@link edu.kbtu.university.system.Request}:
 * filed but not yet handled, accepted, refused, or withdrawn.
 */
public enum RequestStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED
}
