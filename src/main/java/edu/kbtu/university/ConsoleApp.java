package edu.kbtu.university;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.kbtu.university.academics.Course;
import edu.kbtu.university.academics.Lesson;
import edu.kbtu.university.academics.Mark;
import edu.kbtu.university.enums.LessonType;
import edu.kbtu.university.enums.Major;
import edu.kbtu.university.enums.ManagerType;
import edu.kbtu.university.enums.RoomType;
import edu.kbtu.university.enums.Semester;
import edu.kbtu.university.enums.StudentYear;
import edu.kbtu.university.enums.TeacherTitle;
import edu.kbtu.university.exceptions.AuthenticationException;
import edu.kbtu.university.research.ByCitationsComparator;
import edu.kbtu.university.research.ByDateComparator;
import edu.kbtu.university.research.ByPagesComparator;
import edu.kbtu.university.research.ResearchPaper;
import edu.kbtu.university.research.ResearchPaperBuilder;
import edu.kbtu.university.system.LogEntry;
import edu.kbtu.university.system.RecommendationLetter;
import edu.kbtu.university.system.Report;
import edu.kbtu.university.system.ScheduleGenerator;
import edu.kbtu.university.system.UniversitySystem;
import edu.kbtu.university.users.Admin;
import edu.kbtu.university.users.Manager;
import edu.kbtu.university.users.Researcher;
import edu.kbtu.university.users.ResearchEmployee;
import edu.kbtu.university.users.Student;
import edu.kbtu.university.users.Teacher;
import edu.kbtu.university.users.User;
import edu.kbtu.university.users.UserFactory;

/**
 * Interactive role-based console for the university system.
 *
 * <p>On startup the saved state ({@code university-system.ser}) is loaded
 * if present; otherwise a default admin ({@code admin@kbtu.kz} / {@code admin})
 * is seeded so the operator can immediately log in and bootstrap the rest of
 * the population.
 *
 * <p>Each role exposes its own menu:
 * <ul>
 *   <li>{@code Admin} — user lifecycle, log audit, save state</li>
 *   <li>{@code Manager} — open courses, assign teachers, requests, reports</li>
 *   <li>{@code Teacher} — view courses / students, put marks, publish papers</li>
 *   <li>{@code Student} — view marks / transcript, register for courses, rate teacher</li>
 *   <li>{@code ResearchEmployee} — view papers, publish papers, sort by strategy</li>
 * </ul>
 */
public final class ConsoleApp {

    private final Scanner in;
    private final UniversitySystem sys;

    private ConsoleApp(Scanner in) {
        this.in = in;
        this.sys = UniversitySystem.getInstance();
    }

    /** Runs the interactive console on {@code System.in} / {@code System.out}. */
    public static void run() {
        try (Scanner sc = new Scanner(System.in)) {
            new ConsoleApp(sc).loop();
        } catch (EndOfInputException e) {
            System.out.println();
            System.out.println("End of input — exiting.");
        }
    }

    private void loop() {
        bootstrap();
        System.out.println();
        System.out.println("=== KBTU University System ===");

        while (true) {
            System.out.println();
            System.out.println("1) Login");
            System.out.println("0) Exit");
            String choice = prompt("Choice: ");
            switch (choice) {
                case "1":
                    try {
                        User u = login();
                        if (u != null) {
                            sessionFor(u);
                        }
                    } catch (EndOfInputException e) {
                        throw e;
                    }
                    break;
                case "0":
                    System.out.println("Bye.");
                    return;
                default:
                    System.out.println("Unknown option.");
            }
        }
    }

    /**
     * Thrown when stdin is exhausted — propagated up to the top-level
     * {@link #run()} so the console exits cleanly instead of looping
     * forever on a failed prompt.
     */
    private static final class EndOfInputException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    // -----------------------------------------------------------------
    //  Bootstrap & authentication
    // -----------------------------------------------------------------

