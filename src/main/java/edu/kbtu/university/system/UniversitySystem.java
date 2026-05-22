package edu.kbtu.university.system;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.kbtu.university.academics.Course;
import edu.kbtu.university.exceptions.AuthenticationException;
import edu.kbtu.university.news.News;
import edu.kbtu.university.news.NewsService;
import edu.kbtu.university.research.ResearchPaper;
import edu.kbtu.university.users.Researcher;
import edu.kbtu.university.users.User;

/**
 * Singleton container for global system state. Owned by Сержан — this file
 * holds a minimal viable implementation so that the user/auth module can
 * compile and run end-to-end during sprint 1. Сержан extends it in sprint 2
 * with full save/load, reporting, and authentication flows.
 */
public class UniversitySystem implements Serializable {

    private static final long serialVersionUID = 1L;

    private static UniversitySystem instance;

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<News> news = new ArrayList<>();
    private NewsService newsService;
    private List<LogEntry> logs = new ArrayList<>();

    public UniversitySystem() {
    }

    public static synchronized UniversitySystem getInstance() {
        if (instance == null) {
            instance = new UniversitySystem();
        }
        return instance;
    }

    /** Test/utility hook to reset the singleton — used in integration tests. */
    public static synchronized void resetInstance() {
        instance = null;
    }

    public List<User> getUsers() { return users; }
    public List<Course> getCourses() { return courses; }
    public List<News> getNews() { return news; }
    public List<LogEntry> getLogs() { return logs; }

    public NewsService getNewsService() { return newsService; }
    public void setNewsService(NewsService newsService) { this.newsService = newsService; }

    public void addUser(User u) {
        if (u != null && !users.contains(u)) users.add(u);
    }

    public void removeUser(String id) {
        if (id == null) return;
        users.removeIf(u -> id.equals(u.getId()));
    }

    public User findUserById(String id) {
        if (id == null) return null;
        for (User u : users) {
            if (id.equals(u.getId())) return u;
        }
        return null;
    }

    public User findUserByEmail(String email) {
        if (email == null) return null;
        for (User u : users) {
            if (email.equalsIgnoreCase(u.getEmail())) return u;
        }
        return null;
    }

    /**
     * Records an action in the audit log. Used by Admin and during the
     * authentication flow.
     */
    public void addLog(User actor, String action, String details) {
        LogEntry e = new LogEntry();
        e.setId("LOG-" + (logs.size() + 1));
        e.setUser(actor);
        e.setAction(action);
        e.setDetails(details);
        e.setTimestamp(LocalDateTime.now());
        logs.add(e);
    }

    /**
     * @param email
     * @param pwd
     * @return
     */
    public User authenticate(String email, String pwd) throws AuthenticationException {
        User u = findUserByEmail(email);
        if (u == null) {
            throw new AuthenticationException("No user with email " + email);
        }
        u.login(pwd);
        addLog(u, "LOGIN", "User authenticated");
        return u;
    }

    /**
     * @param c
     */
    public void printAllResearchersPapers(Comparator<ResearchPaper> c) {
        // TODO (Сержан, sprint 2)
    }

    /**
     * @return
     */
    public Researcher topCitedResearcher() {
        // TODO (Сержан, sprint 2)
        return null;
    }

    /**
     * @param year
     * @return
     */
    public Researcher topCitedOfYear(int year) {
        // TODO (Сержан, sprint 2)
        return null;
    }

    public void saveState() {
        // TODO (Сержан, sprint 2): DataStorage.serialize(this)
    }

    public void loadState() {
        // TODO (Сержан, sprint 2): DataStorage.deserialize()
    }
}
