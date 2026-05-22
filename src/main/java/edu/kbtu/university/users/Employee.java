package edu.kbtu.university.users;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kbtu.university.news.News;
import edu.kbtu.university.system.Request;
import edu.kbtu.university.system.UniversitySystem;

/**
 * Common base for all employed personnel: teachers, managers, admins,
 * and research-only staff. Employees are also news subscribers and keep
 * per-instance inboxes for news and internal messages.
 */
public abstract class Employee extends User implements NewsObserver {

    private static final long serialVersionUID = 1L;

    /** Monthly gross salary in tenge. */
    protected double salary;

    /** Date the employee was hired. */
    protected LocalDate dateHired;

    /** Department the employee belongs to (e.g. "CS", "IT", "HR"). */
    protected String department;

    /** Inbox of news the employee has received (most recent last). */
    protected final List<News> receivedNews = new ArrayList<>();

    /** Plain-text messages from other employees. */
    protected final List<String> messages = new ArrayList<>();

    /** Default constructor (used by serialization). */
    public Employee() {
    }

    /**
     * Full-state constructor.
     *
     * @param id            employee identifier (typically {@code EMP-####})
     * @param firstName     first name
     * @param lastName      last name
     * @param email         contact email
     * @param plainPassword plain-text password (hashed before storage)
     * @param dateOfBirth   date of birth
     * @param salary        gross monthly salary
     * @param dateHired     hire date
     * @param department    organisational unit
     */
    public Employee(String id, String firstName, String lastName, String email,
                    String plainPassword, LocalDate dateOfBirth,
                    double salary, LocalDate dateHired, String department) {
        super(id, firstName, lastName, email, plainPassword, dateOfBirth);
        this.salary = salary;
        this.dateHired = dateHired;
        this.department = department;
    }

    /**
     * Returns the salary.
     * @return gross monthly salary in tenge
     */
    public double getSalary() { return salary; }

    /**
     * Updates the salary.
     * @param salary new gross monthly salary
     */
    public void setSalary(double salary) { this.salary = salary; }

    /**
     * Returns the hire date.
     * @return hire date
     */
    public LocalDate getDateHired() { return dateHired; }

    /**
     * Updates the hire date.
     * @param dateHired new hire date
     */
    public void setDateHired(LocalDate dateHired) { this.dateHired = dateHired; }

    /**
     * Returns the department.
     * @return department name
     */
    public String getDepartment() { return department; }

    /**
     * Updates the department.
     * @param department new department name
     */
    public void setDepartment(String department) { this.department = department; }

    /**
     * Returns the news inbox.
     * @return unmodifiable view of the news inbox
     */
    public List<News> getReceivedNews() {
        return Collections.unmodifiableList(receivedNews);
    }

    /**
     * Returns the message inbox.
     * @return unmodifiable view of received text messages
     */
    public List<String> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    /**
     * Sends an internal message to another employee. Stored as a tagged
     * string entry in the receiver's mailbox.
     *
     * @param to   recipient employee (no-op if {@code null})
     * @param text message body (no-op if {@code null})
     */
    public void sendMessage(Employee to, String text) {
        if (to == null || text == null) return;
        String stamp = LocalDateTime.now().toString();
        to.messages.add("[" + stamp + "] from " + getFullName() + ": " + text);
    }

    /**
     * Files a complaint as a {@link Request} with subject "Complaint".
     *
     * @param text complaint body
     */
    public void sendComplaint(String text) {
        Request r = new Request();
        r.setSender(this);
        r.setSubject("Complaint");
        r.setContent(text);
        r.setCreatedAt(LocalDateTime.now());
        sendRequest(r);
    }

    /**
     * Submits a generic request and persists it via
     * {@link UniversitySystem#addRequest(Request)}. The sender and
     * timestamp are auto-filled if missing.
     *
     * @param r request to dispatch (no-op if {@code null})
     */
    public void sendRequest(Request r) {
        if (r == null) return;
        if (r.getSender() == null) r.setSender(this);
        if (r.getCreatedAt() == null) r.setCreatedAt(LocalDateTime.now());
        UniversitySystem.getInstance().addRequest(r);
    }

    /**
     * Default {@link NewsObserver#update(News)}: store the news in the inbox.
     * Subclasses may override to add role-specific reactions.
     *
     * @param news news item being delivered
     */
    @Override
    public void update(News news) {
        if (news != null) {
            receivedNews.add(news);
        }
    }
}