    private void bootstrap() {
        UniversitySystem.getInstance().loadState();
        // loadState() may replace the singleton — re-fetch via the field.
        UniversitySystem live = UniversitySystem.getInstance();
        if (live.getUsers().isEmpty()) {
            Admin seed = UserFactory.createAdmin("Default", "Admin",
                    "admin@kbtu.kz", "admin", LocalDate.of(1990, 1, 1),
                    500_000, LocalDate.of(2024, 9, 1), "IT");
            live.addUser(seed);
            System.out.println("No saved state — seeded default admin (admin@kbtu.kz / admin).");
        } else {
            System.out.println("Loaded saved state: " + live.getUsers().size() + " users.");
        }
    }

    private User login() {
        String email = prompt("  Email: ");
        String password = prompt("  Password: ");
        try {
            User u = sys.authenticate(email, password);
            System.out.println("Welcome, " + u.getFullName() + " [" + u.getRole() + "]");
            return u;
        } catch (AuthenticationException e) {
            System.out.println("Login failed: " + e.getMessage());
            return null;
        }
    }

    private void sessionFor(User u) {
        if (u instanceof Admin) adminMenu((Admin) u);
        else if (u instanceof Manager) managerMenu((Manager) u);
        else if (u instanceof Teacher) teacherMenu((Teacher) u);
        else if (u instanceof Student) studentMenu((Student) u);
        else if (u instanceof ResearchEmployee) researchEmployeeMenu((ResearchEmployee) u);
        else System.out.println("No menu defined for this role.");
        u.logout();
        System.out.println("Logged out.");
    }

    // -----------------------------------------------------------------
    //  Admin menu
    // -----------------------------------------------------------------

    private void adminMenu(Admin admin) {
        while (true) {
            header("Admin menu");
            System.out.println("1) Add user");
            System.out.println("2) Remove user by id");
            System.out.println("3) List users");
            System.out.println("4) View logs");
            System.out.println("5) Save state");
            System.out.println("6) Search users by regex (bonus)");
            System.out.println("7) Search courses by regex (bonus)");
            System.out.println("0) Logout");
            switch (prompt("Choice: ")) {
                case "1": addUser(admin); break;
                case "2": admin.removeUser(prompt("  User id: ")); System.out.println("Removed."); break;
                case "3": listUsers(); break;
                case "4": viewLogs(admin); break;
                case "5": sys.saveState(); System.out.println("Saved."); break;
                case "6": searchUsersByRegex(); break;
                case "7": searchCoursesByRegex(); break;
                case "0": return;
                default: System.out.println("Unknown option.");
            }
        }
    }

    private void searchUsersByRegex() {
        try {
            List<User> matches = sys.findUsersByRegex(prompt("  Regex: "));
            if (matches.isEmpty()) {
                System.out.println("  (no matches)");
            } else {
                for (User u : matches) {
                    System.out.printf("  %-8s  %-18s  %s  <%s>%n",
                            u.getId(), u.getRole(), u.getFullName(), u.getEmail());
                }
            }
        } catch (java.util.regex.PatternSyntaxException e) {
            System.out.println("  Invalid regex: " + e.getDescription());
        }
    }

    private void searchCoursesByRegex() {
        try {
            List<Course> matches = sys.findCoursesByRegex(prompt("  Regex: "));
            if (matches.isEmpty()) {
                System.out.println("  (no matches)");
            } else {
                for (Course c : matches) {
                    System.out.printf("  %-8s  %s (credits=%d)%n",
                            c.getId(), c.getName(), c.getCredits());
                }
            }
        } catch (java.util.regex.PatternSyntaxException e) {
            System.out.println("  Invalid regex: " + e.getDescription());
        }
    }

