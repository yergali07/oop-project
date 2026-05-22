package edu.kbtu.university.users;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import edu.kbtu.university.academics.Course;
import edu.kbtu.university.enums.ManagerType;
import edu.kbtu.university.enums.Role;
import edu.kbtu.university.system.Report;
import edu.kbtu.university.system.ReportGenerator;
import edu.kbtu.university.system.Request;
import edu.kbtu.university.system.UniversitySystem;

/**
 * Manager-level employee. Owns the integration methods that connect
 * academic registration, course assignment, reports, news, and requests.
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
     * Approves a student's registration on a course by delegating to
     * {@link Student#registerForCourse(Course)}, then writes an audit log.
     *
     * @param s student whose registration is being approved
     * @param c course the student is registering for
     * @throws IllegalArgumentException if either argument is {@code null}
     */
    public void approveRegistration(Student s, Course c) {
        if (s == null || c == null) {
            throw new IllegalArgumentException("student and course must not be null");
        }
        s.registerForCourse(c);
        UniversitySystem.getInstance().addLog(this, "APPROVE_REGISTRATION",
                "Approved " + s.getId() + " for course " + c);
    }

    /**
     * Opens a course for student registration and writes an audit log.
     *
     * @param c course to open for registration
     */
    public void addCourseForRegistration(Course c) {
        UniversitySystem.getInstance().addCourse(c);
        UniversitySystem.getInstance().addLog(this, "ADD_COURSE", "Opened course " + c);
    }

    /**
     * Assigns a course to a teacher (updates both sides of the relation)
     * and writes an audit log.
     *
     * @param c course to assign
     * @param t teacher who will deliver the course
     * @throws IllegalArgumentException if either argument is {@code null}
     */
    public void assignCourseToTeacher(Course c, Teacher t) {
        if (c == null || t == null) {
            throw new IllegalArgumentException("course and teacher must not be null");
        }
        c.addInstructor(t);
        t.manageCourse(c);
        UniversitySystem.getInstance().addLog(this, "ASSIGN_COURSE",
                "Assigned course " + c + " to teacher " + t.getId());
    }

    /**
     * Generates an academic performance report via {@link ReportGenerator}.
     *
     * @return aggregated academic report (may report on the first course in
     *         the system as a placeholder)
     */
    public Report generateAcademicReport() {
        List<Course> courses = UniversitySystem.getInstance().getCourses();
        Course firstCourse = courses == null || courses.isEmpty() ? null : courses.get(0);
        return new ReportGenerator().academicReport(firstCourse);
    }

    /**
     * Ensures the news service is initialised in the system. The news service
     * is the publisher side of the Observer pattern wired up in
     * {@link edu.kbtu.university.news.NewsService}.
     */
    public void manageNews() {
        if (UniversitySystem.getInstance().getNewsService() == null) {
            UniversitySystem.getInstance().setNewsService(new edu.kbtu.university.news.NewsService());
        }
    }

    /**
     * Returns pending requests addressed to this manager.
     *
     * @return unmodifiable view of the requests currently in the system
     */
    public List<Request> viewRequests() {
        List<Request> requests = UniversitySystem.getInstance().getRequests();
        return requests == null ? Collections.emptyList() : Collections.unmodifiableList(requests);
    }

    /**
     * Returns this user's role.
     * @return {@link Role#MANAGER}
     */
    @Override
    public Role getRole() {
        return Role.MANAGER;
    }
}
