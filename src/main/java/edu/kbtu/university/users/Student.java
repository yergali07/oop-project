package edu.kbtu.university.users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.kbtu.university.academics.Course;
import edu.kbtu.university.academics.Mark;
import edu.kbtu.university.academics.Transcript;
import edu.kbtu.university.enums.Major;
import edu.kbtu.university.enums.Role;
import edu.kbtu.university.enums.StudentYear;
import edu.kbtu.university.exceptions.CourseRegistrationException;
import edu.kbtu.university.exceptions.CreditLimitExceededException;
import edu.kbtu.university.exceptions.LowHIndexException;
import edu.kbtu.university.exceptions.MaxFailuresException;
import edu.kbtu.university.exceptions.PrerequisiteNotMetException;
import edu.kbtu.university.news.News;
import edu.kbtu.university.research.ResearchPaper;
import edu.kbtu.university.research.ResearchProfile;
import edu.kbtu.university.research.ResearchProject;
import edu.kbtu.university.system.UniversitySystem;

/**
 * A student of the university. Implements {@link Researcher} optionally —
 * bachelors may publish, masters and PhDs typically do.
 */
public class Student extends User implements Researcher, NewsObserver {

    private static final long serialVersionUID = 1L;

    private StudentYear year;
    private Major major;
    private double gpa;
    private int currentCredits;
    private Researcher supervisor;
    private List<Course> failedCourses;
    private ResearchProfile profile;
    private Transcript transcript;
    private final List<News> receivedNews = new ArrayList<>();

    /** Default constructor (used by serialization). */
    public Student() {
        this.failedCourses = new ArrayList<>();
        this.profile = new ResearchProfile();
        this.transcript = new Transcript();
        this.transcript.setStudent(this);
    }

    /**
     * Full-state constructor.
     *
     * @param id            student id (typically {@code BD-####} for
     *                      bachelors, {@code MP-####} / {@code PHD-####}
     *                      for masters / PhD)
     * @param firstName     first name
     * @param lastName      last name
     * @param email         contact email
     * @param plainPassword plain-text password (hashed before storage)
     * @param dateOfBirth   date of birth
     * @param year          academic year
     * @param major         declared major
     */
    public Student(String id, String firstName, String lastName, String email,
                   String plainPassword, LocalDate dateOfBirth,
                   StudentYear year, Major major) {
        super(id, firstName, lastName, email, plainPassword, dateOfBirth);
        this.year = year;
        this.major = major;
        this.gpa = 0.0;
        this.currentCredits = 0;
        this.failedCourses = new ArrayList<>();
        this.profile = new ResearchProfile();
        this.transcript = new Transcript();
        this.transcript.setStudent(this);
    }

    /**
     * Returns the academic year.
     * @return academic year
     */
    public StudentYear getYear() { return year; }

    /**
     * Updates the academic year.
     * @param year new academic year
     */
    public void setYear(StudentYear year) { this.year = year; }

    /**
     * Returns the declared major.
     * @return declared major
     */
    public Major getMajor() { return major; }

    /**
     * Updates the major.
     * @param major new major
     */
    public void setMajor(Major major) { this.major = major; }

    /**
     * Returns the current GPA.
     * @return current GPA
     */
    public double getGpa() { return gpa; }

    /**
     * Updates the GPA.
     * @param gpa new GPA
     */
    public void setGpa(double gpa) { this.gpa = gpa; }

    /**
     * Returns the enrolled credit count.
     * @return credit count of currently enrolled courses
     */
    public int getCurrentCredits() { return currentCredits; }

    /**
     * Updates the enrolled credit count.
     * @param currentCredits new credit count
     */
    public void setCurrentCredits(int currentCredits) { this.currentCredits = currentCredits; }

    /**
     * Returns the list of failed courses.
     * @return mutable list of courses the student has failed
     */
    public List<Course> getFailedCourses() { return failedCourses; }

    /**
     * Returns the research profile.
     * @return the research profile (papers, projects, h-index)
     */
    public ResearchProfile getProfile() { return profile; }

    /**
     * Returns the transcript.
     * @return the student's transcript
     */
    public Transcript getTranscript() { return transcript; }

    /**
     * Returns the assigned research supervisor.
     * @return the assigned research supervisor, or {@code null}
     */
    public Researcher getSupervisor() { return supervisor; }

    /**
     * Assigns a research supervisor. Per ТЗ, only fourth-year students may
     * have a supervisor, and the supervisor's h-index must be at least 3.
     *
     * @param supervisor the supervisor to assign, or {@code null} to clear
     * @throws IllegalStateException if the student is not in the fourth year
     * @throws LowHIndexException if the supervisor's h-index is below 3
     */
    public void setSupervisor(Researcher supervisor) {
        if (supervisor == null) {
            this.supervisor = null;
            return;
        }
        if (year != StudentYear.FOURTH) {
            throw new IllegalStateException(
                "Only 4th-year students may be assigned a research supervisor");
        }
        if (supervisor.getHIndex() < 3) {
            throw new LowHIndexException(
                "Supervisor h-index must be >= 3 (was " + supervisor.getHIndex() + ")");
        }
        this.supervisor = supervisor;
    }

    /**
     * Registers this student on a course after checking academic rules:
     * prerequisites, credit limit (21), failure count (max 3), and course
     * capacity.
     *
     * @param c course to register on
     * @throws CourseRegistrationException   if the course is {@code null} or full
     * @throws PrerequisiteNotMetException   if prerequisites are not satisfied
     * @throws CreditLimitExceededException  if total credits would exceed 21
     * @throws MaxFailuresException          if 3 or more courses have been failed
     */
    public void registerForCourse(Course c) {
        if (c == null) {
            throw new CourseRegistrationException("Course is not specified");
        }
        if (!c.hasPrerequisitesMet(this)) {
            throw new PrerequisiteNotMetException("Prerequisites are not met");
        }
        if (currentCredits + c.getCredits() > 21) {
            throw new CreditLimitExceededException("Credit limit of 21 exceeded");
        }
        if (failedCourses.size() >= 3) {
            throw new MaxFailuresException("Maximum number of failed courses exceeded");
        }
        if (c.isFull()) {
            throw new CourseRegistrationException("Course has no free seats");
        }

        c.addStudent(this);
        currentCredits += c.getCredits();
    }

    /**
     * Returns the marks the student has accumulated so far.
     *
     * @return list of {@link Mark} entries from the transcript, possibly empty
     */
    public List<Mark> viewMarks() {
        if (transcript == null || transcript.getMarks() == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(transcript.getMarks().values());
    }

    /**
     * Rates a teacher on a 1..5 scale. The rating is recorded via
     * {@link UniversitySystem#recordTeacherRating(Student, Teacher, int)}.
     *
     * @param t      teacher to rate (no-op if {@code null})
     * @param rating rating in the range {@code [1,5]}
     * @throws IllegalArgumentException if {@code rating} is out of range
     */
    public void rateTeacher(Teacher t, int rating) {
        if (t == null) return;
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be in [1,5]");
        }
        UniversitySystem.getInstance().recordTeacherRating(this, t, rating);
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

    /** {@inheritDoc} */
    @Override
    public void update(News news) {
        if (news != null) {
            receivedNews.add(news);
        }
    }

    /**
     * Returns received news items.
     *
     * @return unmodifiable view of news this student has received
     */
    public List<News> getReceivedNews() {
        return Collections.unmodifiableList(receivedNews);
    }

    /**
     * Returns this user's role.
     * @return {@link Role#STUDENT}
     */
    @Override
    public Role getRole() {
        return Role.STUDENT;
    }
}
