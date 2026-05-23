package edu.kbtu.university.enums;

/**
 * Academic rank of a {@link edu.kbtu.university.users.Teacher}. Per ТЗ,
 * a {@code PROFESSOR} must also be a researcher.
 */
public enum TeacherTitle {
    TUTOR,
    SENIOR_LECTURER,
    LECTURER,
    ASSOC_PROFESSOR,
    PROFESSOR
}