package edu.kbtu.university.system;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kbtu.university.academics.AttendanceRecord;
import edu.kbtu.university.academics.Course;
import edu.kbtu.university.exceptions.AuthenticationException;
import edu.kbtu.university.news.News;
import edu.kbtu.university.news.NewsService;
import edu.kbtu.university.research.ResearchPaper;
import edu.kbtu.university.users.Researcher;
import edu.kbtu.university.users.Teacher;
import edu.kbtu.university.users.User;

/**
 * Singleton container for global system state. Owned by Serzhan: this class
 * wires together users, courses, news, logs, requests, ratings, authentication,
 * research queries, and save/load.
 */
public class UniversitySystem implements Serializable {

    private static final long serialVersionUID = 1L;

    private static UniversitySystem instance;

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<News> news = new ArrayList<>();
    private NewsService newsService = new NewsService();
    private List<LogEntry> logs = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();
    private Map<String, List<Integer>> teacherRatings = new HashMap<>();
    private List<AttendanceRecord> attendance = new ArrayList<>();

    private UniversitySystem() {
    }

    public static synchronized UniversitySystem getInstance() {
        if (instance == null) {
            instance = new UniversitySystem();
        }
        return instance;
    }

    /** Test/utility hook to reset the singleton, used by integration tests. */
    public static synchronized void resetInstance() {
        instance = null;
    }

    public List<User> getUsers() { return users; }
    public List<Course> getCourses() { return courses; }
    public List<News> getNews() { return news; }
    public List<LogEntry> getLogs() { return logs; }
    public List<Request> getRequests() { return requests; }
    public Map<String, List<Integer>> getTeacherRatings() { return teacherRatings; }

    public NewsService getNewsService() { return newsService; }
    public void setNewsService(NewsService newsService) { this.newsService = newsService; }

    public void addUser(User u) {
        if (u != null && !users.contains(u)) users.add(u);
    }

    public void removeUser(String id) {
        if (id == null) return;
        users.removeIf(u -> id.equals(u.getId()));
    }

    public void addCourse(Course c) {
        if (c != null && !courses.contains(c)) courses.add(c);
    }

    public void addNews(News n) {
        if (n == null) return;
        news.add(n);
        if (newsService == null) {
            newsService = new NewsService();
        }
        newsService.publish(n);
    }

