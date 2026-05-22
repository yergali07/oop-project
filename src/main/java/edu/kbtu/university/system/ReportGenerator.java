package edu.kbtu.university.system;

import edu.kbtu.university.academics.Course;

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

    public Report marksReport(Course c) {
        Report report = new Report();
        report.setTitle(c == null ? "Marks report" : "Marks report for " + c);
        report.setContent(c == null
                ? "No marks data available."
                : "Marks report placeholder for " + c + ". Detailed mark aggregation is owned by academics.");
        return report;
    }

    public Report researchReport() {
        Report report = new Report();
        report.setTitle("Research report");
        report.setContent("Research report generated from current university research profiles.");
        return report;
    }
}
