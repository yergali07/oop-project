package edu.kbtu.university.users;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.kbtu.university.enums.Major;
import edu.kbtu.university.enums.StudentYear;
import edu.kbtu.university.enums.TeacherTitle;
import edu.kbtu.university.exceptions.AuthenticationException;
import edu.kbtu.university.exceptions.LowHIndexException;
import edu.kbtu.university.research.ResearchPaper;
import edu.kbtu.university.research.ResearchProject;
import edu.kbtu.university.system.UniversitySystem;

/**
 * Standalone smoke-test for the users module. Exercises:
 * <ol>
 *   <li>Factory-driven user creation with prefixed IDs</li>
 *   <li>Successful and failed authentication</li>
 *   <li>Admin user-management writing to the audit log</li>
 *   <li>Supervisor assignment rules (4th-year + h-index ≥ 3)</li>
 * </ol>
 * Not a JUnit test — runnable via {@code mvn exec:java} or directly from the
 * IDE so it does not depend on a test framework. The final {@code Main.java}
 * (Сержан) supersedes this once the system-wide demo is ready.
 */
public final class UsersSmokeTest {

    private UsersSmokeTest() {}

    public static void main(String[] args) {
        UniversitySystem.resetInstance();
        UniversitySystem sys = UniversitySystem.getInstance();
        int checks = 0;
        int failures = 0;

        // 1. Factory + admin add
        Admin admin = UserFactory.createAdmin("Yergali", "Usibaliev",
                "admin@kbtu.kz", "adminpass", LocalDate.of(1995, 1, 1),
                500_000, LocalDate.of(2024, 9, 1), "IT");
        sys.addUser(admin);
        checks++;
        if (!admin.getId().startsWith("EMP-")) {
            failures++; System.out.println("FAIL: admin id should start with EMP-: " + admin.getId());
        }

        Teacher teacher = UserFactory.createTeacher("Test", "Teacher",
                "t.teacher@kbtu.kz", "tpass", LocalDate.of(1980, 5, 5),
                400_000, LocalDate.of(2020, 9, 1), "CS", TeacherTitle.LECTURER);
        admin.addUser(teacher);
        checks++;
        if (!teacher.getId().startsWith("EMP-")) {
            failures++; System.out.println("FAIL: teacher id should start with EMP-: " + teacher.getId());
        }

        Student senior = UserFactory.createStudent("Senior", "Student",
                "senior@kbtu.kz", "spass", LocalDate.of(2003, 3, 3),
                StudentYear.FOURTH, Major.COMPUTER_SCIENCE);
        Student junior = UserFactory.createStudent("Junior", "Student",
                "junior@kbtu.kz", "jpass", LocalDate.of(2005, 3, 3),
                StudentYear.SECOND, Major.MATHEMATICS);
        admin.addUser(senior);
        admin.addUser(junior);
        checks++;
        if (!senior.getId().startsWith("BD-") || !junior.getId().startsWith("BD-")) {
            failures++; System.out.println("FAIL: student ids should start with BD-");
        }

        // 2. Auth — success
        checks++;
        try {
            sys.authenticate("admin@kbtu.kz", "adminpass");
        } catch (AuthenticationException e) {
            failures++; System.out.println("FAIL: correct password rejected: " + e.getMessage());
        }

        // 3. Auth — wrong password must throw
        checks++;
        try {
            sys.authenticate("admin@kbtu.kz", "wrong");
            failures++; System.out.println("FAIL: wrong password accepted");
        } catch (AuthenticationException expected) {
            // ok
        }

        // 4. Auth — unknown email must throw
        checks++;
        try {
            sys.authenticate("nobody@kbtu.kz", "x");
            failures++; System.out.println("FAIL: unknown email accepted");
        } catch (AuthenticationException expected) {
            // ok
        }

        // 5. Supervisor: 4th-year + h-index >= 3 → OK
        Researcher strong = strongResearcher(5);
        checks++;
        try {
            senior.setSupervisor(strong);
        } catch (RuntimeException e) {
            failures++; System.out.println("FAIL: valid supervisor rejected: " + e);
        }

        // 6. Supervisor: 4th-year + h-index 1 → LowHIndexException
        Researcher weak = strongResearcher(1);
        checks++;
        try {
            senior.setSupervisor(weak);
            failures++; System.out.println("FAIL: low h-index supervisor accepted");
        } catch (LowHIndexException expected) {
            // ok
        }

        // 7. Supervisor: 2nd-year → IllegalStateException
        checks++;
        try {
            junior.setSupervisor(strong);
            failures++; System.out.println("FAIL: 2nd-year supervisor accepted");
        } catch (IllegalStateException expected) {
            // ok
        }

        // 8. rateTeacher input validation
        checks++;
        try {
            senior.rateTeacher(teacher, 10);
            failures++; System.out.println("FAIL: rating 10 accepted");
        } catch (IllegalArgumentException expected) {
            // ok
        }

        // 9. Admin actions produced log entries: 3 addUser (teacher, senior,
        //    junior) + 1 successful LOGIN = 4. Failed auths do not log.
        checks++;
        int logCount = sys.getLogs().size();
        if (logCount < 4) {
            failures++; System.out.println("FAIL: expected >=4 log entries, got " + logCount);
        }

        // 10. removeUser
        checks++;
        admin.removeUser(junior.getId());
        if (sys.findUserById(junior.getId()) != null) {
            failures++; System.out.println("FAIL: junior not removed");
        }

        // 11. equals/hashCode by id
        checks++;
        Student copy = new Student();
        copy.setId(senior.getId());
        if (!senior.equals(copy) || senior.hashCode() != copy.hashCode()) {
            failures++; System.out.println("FAIL: equals/hashCode by id broken");
        }

        System.out.println();
        System.out.println("Users smoke test: " + (checks - failures) + "/" + checks + " checks passed");
        if (failures > 0) {
            System.exit(1);
        }
    }

    /** Anonymous Researcher with a fixed h-index, for supervisor-rule tests. */
    private static Researcher strongResearcher(int hIndex) {
        return new Researcher() {
            @Override public List<ResearchPaper> getPapers() { return Collections.emptyList(); }
            @Override public List<ResearchProject> getProjects() { return Collections.emptyList(); }
            @Override public int getHIndex() { return hIndex; }
            @Override public void publishPaper(ResearchPaper p) {}
            @Override public void joinProject(ResearchProject pr) {}
            @Override public void printPapers(Comparator<ResearchPaper> c) {}
        };
    }
}
