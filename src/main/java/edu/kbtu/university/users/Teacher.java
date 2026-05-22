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

    /** Default constructor (used by serialization). */
    public Teacher() {
        this.courses = new ArrayList<>();
        this.profile = new ResearchProfile();
    }

    /**
     * Full-state constructor.
     *
     * @param id            teacher id (typically {@code EMP-####})
     * @param firstName     first name
     * @param lastName      last name
     * @param email         contact email
     * @param plainPassword plain-text password (hashed before storage)
     * @param dateOfBirth   date of birth
     * @param salary        gross monthly salary
     * @param dateHired     hire date
     * @param department    organisational unit
     * @param title         academic title (lecturer, professor, ...)
     */
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

    /** @return academic title */
    public TeacherTitle getTitle() { return title; }

    /**
     * Sets the academic title. A {@link TeacherTitle#PROFESSOR} must have a
     * non-null research profile per ТЗ; if missing one is auto-created so
     * the invariant holds.
     *
     * @param title new academic title
     */
    public void setTitle(TeacherTitle title) {
        this.title = title;
        if (title == TeacherTitle.PROFESSOR && profile == null) {
            this.profile = new ResearchProfile();
        }
    }

    /** @return the research profile (may be {@code null} for non-researchers) */
    public ResearchProfile getProfile() { return profile; }

    /** @param profile new research profile */
    public void setProfile(ResearchProfile profile) { this.profile = profile; }

    /**
     * @return unmodifiable view of the courses this teacher delivers
     */
    public List<Course> viewCourses() {
        return Collections.unmodifiableList(courses);
    }

    /**
     * Adds a course to this teacher's load and keeps the {@code Course} side
     * of the relation in sync. If the course is already in the list it is
     * left untouched.
     *
     * @param c course to add (no-op if {@code null})
     */
    public void manageCourse(Course c) {
        if (c != null && !courses.contains(c)) {
            courses.add(c);
        }
        if (c != null && !c.getInstructors().contains(this)) {
            c.addInstructor(this);
        }
    }

    /**
     * Records a mark for a student after checking that this teacher is
     * assigned to the course.
     *
     * @param s student to grade
     * @param c course being graded
     * @param m mark to record
     * @throws IllegalArgumentException if any argument is {@code null},
     *         the teacher does not own the course, or the student has no
     *         transcript
     */
    public void putMark(Student s, Course c, Mark m) {
        if (s == null || c == null || m == null) {
            throw new IllegalArgumentException("Student, course and mark must not be null");
        }

        boolean teachesCourse = courses.contains(c) || c.getInstructors().contains(this);
        if (!teachesCourse) {
            throw new IllegalArgumentException("Teacher does not teach this course");
        }
        if (s.getTranscript() == null) {
            throw new IllegalArgumentException("Student transcript is not available");
        }

        s.getTranscript().addMark(c, m);
    }

    /**
     * Lists the students enrolled on the given course.
     *
     * @param c course to inspect
     * @return unmodifiable view of enrolled students, or an empty list if
     *         {@code c} is {@code null}
     */
    public List<Student> viewStudents(Course c) {
        if (c == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(c.getEnrolled());
    }

    /** {@inheritDoc} */
    @Override
    public List<ResearchPaper> getPapers() {
        return profile == null ? Collections.emptyList() : profile.getPapers();
    }

    /** {@inheritDoc} */
    @Override
    public List<ResearchProject> getProjects() {
        return profile == null ? Collections.emptyList() : profile.getProjects();
    }

    /** {@inheritDoc} */
    @Override
    public int getHIndex() {
        return profile == null ? 0 : profile.getHIndex();
    }

    /** {@inheritDoc} */
    @Override
    public void publishPaper(ResearchPaper p) {
        if (profile != null && p != null) {
            profile.addPaper(p);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void joinProject(ResearchProject pr) {
        if (profile != null && pr != null) {
            profile.addProject(pr);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void printPapers(Comparator<ResearchPaper> c) {
        if (profile != null) {
            profile.printPapers(c);
        }
    }

    /**
     * Returns this user's role.
     * @return {@link Role#TEACHER}
     */
    @Override
    public Role getRole() {
        return Role.TEACHER;
    }
}