    public void addRequest(Request r) {
        if (r != null && !requests.contains(r)) requests.add(r);
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
     * Advanced search: returns every {@link User} whose id, full name, or
     * email matches the given regular expression (case-insensitive, partial
     * match via {@code Matcher.find}). Bonus feature.
     *
     * @param pattern Java regular expression
     * @return list of matching users (possibly empty); never {@code null}
     * @throws java.util.regex.PatternSyntaxException if the pattern is invalid
     */
    public List<User> findUsersByRegex(String pattern) {
        if (pattern == null || pattern.isBlank()) return new ArrayList<>();
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(
                pattern, java.util.regex.Pattern.CASE_INSENSITIVE);
        List<User> out = new ArrayList<>();
        for (User u : users) {
            String id = u.getId() == null ? "" : u.getId();
            String name = u.getFullName() == null ? "" : u.getFullName();
            String email = u.getEmail() == null ? "" : u.getEmail();
            if (p.matcher(id).find() || p.matcher(name).find() || p.matcher(email).find()) {
                out.add(u);
            }
        }
        return out;
    }

    /**
     * Advanced search: returns every {@link Course} whose id or name matches
     * the given regular expression. Bonus feature.
     *
     * @param pattern Java regular expression
     * @return list of matching courses (possibly empty); never {@code null}
     * @throws java.util.regex.PatternSyntaxException if the pattern is invalid
     */
    public List<Course> findCoursesByRegex(String pattern) {
        if (pattern == null || pattern.isBlank()) return new ArrayList<>();
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(
                pattern, java.util.regex.Pattern.CASE_INSENSITIVE);
        List<Course> out = new ArrayList<>();
        for (Course c : courses) {
            String id = c.getId() == null ? "" : c.getId();
            String name = c.getName() == null ? "" : c.getName();
            if (p.matcher(id).find() || p.matcher(name).find()) {
                out.add(c);
            }
        }
        return out;
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

    public User authenticate(String email, String pwd) throws AuthenticationException {
        User u = findUserByEmail(email);
        if (u == null) {
            throw new AuthenticationException("No user with email " + email);
        }
        u.login(pwd);
        addLog(u, "LOGIN", "User authenticated");
        return u;
    }

    public void recordTeacherRating(User student, Teacher teacher, int rating) {
        if (teacher == null) return;
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be in [1,5]");
        }
        teacherRatings.computeIfAbsent(teacher.getId(), key -> new ArrayList<>()).add(rating);
        addLog(student, "RATE_TEACHER", "Rated teacher " + teacher.getId() + " with " + rating);
    }

    public double averageTeacherRating(Teacher teacher) {
        if (teacher == null) return 0.0;
        List<Integer> ratings = teacherRatings.get(teacher.getId());
        if (ratings == null || ratings.isEmpty()) return 0.0;
        return ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    /**
     * Returns the mutable attendance log. Used by
     * {@link edu.kbtu.university.users.Teacher#markAttendance} and
     * {@link edu.kbtu.university.users.Student#getAttendanceRate}.
     *
     * @return the attendance log
     */
    public List<AttendanceRecord> getAttendance() {
        if (attendance == null) attendance = new ArrayList<>();
        return attendance;
    }

    /**
     * Appends an attendance entry. Bonus feature.
     *
     * @param record entry to append (no-op if {@code null})
     */
    public void addAttendance(AttendanceRecord record) {
        if (record == null) return;
        if (attendance == null) attendance = new ArrayList<>();
        attendance.add(record);
    }

    public void printAllResearchersPapers(Comparator<ResearchPaper> c) {
        Comparator<ResearchPaper> comparator = c == null ? Comparator.naturalOrder() : c;
        users.stream()
                .filter(user -> user instanceof Researcher)
                .map(user -> (Researcher) user)
                .flatMap(researcher -> researcher.getPapers().stream())
                .sorted(comparator)
                .forEach(System.out::println);
    }

    public Researcher topCitedResearcher() {
        return users.stream()
                .filter(user -> user instanceof Researcher)
                .map(user -> (Researcher) user)
                .max(Comparator.comparingInt(this::totalCitations))
                .orElse(null);
    }

    public Researcher topCitedOfYear(int year) {
        return users.stream()
                .filter(user -> user instanceof Researcher)
                .map(user -> (Researcher) user)
                .max(Comparator.comparingInt(researcher -> citationsOfYear(researcher, year)))
                .orElse(null);
    }

    public void saveState() {
        DataStorage.serialize(this);
    }

    public void loadState() {
        UniversitySystem loaded = DataStorage.deserialize();
        if (loaded != null) {
            loaded.normalizeState();
            instance = loaded;
        }
    }

    private int totalCitations(Researcher researcher) {
        return researcher.getPapers().stream().mapToInt(ResearchPaper::getCitations).sum();
    }

    private int citationsOfYear(Researcher researcher, int year) {
        return researcher.getPapers().stream()
                .filter(paper -> paper.getDatePublished() != null && paper.getDatePublished().getYear() == year)
                .mapToInt(ResearchPaper::getCitations)
                .sum();
    }

    private void normalizeState() {
        if (users == null) users = new ArrayList<>();
        if (courses == null) courses = new ArrayList<>();
        if (news == null) news = new ArrayList<>();
        if (newsService == null) newsService = new NewsService();
        if (logs == null) logs = new ArrayList<>();
        if (requests == null) requests = new ArrayList<>();
        if (teacherRatings == null) teacherRatings = new HashMap<>();
    }
}
