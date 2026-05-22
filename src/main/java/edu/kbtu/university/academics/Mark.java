package edu.kbtu.university.academics;

/**
 * Represents a student's mark for a course.
 * Stores first attestation, second attestation, final exam score and total score.
 */
public class Mark {
    private String studentId;
    private String courseId;
    private double att1;
    private double att2;
    private double finalScore;
    private double totalScore;

    /**
     * Creates an empty mark.
     */
    public Mark() {
    }

    /**
     * Creates a mark with all score fields.
     *
     * @param studentId student identifier
     * @param courseId course identifier
     * @param att1 first attestation score
     * @param att2 second attestation score
     * @param finalScore final exam score
     * @param totalScore total course score
     */
    public Mark(String studentId, String courseId, double att1, double att2,
                double finalScore, double totalScore) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.att1 = att1;
        this.att2 = att2;
        this.finalScore = finalScore;
        this.totalScore = totalScore;
    }


    /**
     * Returns the student identifier.
     *
     * @return student identifier
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Sets the student identifier.
     *
     * @param studentId student identifier
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Returns the course identifier.
     *
     * @return course identifier
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Sets the course identifier.
     *
     * @param courseId course identifier
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Returns the first attestation score.
     *
     * @return first attestation score
     */
    public double getAtt1() {
        return att1;
    }

    /**
     * Sets the first attestation score and recalculates the total score.
     *
     * @param att1 first attestation score
     */
    public void setAtt1(double att1) {
        this.att1 = att1;
        calculateTotal();
    }

    /**
     * Returns the second attestation score.
     *
     * @return second attestation score
     */
    public double getAtt2() {
        return att2;
    }

    /**
     * Sets the second attestation score and recalculates the total score.
     *
     * @param att2 second attestation score
     */
    public void setAtt2(double att2) {
        this.att2 = att2;
        calculateTotal();
    }

    /**
     * Returns the final exam score.
     *
     * @return final exam score
     */
    public double getFinalScore() {
        return finalScore;
    }

    /**
     * Sets the final exam score and recalculates the total score.
     *
     * @param finalScore final exam score
     */
    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
        calculateTotal();
    }

    /**
     * Returns the total course score.
     *
     * @return total course score
     */
    public double getTotalScore() {
        return totalScore;
    }

    /**
     * Sets the total course score directly.
     *
     * @param totalScore total course score
     */
    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * Calculates the total score from attestation and final exam scores.
     */
    public void calculateTotal() {
        this.totalScore = att1 + att2 + finalScore;
    }

    /**
     * Recalculates and returns the total score.
     *
     * @return recalculated total score
     */
    public double calculateTotalScore() {
        calculateTotal();
        return totalScore;
    }

    /**
     * Returns the letter grade according to KBTU grading rules.
     * The method handles F and FX cases before applying the standard grade scale.
     *
     * @return letter grade: A, A-, B+, B, B-, C+, C, C-, D+, D, FX or F
     */
    public String getLetterGrade() {
        calculateTotal();

        if (att1 + att2 <= 29.5) {
            return "F";
        }
        if (finalScore <= 9.5) {
            return "F";
        }
        if (finalScore <= 19.5) {
            return "FX";
        }
        if (totalScore < 50.0) {
            return "F";
        }
        if (totalScore >= 95.0) {
            return "A";
        }
        if (totalScore >= 90.0) {
            return "A-";
        }
        if (totalScore >= 85.0) {
            return "B+";
        }
        if (totalScore >= 80.0) {
            return "B";
        }
        if (totalScore >= 75.0) {
            return "B-";
        }
        if (totalScore >= 70.0) {
            return "C+";
        }
        if (totalScore >= 65.0) {
            return "C";
        }
        if (totalScore >= 60.0) {
            return "C-";
        }
        if (totalScore >= 55.0) {
            return "D+";
        }
        return "D";
    }

    /**
     * Converts the current letter grade to GPA points.
     *
     * @return GPA points for the current letter grade
     */
    public double getGpaPoints() {
        switch (getLetterGrade()) {
            case "A":
                return 4.0;
            case "A-":
                return 3.67;
            case "B+":
                return 3.33;
            case "B":
                return 3.0;
            case "B-":
                return 2.67;
            case "C+":
                return 2.33;
            case "C":
                return 2.0;
            case "C-":
                return 1.67;
            case "D+":
                return 1.33;
            case "D":
                return 1.0;
            default:
                return 0.0;
        }
    }

    /**
     * Checks whether the mark is passing.
     *
     * @return {@code true} if the grade is neither F nor FX; otherwise {@code false}
     */
    public boolean isPassing() {
        String grade = getLetterGrade();
        return !grade.equals("F") && !grade.equals("FX");
    }
}
