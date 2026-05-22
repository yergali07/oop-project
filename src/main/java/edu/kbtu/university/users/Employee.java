package edu.kbtu.university.users;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kbtu.university.news.News;
import edu.kbtu.university.system.Request;

/**
 * Common base for all employed personnel: teachers, managers, admins,
 * and research-only staff. Employees are also news subscribers.
 */
public abstract class Employee extends User implements NewsObserver {

    private static final long serialVersionUID = 1L;

    protected double salary;
    protected LocalDate dateHired;
    protected String department;

    /** Inbox of news the employee has received (most recent last). */
    protected final List<News> receivedNews = new ArrayList<>();

    /** Plain-text messages from other employees. */
    protected final List<String> messages = new ArrayList<>();

    public Employee() {
    }

    public Employee(String id, String firstName, String lastName, String email,
                    String plainPassword, LocalDate dateOfBirth,
                    double salary, LocalDate dateHired, String department) {
        super(id, firstName, lastName, email, plainPassword, dateOfBirth);
        this.salary = salary;
        this.dateHired = dateHired;
        this.department = department;
    }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public LocalDate getDateHired() { return dateHired; }
    public void setDateHired(LocalDate dateHired) { this.dateHired = dateHired; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public List<News> getReceivedNews() {
        return Collections.unmodifiableList(receivedNews);
    }

    public List<String> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    /**
     * Sends an internal message to another employee. Stored as a tagged
     * string entry in the receiver's mailbox.
     */
    public void sendMessage(Employee to, String text) {
        if (to == null || text == null) return;
        String stamp = LocalDateTime.now().toString();
        to.messages.add("[" + stamp + "] from " + getFullName() + ": " + text);
    }

    /**
     * Files a complaint as a {@link Request} with subject "Complaint".
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
     * Submits a generic request. The Request/management module owns
     * persistence; this stub stamps the sender and timestamp so callers
     * can rely on non-null values when integrating.
     */
    public void sendRequest(Request r) {
        if (r == null) return;
        if (r.getSender() == null) r.setSender(this);
        if (r.getCreatedAt() == null) r.setCreatedAt(LocalDateTime.now());
        // TODO (Сержан): persist via Manager.viewRequests when wired
    }

    /**
     * Default {@link NewsObserver#update(News)}: store the news in the inbox.
     * Subclasses may override to add role-specific reactions.
     */
    @Override
    public void update(News news) {
        if (news != null) {
            receivedNews.add(news);
        }
    }
}