    private void addUser(Admin admin) {
        System.out.println("  Roles: 1=Student  2=Teacher  3=Manager  4=Admin  5=ResearchEmployee");
        String roleChoice = prompt("  Role: ");
        String firstName = prompt("  First name: ");
        String lastName = prompt("  Last name: ");
        String email = prompt("  Email: ");
        String password = prompt("  Password: ");
        LocalDate dob = promptDate("  Date of birth (yyyy-mm-dd): ");
        try {
            User u;
            switch (roleChoice) {
                case "1": {
                    StudentYear year = pickEnum("  Year", StudentYear.values());
                    Major major = pickEnum("  Major", Major.values());
                    u = UserFactory.createStudent(firstName, lastName, email, password, dob, year, major);
                    break;
                }
                case "2": {
                    double salary = promptDouble("  Salary: ");
                    LocalDate hired = promptDate("  Date hired (yyyy-mm-dd): ");
                    String dept = prompt("  Department: ");
                    TeacherTitle title = pickEnum("  Title", TeacherTitle.values());
                    u = UserFactory.createTeacher(firstName, lastName, email, password, dob, salary, hired, dept, title);
                    break;
                }
                case "3": {
                    double salary = promptDouble("  Salary: ");
                    LocalDate hired = promptDate("  Date hired (yyyy-mm-dd): ");
                    String dept = prompt("  Department: ");
                    ManagerType type = pickEnum("  Manager type", ManagerType.values());
                    u = UserFactory.createManager(firstName, lastName, email, password, dob, salary, hired, dept, type);
                    break;
                }
                case "4": {
                    double salary = promptDouble("  Salary: ");
                    LocalDate hired = promptDate("  Date hired (yyyy-mm-dd): ");
                    String dept = prompt("  Department: ");
                    u = UserFactory.createAdmin(firstName, lastName, email, password, dob, salary, hired, dept);
                    break;
                }
                case "5": {
                    double salary = promptDouble("  Salary: ");
                    LocalDate hired = promptDate("  Date hired (yyyy-mm-dd): ");
                    String dept = prompt("  Department: ");
                    u = UserFactory.createResearchEmployee(firstName, lastName, email, password, dob, salary, hired, dept);
                    break;
                }
                default:
                    System.out.println("Unknown role.");
                    return;
            }
            admin.addUser(u);
            System.out.println("Created user id=" + u.getId());
        } catch (RuntimeException e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }

    private void listUsers() {
        for (User u : sys.getUsers()) {
            System.out.printf("  %-8s  %-18s  %s%n", u.getId(), u.getRole(), u.getFullName());
        }
    }

    private void viewLogs(Admin admin) {
        List<LogEntry> logs = admin.viewLogs();
        if (logs.isEmpty()) {
            System.out.println("  (no log entries)");
            return;
        }
        for (LogEntry e : logs) {
            System.out.printf("  [%s] %s — %s%n",
                    e.getTimestamp(), e.getAction(), e.getDetails());
        }
    }

    // -----------------------------------------------------------------
    //  Manager menu
    // -----------------------------------------------------------------

    private void managerMenu(Manager manager) {
        while (true) {
            header("Manager menu (" + manager.getManagerType() + ")");
            System.out.println("1) Open a new course for registration");
            System.out.println("2) Assign a course to a teacher");
            System.out.println("3) Approve a student's registration on a course");
            System.out.println("4) View incoming requests");
            System.out.println("5) Generate academic report");
            System.out.println("6) Save state");
            System.out.println("7) Generate weekly schedule (bonus)");
            System.out.println("0) Logout");
            switch (prompt("Choice: ")) {
                case "1": addCourse(manager); break;
                case "2": assignCourse(manager); break;
                case "3": approveRegistration(manager); break;
                case "4":
                    manager.viewRequests().forEach(r -> System.out.println("  " + r));
                    break;
                case "5":
                    System.out.println("--- " + manager.generateAcademicReport() + " ---");
                    break;
                case "6": sys.saveState(); System.out.println("Saved."); break;
                case "7": generateSchedule(); break;
                case "0": return;
                default: System.out.println("Unknown option.");
            }
        }
    }

    private void generateSchedule() {
        Course c = pickCourse();
        if (c == null) return;
        if (c.getInstructors().isEmpty()) {
            System.out.println("  Course has no instructor — assign one first.");
            return;
        }
        Teacher instructor = c.getInstructors().get(0);
        int nLectures = (int) promptDouble("  Number of weekly lectures: ");
        int nPractices = (int) promptDouble("  Number of weekly practice sessions: ");

        List<Lesson> lessons = new ArrayList<>();
        for (int i = 0; i < nLectures; i++) {
            Lesson l = new Lesson();
            l.setType(LessonType.LECTURE);
            l.setDurationMinutes(90);
            l.setInstructor(instructor);
            lessons.add(l);
        }
        for (int i = 0; i < nPractices; i++) {
            Lesson l = new Lesson();
            l.setType(LessonType.PRACTICE);
            l.setDurationMinutes(90);
            l.setInstructor(instructor);
            lessons.add(l);
        }

        Map<String, RoomType> rooms = new LinkedHashMap<>();
        rooms.put("A301", RoomType.LECTURE_HALL);
        rooms.put("A302", RoomType.LECTURE_HALL);
        rooms.put("B101", RoomType.LAB);
        rooms.put("B102", RoomType.LAB);
        rooms.put("C201", RoomType.SEMINAR_ROOM);

        try {
            new ScheduleGenerator().generate(lessons, rooms);
            c.setLessons(lessons);
            for (Lesson l : lessons) {
                System.out.printf("  %s %s-%s  %-4s %s%n",
                        l.getDay(), l.getStartTime(), l.getEndTime(),
                        l.getRoom(), l.getType());
            }
        } catch (ScheduleGenerator.ScheduleConflictException e) {
            System.out.println("  Failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("  Failed: " + e.getMessage());
        }
    }


    private void addCourse(Manager manager) {
        Course c = new Course();
        c.setId(prompt("  Course id (e.g. CS101): "));
        c.setName(prompt("  Course name: "));
        c.setCredits((int) promptDouble("  Credits: "));
        c.setIntendedYear(pickEnum("  Intended year", StudentYear.values()));
        c.setIntendedMajor(pickEnum("  Intended major", Major.values()));
        c.setSemester(pickEnum("  Semester", Semester.values()));
        c.setMaxStudents((int) promptDouble("  Max students: "));
        manager.addCourseForRegistration(c);
        System.out.println("Opened course " + c);
    }

    private void assignCourse(Manager manager) {
        Course c = pickCourse();
        if (c == null) return;
        Teacher t = pickUserOfType(Teacher.class, "teacher");
        if (t == null) return;
        try {
            manager.assignCourseToTeacher(c, t);
            System.out.println("Assigned " + c.getName() + " to " + t.getFullName());
        } catch (RuntimeException e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }

    private void approveRegistration(Manager manager) {
        Student s = pickUserOfType(Student.class, "student");
        if (s == null) return;
        Course c = pickCourse();
        if (c == null) return;
        try {
            manager.approveRegistration(s, c);
            System.out.println("Registration approved.");
        } catch (RuntimeException e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------
    //  Teacher menu
    // -----------------------------------------------------------------

    private void teacherMenu(Teacher teacher) {
        while (true) {
            header("Teacher menu (" + teacher.getTitle() + ")");
            System.out.println("1) View my courses");
            System.out.println("2) View students in a course");
            System.out.println("3) Put a mark");
            System.out.println("4) Publish a paper");
            System.out.println("5) Print my papers (sorted)");
            System.out.println("6) Save state");
            System.out.println("7) Marks report for a course (bonus)");
            System.out.println("8) Write recommendation letter (bonus)");
            System.out.println("9) Mark student attendance (bonus)");
            System.out.println("0) Logout");
            switch (prompt("Choice: ")) {
                case "1": teacher.viewCourses().forEach(c -> System.out.println("  " + c)); break;
                case "2": viewStudentsForTeacher(teacher); break;
                case "3": putMark(teacher); break;
                case "4": publishPaper(teacher); break;
                case "5": printPapers(teacher); break;
                case "6": sys.saveState(); System.out.println("Saved."); break;
                case "7": marksReportForTeacher(teacher); break;
                case "8": writeRecommendationLetter(teacher); break;
                case "9": markAttendance(teacher); break;
                case "0": return;
                default: System.out.println("Unknown option.");
            }
        }
    }

    private void marksReportForTeacher(Teacher teacher) {
        Course c = pickFromList(teacher.viewCourses(), "course");
        if (c == null) return;
        try {
            Report r = teacher.generateMarksReport(c);
            System.out.println();
            System.out.println("=== " + r.getTitle() + " ===");
            System.out.println(r.getContent());
        } catch (RuntimeException e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }

    private void writeRecommendationLetter(Teacher teacher) {
        Student s = pickUserOfType(Student.class, "student");
        if (s == null) return;
        RecommendationLetter letter = teacher.writeRecommendationLetter(s);
        System.out.println();
        System.out.println("--- Recommendation letter ---");
        System.out.println(letter.getBody());
        System.out.println("(issued on " + letter.getIssuedOn() + ")");
    }

    private void markAttendance(Teacher teacher) {
        Course c = pickFromList(teacher.viewCourses(), "course");
        if (c == null) return;
        Student s = pickUserOfType(Student.class, "student");
        if (s == null) return;
        LocalDate date = promptDate("  Date (yyyy-mm-dd): ");
        String p = prompt("  Present? (y/n): ");
        boolean present = p.equalsIgnoreCase("y") || p.equalsIgnoreCase("yes");
        try {
            teacher.markAttendance(s, c, date, present);
            System.out.println("Recorded.");
        } catch (RuntimeException e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }

    private void viewStudentsForTeacher(Teacher teacher) {
        Course c = pickFromList(teacher.viewCourses(), "course");
        if (c == null) return;
        teacher.viewStudents(c).forEach(s -> System.out.println("  " + s));
    }

    private void putMark(Teacher teacher) {
        Course c = pickFromList(teacher.viewCourses(), "course");
        if (c == null) return;
        Student s = pickUserOfType(Student.class, "student");
        if (s == null) return;
        Mark m = new Mark();
        m.setAtt1(promptDouble("  ATT1 (0..30): "));
        m.setAtt2(promptDouble("  ATT2 (0..30): "));
        m.setFinalScore(promptDouble("  Final (0..40): "));
        m.calculateTotal();
        try {
            teacher.putMark(s, c, m);
            System.out.printf("Recorded: total=%.1f letter=%s%n", m.getTotalScore(), m.getLetterGrade());
        } catch (RuntimeException e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------
    //  Student menu
    // -----------------------------------------------------------------

    private void studentMenu(Student student) {
        while (true) {
            header("Student menu (" + student.getYear() + " " + student.getMajor() + ")");
            System.out.println("1) View my marks");
            System.out.println("2) View transcript / GPA");
            System.out.println("3) Register for a course");
            System.out.println("4) Rate a teacher");
            System.out.println("5) View supervisor");
            System.out.println("6) Save state");
            System.out.println("7) View attendance rate for a course (bonus)");
            System.out.println("0) Logout");
            switch (prompt("Choice: ")) {
                case "1": student.viewMarks().forEach(m -> System.out.println("  " + m)); break;
                case "2":
                    System.out.printf("  GPA=%.2f  credits=%d%n",
                            student.getTranscript().calculateGPA(), student.getCurrentCredits());
                    break;
                case "3": registerForCourse(student); break;
                case "4": rateTeacher(student); break;
                case "5":
                    Researcher sup = student.getSupervisor();
                    System.out.println(sup == null ? "  (no supervisor)" : "  " + sup);
                    break;
                case "6": sys.saveState(); System.out.println("Saved."); break;
                case "7": viewAttendanceRate(student); break;
                case "0": return;
                default: System.out.println("Unknown option.");
            }
        }
    }

    private void viewAttendanceRate(Student student) {
        Course c = pickCourse();
        if (c == null) return;
        double rate = student.getAttendanceRate(c);
        System.out.printf("  Attendance rate for %s: %.0f%%%n", c.getName(), rate * 100);
    }

    private void registerForCourse(Student student) {
        Course c = pickCourse();
        if (c == null) return;
        try {
            student.registerForCourse(c);
            System.out.println("Registered on " + c.getName() + ". Credits=" + student.getCurrentCredits());
        } catch (RuntimeException e) {
            System.out.println("Failed: " + e.getClass().getSimpleName() + " — " + e.getMessage());
        }
    }

    private void rateTeacher(Student student) {
        Teacher t = pickUserOfType(Teacher.class, "teacher");
        if (t == null) return;
        int rating = (int) promptDouble("  Rating (1..5): ");
        try {
            student.rateTeacher(t, rating);
            System.out.printf("Rated. Average for %s now %.2f%n",
                    t.getFullName(), sys.averageTeacherRating(t));
        } catch (RuntimeException e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------
    //  Research employee menu
    // -----------------------------------------------------------------

    private void researchEmployeeMenu(ResearchEmployee re) {
        while (true) {
            header("Research Employee menu");
            System.out.println("1) View my papers");
            System.out.println("2) Publish a new paper");
            System.out.println("3) Print my papers sorted by citations / date / pages");
            System.out.println("4) Show h-index");
            System.out.println("5) Save state");
            System.out.println("0) Logout");
            switch (prompt("Choice: ")) {
                case "1": re.getPapers().forEach(p -> System.out.println("  " + p.getTitle())); break;
                case "2": publishPaper(re); break;
                case "3": printPapers(re); break;
                case "4": System.out.println("  h-index = " + re.getHIndex()); break;
                case "5": sys.saveState(); System.out.println("Saved."); break;
                case "0": return;
                default: System.out.println("Unknown option.");
            }
        }
    }

    // -----------------------------------------------------------------
    //  Researcher-shared helpers (Teacher, Student, ResearchEmployee)
    // -----------------------------------------------------------------

    private void publishPaper(Researcher r) {
        ResearchPaper p = new ResearchPaperBuilder()
                .title(prompt("  Title: "))
                .journal(prompt("  Journal: "))
                .doi(prompt("  DOI: "))
                .datePublished(promptDate("  Date published (yyyy-mm-dd): "))
                .citations((int) promptDouble("  Citations: "))
                .pages((int) promptDouble("  Pages: "))
                .abstractText(prompt("  Abstract: "))
                .keywords(Arrays.asList(prompt("  Keywords (comma-separated): ").split("\\s*,\\s*")))
                .build();
        r.publishPaper(p);
        System.out.println("Published.");
    }

    private void printPapers(Researcher r) {
        System.out.println("  Sort by: 1=citations  2=date  3=pages");
        Comparator<ResearchPaper> cmp;
        switch (prompt("  Choice: ")) {
            case "1": cmp = new ByCitationsComparator(); break;
            case "2": cmp = new ByDateComparator(); break;
            case "3": cmp = new ByPagesComparator(); break;
            default: System.out.println("Unknown option."); return;
        }
        r.printPapers(cmp);
    }

    // -----------------------------------------------------------------
    //  Generic pickers and I/O helpers
    // -----------------------------------------------------------------

    private Course pickCourse() {
        return pickFromList(sys.getCourses(), "course");
    }

    private <T extends User> T pickUserOfType(Class<T> type, String label) {
        List<T> matches = sys.getUsers().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .toList();
        return pickFromList(matches, label);
    }

    private <T> T pickFromList(List<T> items, String label) {
        if (items.isEmpty()) {
            System.out.println("  No " + label + "s available.");
            return null;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("  %d) %s%n", i + 1, items.get(i));
        }
        int idx;
        try {
            idx = Integer.parseInt(prompt("  Pick #: ")) - 1;
        } catch (NumberFormatException e) {
            System.out.println("  Not a number.");
            return null;
        }
        if (idx < 0 || idx >= items.size()) {
            System.out.println("  Out of range.");
            return null;
        }
        return items.get(idx);
    }

    private <E extends Enum<E>> E pickEnum(String label, E[] values) {
        System.out.print("  " + label + " [");
        for (int i = 0; i < values.length; i++) {
            System.out.print((i == 0 ? "" : ", ") + i + "=" + values[i]);
        }
        System.out.println("]");
        int idx;
        try {
            idx = Integer.parseInt(prompt("  #: "));
        } catch (NumberFormatException e) {
            return values[0];
        }
        return values[Math.floorMod(idx, values.length)];
    }

    private String prompt(String msg) {
        System.out.print(msg);
        if (!in.hasNextLine()) {
            throw new EndOfInputException();
        }
        return in.nextLine().trim();
    }

    private double promptDouble(String msg) {
        while (true) {
            String s = prompt(msg);
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("  Not a number, try again.");
            }
        }
    }

    private LocalDate promptDate(String msg) {
        while (true) {
            String s = prompt(msg);
            try {
                return LocalDate.parse(s);
            } catch (DateTimeParseException e) {
                System.out.println("  Use yyyy-mm-dd, try again.");
            }
        }
    }

    private void header(String title) {
        System.out.println();
        System.out.println("--- " + title + " ---");
    }
}
