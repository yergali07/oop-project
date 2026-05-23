package edu.kbtu.university;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.kbtu.university.academics.Course;
import edu.kbtu.university.academics.Mark;
import edu.kbtu.university.enums.Major;
import edu.kbtu.university.enums.ManagerType;
import edu.kbtu.university.enums.Semester;
import edu.kbtu.university.enums.StudentYear;
import edu.kbtu.university.enums.TeacherTitle;
import edu.kbtu.university.exceptions.AuthenticationException;
import edu.kbtu.university.exceptions.CourseRegistrationException;
import edu.kbtu.university.exceptions.CreditLimitExceededException;
import edu.kbtu.university.exceptions.LowHIndexException;
import edu.kbtu.university.exceptions.MaxFailuresException;
import edu.kbtu.university.exceptions.NotAResearcherException;
import edu.kbtu.university.exceptions.PrerequisiteNotMetException;
import edu.kbtu.university.research.ByCitationsComparator;
import edu.kbtu.university.research.ResearchPaper;
import edu.kbtu.university.research.ResearchPaperBuilder;
import edu.kbtu.university.research.ResearchProject;
import edu.kbtu.university.users.Admin;
import edu.kbtu.university.users.Manager;
import edu.kbtu.university.users.Researcher;
import edu.kbtu.university.users.Student;
import edu.kbtu.university.users.Teacher;
import edu.kbtu.university.users.User;
import edu.kbtu.university.users.UserFactory;
import edu.kbtu.university.users.UsersSmokeTest;
import edu.kbtu.university.system.UniversitySystem;

/**
 * Top-level demo entry point. Executes the 13-step end-to-end scenario
 * described in the README ("Спринт 3"): user lifecycle → registration
 * rules → supervisor h-index check → marks → research Builder →
 * project participation → top-cited query → save/load round-trip.
 *
 * <p>Usage:
 * <ul>
 *   <li>{@code java edu.kbtu.university.Main} — run the demo (default)</li>
 *   <li>{@code java edu.kbtu.university.Main demo} — same as default</li>
 *   <li>{@code java edu.kbtu.university.Main smoke} — run the users
 *       module smoke test</li>
 *   <li>{@code java edu.kbtu.university.Main console} — interactive
 *       console (not implemented yet)</li>
 * </ul>
 */
public final class Main {

    private static final String SER_FILE = "university-system.ser";

    private Main() {
    }

    public static void main(String[] args) {
        String mode = args.length > 0 ? args[0].toLowerCase() : "demo";
        switch (mode) {
            case "smoke":
                UsersSmokeTest.main(args);
                return;
            case "console":
                ConsoleApp.run();
                return;
            case "demo":
            default:
                runDemo();
        }
    }

