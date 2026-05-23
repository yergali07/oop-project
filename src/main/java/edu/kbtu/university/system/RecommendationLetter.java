package edu.kbtu.university.system;

import java.io.Serializable;
import java.time.LocalDate;

import edu.kbtu.university.users.Student;
import edu.kbtu.university.users.User;

/**
 * Recommendation letter issued by a {@link User} (typically a
 * {@link edu.kbtu.university.users.Teacher} or a research supervisor)
 * on behalf of a {@link Student} — e.g. to support a graduate-school
 * application or an external scholarship.
 *
 * <p>The body is generated from the student's transcript (GPA, completed
 * credits, failed-course list) plus a free-form summary supplied by the
 * author.
 */
public class RecommendationLetter implements Serializable {

    private static final long serialVersionUID = 1L;

    private User author;
    private Student subject;
    private LocalDate issuedOn;
    private String body;

    /** Default constructor (used by serialization). */
    public RecommendationLetter() {
    }

    /**
     * Full-state constructor.
     *
     * @param author  the issuing user (teacher or supervisor)
     * @param subject the student the letter is about
     * @param body    rendered letter body
     */
    public RecommendationLetter(User author, Student subject, String body) {
        this.author = author;
        this.subject = subject;
        this.body = body;
        this.issuedOn = LocalDate.now();
    }

    /**
     * Renders a default letter body from the student's transcript.
     *
     * @param author  the issuing user
     * @param subject the student the letter is about
     * @return a freshly built {@code RecommendationLetter}
     */
    public static RecommendationLetter generate(User author, Student subject) {
        double gpa = subject.getTranscript() == null ? 0.0
                : subject.getTranscript().calculateGPA();
        int credits = subject.getCurrentCredits();
        int failed = subject.getFailedCourses() == null ? 0 : subject.getFailedCourses().size();
        String standing = gpa >= 3.67 ? "outstanding"
                : gpa >= 3.0 ? "strong"
                : gpa >= 2.0 ? "satisfactory"
                : "below expectations";

        StringBuilder b = new StringBuilder();
        b.append("To whom it may concern,").append(System.lineSeparator()).append(System.lineSeparator());
        b.append("I am writing to recommend ").append(subject.getFullName())
         .append(" (id: ").append(subject.getId()).append("), ")
         .append("a student in our ").append(subject.getMajor()).append(" program. ")
         .append("Their academic record is ").append(standing).append(": ")
         .append("a current GPA of ").append(String.format("%.2f", gpa))
         .append(" across ").append(credits).append(" enrolled credits");
        if (failed > 0) {
            b.append(", with ").append(failed).append(" failed course(s) on record");
        }
        b.append(".").append(System.lineSeparator()).append(System.lineSeparator());
        b.append("Yours sincerely,").append(System.lineSeparator());
        b.append(author == null ? "(unspecified)" : author.getFullName());
        if (author != null) {
            b.append(" — ").append(author.getRole());
        }
        return new RecommendationLetter(author, subject, b.toString());
    }

    /** @return the issuing user */
    public User getAuthor() { return author; }

    /** @param author the issuing user */
    public void setAuthor(User author) { this.author = author; }

    /** @return the student the letter is about */
    public Student getSubject() { return subject; }

    /** @param subject the student the letter is about */
    public void setSubject(Student subject) { this.subject = subject; }

    /** @return date the letter was issued */
    public LocalDate getIssuedOn() { return issuedOn; }

    /** @param issuedOn date the letter was issued */
    public void setIssuedOn(LocalDate issuedOn) { this.issuedOn = issuedOn; }

    /** @return rendered letter body */
    public String getBody() { return body; }

    /** @param body rendered letter body */
    public void setBody(String body) { this.body = body; }

    @Override
    public String toString() {
        return "RecommendationLetter{author=" + (author == null ? "?" : author.getId())
                + ", subject=" + (subject == null ? "?" : subject.getId())
                + ", issuedOn=" + issuedOn + "}";
    }
}
