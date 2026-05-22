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

    public Student() {
        this.failedCourses = new ArrayList<>();
        this.profile = new ResearchProfile();
        this.transcript = new Transcript();
        this.transcript.setStudent(this);
    }

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

    public StudentYear getYear() { return year; }
    public void setYear(StudentYear year) { this.year = year; }

    public Major getMajor() { return major; }
    public void setMajor(Major major) { this.major = major; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public int getCurrentCredits() { return currentCredits; }
    public void setCurrentCredits(int currentCredits) { this.currentCredits = currentCredits; }

    public List<Course> getFailedCourses() { return failedCourses; }

    public ResearchProfile getProfile() { return profile; }
    public Transcript getTranscript() { return transcript; }

    public Researcher getSupervisor() { return supervisor; }

    /**
     * Assigns a research supervisor. Per ТЗ, only fourth-year students may
     * have a supervisor, and the supervisor's h-index must be at least 3.
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
     * prerequisites, credit limit, failure count and course capacity.
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
     * Returns the marks the student has accumulated so far. Storage is owned
     * by the academic module; this stub returns an empty list until the
     * Transcript accessor is wired up.
     */
    public List<Mark> viewMarks() {
        if (transcript == null || transcript.getMarks() == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(transcript.getMarks().values());
    }

    /**
     * Rates a teacher on a 1..5 scale.
     */
    public void rateTeacher(Teacher t, int rating) {
        if (t == null) return;
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be in [1,5]");
        }
        UniversitySystem.getInstance().recordTeacherRating(this, t, rating);
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
    public void update(News news) {
        if (news != null) {
            receivedNews.add(news);
        }
    }

    public List<News> getReceivedNews() {
        return Collections.unmodifiableList(receivedNews);
    }

    @Override
    public Role getRole() {
        return Role.STUDENT;
    }
}
