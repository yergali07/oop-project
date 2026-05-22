package edu.kbtu.university.academics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.kbtu.university.enums.Major;
import edu.kbtu.university.enums.Semester;
import edu.kbtu.university.enums.StudentYear;
import edu.kbtu.university.users.Student;
import edu.kbtu.university.users.Teacher;

/**
 * Represents an academic course in the university system.
 * Stores course metadata, instructors, enrolled students, prerequisites and lessons.
 */
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

    /**
     * Creates an empty course and initializes all internal lists.
     */
    public Course() {
        this.instructors = new ArrayList<>();
        this.enrolled = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
        this.lessons = new ArrayList<>();
    }

    /**
     * Creates a course with all academic fields.
     *
     * @param id unique course identifier
     * @param name course name
     * @param credits number of credits
     * @param intendedYear recommended student year
     * @param intendedMajor recommended major
     * @param semester course semester
     * @param instructors course instructors
     * @param enrolled enrolled students
     * @param prerequisites prerequisite courses
     * @param lessons course lessons
     * @param maxStudents maximum number of students
     */
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

    /**
     * Returns the unique course identifier.
     *
     * @return course identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique course identifier.
     *
     * @param id course identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the course name.
     *
     * @return course name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the course name.
     *
     * @param name course name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the number of credits for the course.
     *
     * @return number of credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Sets the number of credits for the course.
     *
     * @param credits number of credits
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Returns the recommended student year for this course.
     *
     * @return recommended student year
     */
    public StudentYear getIntendedYear() {
        return intendedYear;
    }

    /**
     * Sets the recommended student year for this course.
     *
     * @param intendedYear recommended student year
     */
    public void setIntendedYear(StudentYear intendedYear) {
        this.intendedYear = intendedYear;
    }

    /**
     * Returns the recommended major for this course.
     *
     * @return recommended major
     */
    public Major getIntendedMajor() {
        return intendedMajor;
    }

    /**
     * Sets the recommended major for this course.
     *
     * @param intendedMajor recommended major
     */
    public void setIntendedMajor(Major intendedMajor) {
        this.intendedMajor = intendedMajor;
    }

    /**
     * Returns the semester in which the course is offered.
     *
     * @return course semester
     */
    public Semester getSemester() {
        return semester;
    }

    /**
     * Sets the semester in which the course is offered.
     *
     * @param semester course semester
     */
    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    /**
     * Returns the list of course instructors.
     *
     * @return course instructors
     */
    public List<Teacher> getInstructors() {
        return instructors;
    }

    /**
     * Sets the list of course instructors.
     *
     * @param instructors course instructors
     */
    public void setInstructors(List<Teacher> instructors) {
        this.instructors = instructors;
    }

    /**
     * Returns the list of enrolled students.
     *
     * @return enrolled students
     */
    public List<Student> getEnrolled() {
        return enrolled;
    }

    /**
     * Sets the list of enrolled students.
     *
     * @param enrolled enrolled students
     */
    public void setEnrolled(List<Student> enrolled) {
        this.enrolled = enrolled;
    }

    /**
     * Returns the list of prerequisite courses.
     *
     * @return prerequisite courses
     */
    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    /**
     * Sets the list of prerequisite courses.
     *
     * @param prerequisites prerequisite courses
     */
    public void setPrerequisites(List<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    /**
     * Returns the list of course lessons.
     *
     * @return course lessons
     */
    public List<Lesson> getLessons() {
        return lessons;
    }

    /**
     * Sets the list of course lessons.
     *
     * @param lessons course lessons
     */
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    /**
     * Returns the maximum number of students allowed on the course.
     *
     * @return maximum number of students
     */
    public int getMaxStudents() {
        return maxStudents;
    }

    /**
     * Sets the maximum number of students allowed on the course.
     *
     * @param maxStudents maximum number of students
     */
    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    /**
     * Adds an instructor to the course if the instructor is not already assigned.
     *
     * @param t instructor to add
     */
    public void addInstructor(Teacher t) {
        if (t != null && !instructors.contains(t)) {
            instructors.add(t);
        }
    }

    /**
     * Adds a student to the course if there is free capacity.
     *
     * @param s student to enroll
     */
    public void addStudent(Student s) {
        if (s != null && !isFull() && !enrolled.contains(s)) {
            enrolled.add(s);
        }
    }

    /**
     * Checks whether the student has passed all prerequisite courses.
     *
     * @param s student whose transcript is checked
     * @return {@code true} if all prerequisites are present and passed; otherwise {@code false}
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
     * Checks whether the course has reached its maximum capacity.
     *
     * @return {@code true} if the course is full; otherwise {@code false}
     */
    public boolean isFull() {
        return enrolled.size() >= maxStudents;
    }

    /**
     * Compares courses by their unique identifier.
     *
     * @param o object to compare with
     * @return {@code true} if both objects are courses with the same id; otherwise {@code false}
     */
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

    /**
     * Returns a hash code based on the course identifier.
     *
     * @return hash code of the course
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
