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

    public Manager() {
    }

    public Manager(String id, String firstName, String lastName, String email,
                   String plainPassword, LocalDate dateOfBirth,
                   double salary, LocalDate dateHired, String department,
                   ManagerType managerType) {
        super(id, firstName, lastName, email, plainPassword, dateOfBirth,
              salary, dateHired, department);
        this.managerType = managerType;
    }

    public ManagerType getManagerType() { return managerType; }
    public void setManagerType(ManagerType managerType) { this.managerType = managerType; }

    /**
     * Approves a student's registration on a course.
     */
    public void approveRegistration(Student s, Course c) {
        // TODO (Сержан)
    }

    /**
     * Opens a course for student registration.
     */
    public void addCourseForRegistration(Course c) {
        // TODO (Сержан)
    }

    /**
     * Assigns a course to a teacher.
     */
    public void assignCourseToTeacher(Course c, Teacher t) {
        // TODO (Сержан)
    }

    public Report generateAcademicReport() {
        // TODO (Сержан): delegate to ReportGenerator
        return null;
    }

    public void manageNews() {
        // TODO (Сержан)
    }

    public List<Request> viewRequests() {
        // TODO (Сержан)
        return Collections.emptyList();
    }

    @Override
    public Role getRole() {
        return Role.MANAGER;
    }
}