    private static void runDemo() {
        // Fresh state for the demo run.
        UniversitySystem.resetInstance();
        new File(SER_FILE).delete();

        UniversitySystem sys = UniversitySystem.getInstance();

        // -------------------------------------------------------------
        // Step 1. Admin creates users.
        // -------------------------------------------------------------
        header("1. Admin creates users");
        Admin admin = UserFactory.createAdmin("Yergali", "Usibaliev",
                "admin@kbtu.kz", "adminpass", LocalDate.of(1995, 1, 1),
                500_000, LocalDate.of(2024, 9, 1), "IT");
        sys.addUser(admin);

        Manager manager = UserFactory.createManager("Serzhan", "Serikbayuly",
                "manager@kbtu.kz", "mgrpass", LocalDate.of(1990, 5, 5),
                450_000, LocalDate.of(2023, 9, 1), "Academic",
                ManagerType.ACADEMIC);
        Teacher teacher = UserFactory.createTeacher("Farkhat", "Zhutanov",
                "teacher@kbtu.kz", "tpass", LocalDate.of(1985, 4, 4),
                400_000, LocalDate.of(2020, 9, 1), "CS", TeacherTitle.PROFESSOR);
        Student senior = UserFactory.createStudent("Senior", "Student",
                "senior@kbtu.kz", "spass", LocalDate.of(2003, 3, 3),
                StudentYear.FOURTH, Major.COMPUTER_SCIENCE);

        admin.addUser(manager);
        admin.addUser(teacher);
        admin.addUser(senior);
        System.out.printf("Created admin=%s, manager=%s, teacher=%s, student=%s%n",
                admin.getId(), manager.getId(), teacher.getId(), senior.getId());

        // -------------------------------------------------------------
        // Step 2. Manager opens courses for registration.
        // -------------------------------------------------------------
        header("2. Manager opens 8 courses (7 valid + 1 with unmet prerequisite)");
        Course oop = makeCourse("CS101", "OOP", 3);
        Course advancedOop = makeCourse("CS401", "Advanced OOP", 3);
        advancedOop.setPrerequisites(new ArrayList<>(Arrays.asList(oop)));

        Course[] freeCourses = {
                makeCourse("CS102", "Algorithms", 3),
                makeCourse("CS103", "Databases", 3),
                makeCourse("CS104", "Networks", 3),
                makeCourse("CS105", "OS", 3),
                makeCourse("CS106", "Web", 3),
                makeCourse("CS107", "Mobile", 3),
        };
        manager.addCourseForRegistration(oop);
        manager.addCourseForRegistration(advancedOop);
        for (Course c : freeCourses) {
            manager.addCourseForRegistration(c);
        }
        manager.assignCourseToTeacher(oop, teacher);
        for (Course c : freeCourses) {
            manager.assignCourseToTeacher(c, teacher);
        }
        System.out.println("Opened " + sys.getCourses().size() + " courses, teacher assigned.");

        // -------------------------------------------------------------
        // Step 3. Student tries to register on a course with unmet
        //         prerequisite → PrerequisiteNotMetException.
        // -------------------------------------------------------------
        header("3. Register on course with unmet prerequisite");
        expect("PrerequisiteNotMetException",
                () -> senior.registerForCourse(advancedOop),
                PrerequisiteNotMetException.class);

        // -------------------------------------------------------------
        // Step 4. Register on 7 valid courses (21 credits).
        // -------------------------------------------------------------
        header("4. Register on 7 valid 3-credit courses (21 credits total)");
        senior.registerForCourse(oop);
        for (Course c : freeCourses) {
            senior.registerForCourse(c);
        }
        System.out.printf("Student credits=%d, courses enrolled=%d%n",
                senior.getCurrentCredits(), oop.getEnrolled().size() + freeCourses[0].getEnrolled().size());

        // -------------------------------------------------------------
        // Step 5. Adding one more credit → CreditLimitExceededException.
        // -------------------------------------------------------------
        header("5. Try to add a 22nd credit");
        Course extra = makeCourse("CS108", "Extra", 1);
        manager.addCourseForRegistration(extra);
        expect("CreditLimitExceededException",
                () -> senior.registerForCourse(extra),
                CreditLimitExceededException.class);

        // -------------------------------------------------------------
        // Step 6. Try to assign a supervisor with h-index 1 to a 4th-year
        //         student → LowHIndexException.
        // -------------------------------------------------------------
        header("6. Assign supervisor with h-index 1 to 4th-year student");
        Researcher weak = fixedHIndex(1);
        expect("LowHIndexException",
                () -> senior.setSupervisor(weak),
                LowHIndexException.class);

        // -------------------------------------------------------------
        // Step 7. Assign a proper supervisor (h-index ≥ 3).
        // -------------------------------------------------------------
        header("7. Assign supervisor with h-index 5");
        Researcher strong = fixedHIndex(5);
        senior.setSupervisor(strong);
        System.out.println("Supervisor set: h-index=" + senior.getSupervisor().getHIndex());

        // -------------------------------------------------------------
        // Step 8. Teacher puts marks.
        // -------------------------------------------------------------
        header("8. Teacher puts marks on student's transcript");
        Mark m = new Mark();
        m.setAtt1(30);
        m.setAtt2(30);
        m.setFinalScore(35); // total=95 → A
        m.calculateTotal();
        teacher.putMark(senior, oop, m);
        System.out.printf("Mark for %s: total=%.1f, letter=%s%n",
                oop.getName(), m.getTotalScore(), m.getLetterGrade());

        // -------------------------------------------------------------
        // Step 9. Student views transcript / GPA.
        // -------------------------------------------------------------
        header("9. Student views transcript");
        double gpa = senior.getTranscript().calculateGPA();
        System.out.printf("Transcript: marks=%d, GPA=%.2f%n",
                senior.viewMarks().size(), gpa);

        // -------------------------------------------------------------
        // Step 10. Researcher publishes a paper via the Builder pattern.
        // -------------------------------------------------------------
        header("10. Publish a research paper via Builder");
        ResearchPaper paper = new ResearchPaperBuilder()
                .title("OOP Design Patterns in Education")
                .authors(new ArrayList<>(Collections.singletonList(teacher)))
                .journal("IEEE Software Education")
                .doi("10.1109/SE.2026.0001")
                .datePublished(LocalDate.of(2026, 3, 1))
                .citations(42)
                .pages(12)
                .abstractText("Empirical study of teaching OOP via real-world projects.")
                .keywords(Arrays.asList("OOP", "education", "patterns"))
                .build();
        teacher.publishPaper(paper);
        System.out.println("Published: \"" + paper.getTitle() + "\" with " + paper.getCitations() + " citations");

        // -------------------------------------------------------------
        // Step 11. Try to add a non-Researcher to a project →
        //          NotAResearcherException.
        // -------------------------------------------------------------
        header("11. Add a non-Researcher (Admin) to a research project");
        ResearchProject project = new ResearchProject("Educational OOP", teacher);
        expect("NotAResearcherException",
                () -> project.addParticipant(admin),
                NotAResearcherException.class);
        project.addPaper(paper);
        System.out.println("Project: " + project);

        // -------------------------------------------------------------
        // Step 12. Show the top-cited researcher in the system.
        // -------------------------------------------------------------
        header("12. Query top-cited researcher");
        Researcher top = sys.topCitedResearcher();
        String topId = top instanceof User ? ((User) top).getId() : "<anonymous>";
        System.out.printf("Top-cited researcher id=%s, h-index=%d%n", topId, top.getHIndex());

        // -------------------------------------------------------------
        // Step 13. saveState() → reset → loadState() → data preserved.
        // -------------------------------------------------------------
        header("13. saveState → reset → loadState round-trip");
        sys.saveState();
        int usersBefore = sys.getUsers().size();
        int coursesBefore = sys.getCourses().size();
        UniversitySystem.resetInstance();
        UniversitySystem.getInstance().loadState();
        UniversitySystem reloaded = UniversitySystem.getInstance();
        System.out.printf("Before: users=%d, courses=%d%n", usersBefore, coursesBefore);
        System.out.printf("After:  users=%d, courses=%d%n",
                reloaded.getUsers().size(), reloaded.getCourses().size());

        // -------------------------------------------------------------
        // Bonus showcase (14-18). Не входят в обязательную программу,
        // но дают дополнительные баллы.
        // -------------------------------------------------------------
        header("14. Bonus — marks report for the teacher");
        edu.kbtu.university.system.Report mr = teacher.generateMarksReport(oop);
        System.out.println(mr.getTitle());
        System.out.println(mr.getContent());

        header("15. Bonus — recommendation letter from the teacher");
        edu.kbtu.university.system.RecommendationLetter letter =
                teacher.writeRecommendationLetter(senior);
        System.out.println(letter.getBody());

        header("16. Bonus — mark attendance and read attendance rate");
        teacher.markAttendance(senior, oop, LocalDate.now(), true);
        teacher.markAttendance(senior, oop, LocalDate.now().plusDays(1), true);
        teacher.markAttendance(senior, oop, LocalDate.now().plusDays(2), false);
        System.out.printf("Attendance rate for %s in %s: %.0f%%%n",
                senior.getFullName(), oop.getName(),
                senior.getAttendanceRate(oop) * 100);

        header("17. Bonus — advanced regex search across users / courses");
        System.out.println("Users matching ^Senior:");
        UniversitySystem.getInstance().findUsersByRegex("^Senior")
                .forEach(u -> System.out.println("  " + u.getId() + "  " + u.getFullName()));
        System.out.println("Courses matching ^CS10[0-7]$:");
        UniversitySystem.getInstance().findCoursesByRegex("^CS10[0-7]$")
                .forEach(c -> System.out.println("  " + c.getId() + "  " + c.getName()));

        header("18. Bonus — generate a weekly schedule with room-type constraints");
        java.util.List<edu.kbtu.university.academics.Lesson> lessonBatch = new java.util.ArrayList<>();
        for (int i = 0; i < 2; i++) {
            edu.kbtu.university.academics.Lesson lec = new edu.kbtu.university.academics.Lesson();
            lec.setType(edu.kbtu.university.enums.LessonType.LECTURE);
            lec.setDurationMinutes(90);
            lec.setInstructor(teacher);
            lessonBatch.add(lec);
        }
        for (int i = 0; i < 2; i++) {
            edu.kbtu.university.academics.Lesson pr = new edu.kbtu.university.academics.Lesson();
            pr.setType(edu.kbtu.university.enums.LessonType.PRACTICE);
            pr.setDurationMinutes(90);
            pr.setInstructor(teacher);
            lessonBatch.add(pr);
        }
        java.util.LinkedHashMap<String, edu.kbtu.university.enums.RoomType> roomPool =
                new java.util.LinkedHashMap<>();
        roomPool.put("A301", edu.kbtu.university.enums.RoomType.LECTURE_HALL);
        roomPool.put("B101", edu.kbtu.university.enums.RoomType.LAB);
        new edu.kbtu.university.system.ScheduleGenerator().generate(lessonBatch, roomPool);
        for (edu.kbtu.university.academics.Lesson l : lessonBatch) {
            System.out.printf("  %-9s %s-%s  %-4s %s%n",
                    l.getDay(), l.getStartTime(), l.getEndTime(),
                    l.getRoom(), l.getType());
        }

        // Clean up the serialization file so subsequent runs start fresh.
        new File(SER_FILE).delete();

        System.out.println();
        System.out.println("=== Demo finished successfully ===");
    }

