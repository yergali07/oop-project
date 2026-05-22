package edu.kbtu.university.users;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import edu.kbtu.university.enums.ManagerType;
import edu.kbtu.university.enums.Major;
import edu.kbtu.university.enums.Role;
import edu.kbtu.university.enums.StudentYear;
import edu.kbtu.university.enums.TeacherTitle;

/**
 * Factory (pattern <strong>Factory</strong>) for creating concrete
 * {@link User} subclasses with auto-generated, prefix-tagged IDs:
 * <ul>
 *   <li>{@code BD-####} — bachelor students</li>
 *   <li>{@code MP-####} — masters students</li>
 *   <li>{@code PHD-####} — PhD students</li>
 *   <li>{@code EMP-####} — every employee subtype (teacher, manager,
 *       admin, research employee)</li>
 * </ul>
 */
public final class UserFactory {

    private static final AtomicInteger BACHELOR_SEQ = new AtomicInteger(0);
    private static final AtomicInteger MASTER_SEQ = new AtomicInteger(0);
    private static final AtomicInteger PHD_SEQ = new AtomicInteger(0);
    private static final AtomicInteger EMPLOYEE_SEQ = new AtomicInteger(0);

    private UserFactory() {
    }

    private static String studentId(StudentYear year) {
        // Years 1-4 are bachelors per ТЗ; masters/PhD live outside the
        // StudentYear enum and are created via the dedicated helpers below.
        return String.format("BD-%04d", BACHELOR_SEQ.incrementAndGet());
    }

    private static String employeeId() {
        return String.format("EMP-%04d", EMPLOYEE_SEQ.incrementAndGet());
    }

    public static Student createStudent(String firstName, String lastName, String email,
                                        String password, LocalDate dob,
                                        StudentYear year, Major major) {
        return new Student(studentId(year), firstName, lastName, email, password, dob, year, major);
    }

    public static Student createMastersStudent(String firstName, String lastName, String email,
                                               String password, LocalDate dob, Major major) {
        String id = String.format("MP-%04d", MASTER_SEQ.incrementAndGet());
        Student s = new Student(id, firstName, lastName, email, password, dob, null, major);
        return s;
    }

    public static Student createPhdStudent(String firstName, String lastName, String email,
                                           String password, LocalDate dob, Major major) {
        String id = String.format("PHD-%04d", PHD_SEQ.incrementAndGet());
        Student s = new Student(id, firstName, lastName, email, password, dob, null, major);
        return s;
    }

    public static Teacher createTeacher(String firstName, String lastName, String email,
                                        String password, LocalDate dob,
                                        double salary, LocalDate dateHired, String department,
                                        TeacherTitle title) {
        return new Teacher(employeeId(), firstName, lastName, email, password, dob,
                salary, dateHired, department, title);
    }

    public static Manager createManager(String firstName, String lastName, String email,
                                        String password, LocalDate dob,
                                        double salary, LocalDate dateHired, String department,
                                        ManagerType type) {
        return new Manager(employeeId(), firstName, lastName, email, password, dob,
                salary, dateHired, department, type);
    }

    public static Admin createAdmin(String firstName, String lastName, String email,
                                    String password, LocalDate dob,
                                    double salary, LocalDate dateHired, String department) {
        return new Admin(employeeId(), firstName, lastName, email, password, dob,
                salary, dateHired, department);
    }

    public static ResearchEmployee createResearchEmployee(String firstName, String lastName, String email,
                                                          String password, LocalDate dob,
                                                          double salary, LocalDate dateHired, String department) {
        return new ResearchEmployee(employeeId(), firstName, lastName, email, password, dob,
                salary, dateHired, department);
    }

    /**
     * Generic role-driven factory. The {@code args} map provides per-role
     * constructor inputs. Required keys per role:
     * <ul>
     *   <li>Common: {@code firstName, lastName, email, password, dob}</li>
     *   <li>Student: {@code year} ({@link StudentYear}), {@code major}
     *       ({@link Major})</li>
     *   <li>Employee subtypes: {@code salary, dateHired, department}</li>
     *   <li>Teacher: additionally {@code title} ({@link TeacherTitle})</li>
     *   <li>Manager: additionally {@code managerType} ({@link ManagerType})</li>
     * </ul>
     */
    public static User createUser(Role role, Map<String, Object> args) {
        if (role == null) {
            throw new IllegalArgumentException("role must not be null");
        }
        if (args == null) {
            throw new IllegalArgumentException("args must not be null");
        }
        String firstName = (String) args.get("firstName");
        String lastName = (String) args.get("lastName");
        String email = (String) args.get("email");
        String password = (String) args.get("password");
        LocalDate dob = (LocalDate) args.get("dob");

        switch (role) {
            case STUDENT:
                return createStudent(firstName, lastName, email, password, dob,
                        (StudentYear) args.get("year"), (Major) args.get("major"));
            case TEACHER:
                return createTeacher(firstName, lastName, email, password, dob,
                        ((Number) args.get("salary")).doubleValue(),
                        (LocalDate) args.get("dateHired"),
                        (String) args.get("department"),
                        (TeacherTitle) args.get("title"));
            case MANAGER:
                return createManager(firstName, lastName, email, password, dob,
                        ((Number) args.get("salary")).doubleValue(),
                        (LocalDate) args.get("dateHired"),
                        (String) args.get("department"),
                        (ManagerType) args.get("managerType"));
            case ADMIN:
                return createAdmin(firstName, lastName, email, password, dob,
                        ((Number) args.get("salary")).doubleValue(),
                        (LocalDate) args.get("dateHired"),
                        (String) args.get("department"));
            case RESEARCH_EMPLOYEE:
                return createResearchEmployee(firstName, lastName, email, password, dob,
                        ((Number) args.get("salary")).doubleValue(),
                        (LocalDate) args.get("dateHired"),
                        (String) args.get("department"));
            default:
                throw new IllegalArgumentException("Unsupported role: " + role);
        }
    }
}
