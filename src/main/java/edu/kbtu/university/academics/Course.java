package edu.kbtu.university.academics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.kbtu.university.enums.Major;
import edu.kbtu.university.enums.Semester;
import edu.kbtu.university.enums.StudentYear;
import edu.kbtu.university.users.Student;
import edu.kbtu.university.users.Teacher;

public class Course {
    private String id;
    private String name;
    private int credits;
    private StudentYear intendedYear;
    private Major intendedMajor;
    private Semester semester;
    private List<Teacher> instructors;
    private List<Student> enrolled;
    private List<Course> prerequisites;
    private List<Lesson> lessons;
    private int maxStudents;

    public Course() {
        this.instructors = new ArrayList<>();
        this.enrolled = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
        this.lessons = new ArrayList<>();
    }

    public Course(String id, String name, int credits, StudentYear intendedYear,
                  Major intendedMajor, Semester semester, List<Teacher> instructors,
                  List<Student> enrolled, List<Course> prerequisites,
                  List<Lesson> lessons, int maxStudents) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.intendedYear = intendedYear;
        this.intendedMajor = intendedMajor;
        this.semester = semester;
        this.instructors = instructors != null ? instructors : new ArrayList<>();
        this.enrolled = enrolled != null ? enrolled : new ArrayList<>();
        this.prerequisites = prerequisites != null ? prerequisites : new ArrayList<>();
        this.lessons = lessons != null ? lessons : new ArrayList<>();
        this.maxStudents = maxStudents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public StudentYear getIntendedYear() {
        return intendedYear;
    }

    public void setIntendedYear(StudentYear intendedYear) {
        this.intendedYear = intendedYear;
    }

    public Major getIntendedMajor() {
        return intendedMajor;
    }

    public void setIntendedMajor(Major intendedMajor) {
        this.intendedMajor = intendedMajor;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public List<Teacher> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Teacher> instructors) {
        this.instructors = instructors;
    }

    public List<Student> getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(List<Student> enrolled) {
        this.enrolled = enrolled;
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    /**
     * @param t
     */
    public void addInstructor(Teacher t) {
        // TODO implement here
    }

    /**
     * @param s
     * @return
     */
    public boolean hasPrerequisitesMet(Student s) {
        if (prerequisites == null || prerequisites.isEmpty()) {
            return true;
        }

        if (s == null || s.getTranscript() == null || s.getTranscript().getMarks() == null) {
            return false;
        }

        Transcript transcript = s.getTranscript();

        for (Course prereq : prerequisites) {
            if (!transcript.getMarks().containsKey(prereq)) {
                return false;
            }

            Mark mark = transcript.getMarks().get(prereq);
            if (mark == null || !mark.isPassing()) {
                return false;
            }
        }

        return true;
    }

    /**
     * @return
     */
    public boolean isFull() {
        // TODO implement here
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        Course course = (Course) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