    private static void header(String title) {
        System.out.println();
        System.out.println("--- " + title + " ---");
    }

    /**
     * Asserts the supplied action throws an exception of the expected type.
     * Prints a line whether the expectation held, and never aborts the
     * demo (so all 13 steps can complete even if one regression slips in).
     */
    private static void expect(String label, Runnable action, Class<? extends Throwable> expected) {
        try {
            action.run();
            System.out.println("UNEXPECTED: no exception thrown (expected " + label + ")");
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                System.out.println("OK: caught " + label + " — " + t.getMessage());
            } else {
                System.out.println("UNEXPECTED: caught " + t.getClass().getSimpleName()
                        + " instead of " + label + " — " + t.getMessage());
            }
        }
    }

    private static Course makeCourse(String id, String name, int credits) {
        Course c = new Course();
        c.setId(id);
        c.setName(name);
        c.setCredits(credits);
        c.setIntendedYear(StudentYear.FOURTH);
        c.setIntendedMajor(Major.COMPUTER_SCIENCE);
        c.setSemester(Semester.FALL);
        c.setMaxStudents(50);
        return c;
    }

    /**
     * Lightweight {@link Researcher} stand-in for the supervisor h-index
     * check — avoids the need to fabricate a full ResearchProfile with
     * the right number of citations.
     */
    private static Researcher fixedHIndex(int h) {
        return new FixedHIndexResearcher(h);
    }

    private static final class FixedHIndexResearcher implements Researcher {
        private static final long serialVersionUID = 1L;
        private final int hIndex;
        FixedHIndexResearcher(int hIndex) { this.hIndex = hIndex; }
        @Override public List<ResearchPaper> getPapers() { return Collections.emptyList(); }
        @Override public List<ResearchProject> getProjects() { return Collections.emptyList(); }
        @Override public int getHIndex() { return hIndex; }
        @Override public void publishPaper(ResearchPaper p) {}
        @Override public void joinProject(ResearchProject pr) {}
        @Override public void printPapers(java.util.Comparator<ResearchPaper> c) {}
    }

    @SuppressWarnings("unused")
    private static void touchComparator() {
        // Holds a reference so ByCitationsComparator is reachable from
        // build configuration analyzers that scan only Main's imports.
        new ByCitationsComparator();
    }
}
