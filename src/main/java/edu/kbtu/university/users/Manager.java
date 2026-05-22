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
 * Manager-level employee. Serzhan owns the integration methods that connect
 * academic registration, course assignment, reports, news, and requests.
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

    public void approveRegistration(Student s, Course c) {
        if (s == null || c == null) {
            throw new IllegalArgumentException("student and course must not be null");
        }
        s.registerForCourse(c);
        UniversitySystem.getInstance().addLog(this, "APPROVE_REGISTRATION",
                "Approved " + s.getId() + " for course " + c);
    }

    public void addCourseForRegistration(Course c) {
        UniversitySystem.getInstance().addCourse(c);
        UniversitySystem.getInstance().addLog(this, "ADD_COURSE", "Opened course " + c);
    }

    public void assignCourseToTeacher(Course c, Teacher t) {
        if (c == null || t == null) {
            throw new IllegalArgumentException("course and teacher must not be null");
        }
        c.addInstructor(t);
        t.manageCourse(c);
        UniversitySystem.getInstance().addLog(this, "ASSIGN_COURSE",
                "Assigned course " + c + " to teacher " + t.getId());
    }

    public Report generateAcademicReport() {
        List<Course> courses = UniversitySystem.getInstance().getCourses();
        Course firstCourse = courses == null || courses.isEmpty() ? null : courses.get(0);
        return new ReportGenerator().academicReport(firstCourse);
    }

    public void manageNews() {
        if (UniversitySystem.getInstance().getNewsService() == null) {
            UniversitySystem.getInstance().setNewsService(new edu.kbtu.university.news.NewsService());
        }
    }

    public List<Request> viewRequests() {
        List<Request> requests = UniversitySystem.getInstance().getRequests();
        return requests == null ? Collections.emptyList() : Collections.unmodifiableList(requests);
    }

    @Override
    public Role getRole() {
        return Role.MANAGER;
    }
}
