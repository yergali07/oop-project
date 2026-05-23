package edu.kbtu.university.system;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.kbtu.university.academics.Course;
import edu.kbtu.university.academics.Mark;
import edu.kbtu.university.users.Student;

/**
 * Builds {@link Report} instances for different facets of the system —
 * academic performance per course, marks summary, and research output.
 * Used by {@link edu.kbtu.university.users.Manager#generateAcademicReport()}
 * and {@link edu.kbtu.university.users.Teacher#generateMarksReport(Course)}.
 */
public class ReportGenerator {

    public ReportGenerator() {
    }

    public Report academicReport(Course c) {
        Report report = new Report();
        report.setTitle(c == null ? "Academic report" : "Academic report for " + c);
        report.setContent(c == null
                ? "No course data available."
                : "Course: " + c + System.lineSeparator()
                        + "Enrolled students: " + c.getEnrolled().size() + System.lineSeparator()
                        + "Instructors: " + c.getInstructors().size());
        return report;
    }

    /**
     * Aggregates marks for the supplied course across all enrolled students:
     * count of submitted marks, average total score, letter-grade histogram,
     * pass/fail split, and best/worst student.
     *
     * @param c course to aggregate (may be {@code null} for a placeholder
     *          report)
     * @return formatted {@link Report}
     */
    public Report marksReport(Course c) {
        Report report = new Report();
        if (c == null) {
            report.setTitle("Marks report");
            report.setContent("No course supplied.");
            return report;
        }
        report.setTitle("Marks report for " + c.getName());

        int enrolled = c.getEnrolled().size();
        int graded = 0;
        double sumTotal = 0.0;
        double bestScore = Double.NEGATIVE_INFINITY;
        double worstScore = Double.POSITIVE_INFINITY;
        String bestStudent = "-";
        String worstStudent = "-";
        int passing = 0;
        Map<String, Integer> histogram = new LinkedHashMap<>();
        for (String g : new String[]{"A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"}) {
            histogram.put(g, 0);
        }

        for (Student s : c.getEnrolled()) {
            if (s.getTranscript() == null || s.getTranscript().getMarks() == null) continue;
            Mark m = s.getTranscript().getMarks().get(c);
            if (m == null) continue;
            graded++;
            double total = m.getTotalScore();
            sumTotal += total;
            if (m.isPassing()) passing++;
            histogram.merge(m.getLetterGrade(), 1, Integer::sum);
            if (total > bestScore) { bestScore = total; bestStudent = s.getFullName() + " (" + s.getId() + ")"; }
            if (total < worstScore) { worstScore = total; worstStudent = s.getFullName() + " (" + s.getId() + ")"; }
        }

        StringBuilder body = new StringBuilder();
        body.append("Enrolled: ").append(enrolled).append(System.lineSeparator());
        body.append("Graded:   ").append(graded).append(System.lineSeparator());
        if (graded > 0) {
            body.append(String.format("Average total: %.2f%n", sumTotal / graded));
            body.append(String.format("Passing: %d (%.0f%%)%n", passing, 100.0 * passing / graded));
            body.append("Best:  ").append(bestStudent).append(String.format(" — %.1f%n", bestScore));
            body.append("Worst: ").append(worstStudent).append(String.format(" — %.1f%n", worstScore));
            body.append("Letter-grade histogram:").append(System.lineSeparator());
            for (Map.Entry<String, Integer> e : histogram.entrySet()) {
                if (e.getValue() == 0) continue;
                body.append("  ").append(String.format("%-2s", e.getKey()))
                        .append(" : ").append(e.getValue()).append(System.lineSeparator());
            }
        } else {
            body.append("No marks recorded yet for this course.").append(System.lineSeparator());
        }
        report.setContent(body.toString());
        return report;
    }

    public Report researchReport() {
        Report report = new Report();
        report.setTitle("Research report");
        report.setContent("Research report generated from current university research profiles.");
        return report;
    }
}
