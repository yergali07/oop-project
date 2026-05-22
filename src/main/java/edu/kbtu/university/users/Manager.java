package edu.kbtu.university.users;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import edu.kbtu.university.academics.Course;
import edu.kbtu.university.enums.ManagerType;
import edu.kbtu.university.enums.Role;
import edu.kbtu.university.system.Report;
import edu.kbtu.university.system.Request;

/**
 * Manager-level employee. Business logic (approving registrations, assigning
 * courses to teachers, generating reports, news management) lives in this
 * class but is implemented by Сержан in sprint 2; this skeleton supplies the
 * constructor, accessors, and role.
 */
public class Manager extends Employee {

    private static final long serialVersionUID = 1L;

    private ManagerType managerType;

    /** Default constructor (used by serialization). */
    public Manager() {
    }

    /**
     * Full-state constructor.
     *
     * @param id            manager id (typically {@code EMP-####})
     * @param firstName     first name
     * @param lastName      last name
     * @param email         contact email
     * @param plainPassword plain-text password (hashed before storage)
     * @param dateOfBirth   date of birth
     * @param salary        gross monthly salary
     * @param dateHired     hire date
     * @param department    organisational unit
     * @param managerType   the kind of manager (office registrar, academic, etc.)
     */
    public Manager(String id, String firstName, String lastName, String email,
                   String plainPassword, LocalDate dateOfBirth,
                   double salary, LocalDate dateHired, String department,
                   ManagerType managerType) {
        super(id, firstName, lastName, email, plainPassword, dateOfBirth,
              salary, dateHired, department);
        this.managerType = managerType;
    }

    /**
     * Returns the manager kind.
     * @return manager type
     */
    public ManagerType getManagerType() { return managerType; }

    /**
     * Updates the manager kind.
     * @param managerType new manager type
     */
    public void setManagerType(ManagerType managerType) { this.managerType = managerType; }

    /**
     * Approves a student's registration on a course.
     *
     * @param s student whose registration is being approved
     * @param c course the student is registering for
     */
    public void approveRegistration(Student s, Course c) {
        // TODO (Сержан)
    }

    /**
     * Opens a course for student registration.
     *
     * @param c course to open for registration
     */
    public void addCourseForRegistration(Course c) {
        // TODO (Сержан)
    }

    /**
     * Assigns a course to a teacher.
     *
     * @param c course to assign
     * @param t teacher who will deliver the course
     */
    public void assignCourseToTeacher(Course c, Teacher t) {
        // TODO (Сержан)
    }

    /**
     * Generates an academic performance report.
     *
     * @return aggregated academic report
     */
    public Report generateAcademicReport() {
        // TODO (Сержан): delegate to ReportGenerator
        return null;
    }

    /** Manages the news subsystem (compose, edit, retract). */
    public void manageNews() {
        // TODO (Сержан)
    }

    /**
     * Returns pending requests addressed to this manager.
     *
     * @return list of requests awaiting handling
     */
    public List<Request> viewRequests() {
        // TODO (Сержан)
        return Collections.emptyList();
    }

    @Override
    public Role getRole() {
        return Role.MANAGER;
    }
}
