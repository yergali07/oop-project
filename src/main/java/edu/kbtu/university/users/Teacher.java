package edu.kbtu.university.users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.kbtu.university.academics.Course;
import edu.kbtu.university.academics.Mark;
import edu.kbtu.university.enums.Role;
import edu.kbtu.university.enums.TeacherTitle;
import edu.kbtu.university.research.ResearchPaper;
import edu.kbtu.university.research.ResearchProfile;
import edu.kbtu.university.research.ResearchProject;

/**
 * A teaching member of staff. Per ТЗ, teachers with the rank
 * {@link TeacherTitle#PROFESSOR} must also be researchers — this invariant
 * is enforced in {@link #setTitle(TeacherTitle)}.
 */
public class Teacher extends Employee implements Researcher {

    private static final long serialVersionUID = 1L;

    private TeacherTitle title;
    private List<Course> courses;
    private ResearchProfile profile;

    public Teacher() {
        this.courses = new ArrayList<>();
        this.profile = new ResearchProfile();
    }

    public Teacher(String id, String firstName, String lastName, String email,
                   String plainPassword, LocalDate dateOfBirth,
                   double salary, LocalDate dateHired, String department,
                   TeacherTitle title) {
        super(id, firstName, lastName, email, plainPassword, dateOfBirth,
              salary, dateHired, department);
        this.courses = new ArrayList<>();
        this.profile = new ResearchProfile();
        setTitle(title);
    }

    public TeacherTitle getTitle() { return title; }

    /**
     * Sets the academic title. A {@link TeacherTitle#PROFESSOR} must have a
     * non-null research profile per ТЗ; if missing one is auto-created so
     * the invariant holds.
     */
    public void setTitle(TeacherTitle title) {
        this.title = title;
        if (title == TeacherTitle.PROFESSOR && profile == null) {
            this.profile = new ResearchProfile();
        }
    }

    public ResearchProfile getProfile() { return profile; }
    public void setProfile(ResearchProfile profile) { this.profile = profile; }

    public List<Course> viewCourses() {
        return Collections.unmodifiableList(courses);
    }

    /**
     * Adds a course to this teacher's load. If the course is already in the
     * list it is left untouched.
     */
    public void manageCourse(Course c) {
        if (c != null && !courses.contains(c)) {
            courses.add(c);
        }
    }

    /**
     * Records a mark for a student. Mark storage and the
     * "teacher actually teaches this course" check live in the academic
     * module (Фархат). This stub validates inputs and delegates.
     */
    public void putMark(Student s, Course c, Mark m) {
        if (s == null || c == null || m == null) return;
        if (!courses.contains(c)) {
            throw new IllegalStateException(
                "Teacher " + getId() + " does not teach course " + c);
        }
        // TODO (Фархат): write mark into student's transcript
    }

    /**
     * Lists the students enrolled on the given course. Delegates to the
     * course module once {@code Course.getEnrolled()} is exposed.
     */
    public List<Student> viewStudents(Course c) {
        return Collections.emptyList();
    }

    @Override
    public List<ResearchPaper> getPapers() {
        return profile == null ? Collections.emptyList() : profile.getPapers();
    }

    @Override
    public List<ResearchProject> getProjects() {
        return profile == null ? Collections.emptyList() : profile.getProjects();
    }

    @Override
    public int getHIndex() {
        return profile == null ? 0 : profile.getHIndex();
    }

    @Override
    public void publishPaper(ResearchPaper p) {
        if (profile != null && p != null) {
            profile.addPaper(p);
        }
    }

    @Override
    public void joinProject(ResearchProject pr) {
        if (profile != null && pr != null) {
            profile.addProject(pr);
        }
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> c) {
        if (profile != null) {
            profile.printPapers(c);
        }
    }

    @Override
    public Role getRole() {
        return Role.TEACHER;
    }
}
