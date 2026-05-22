package edu.kbtu.university.academics;

public class Mark {
    private String studentId;
    private String courseId;
    private double att1;
    private double att2;
    private double finalScore;
    private double totalScore;

    public Mark() {
    }

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
     *
     */
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public double getAtt1() {
        return att1;
    }

    public void setAtt1(double att1) {
        this.att1 = att1;
        calculateTotal();
    }

    public double getAtt2() {
        return att2;
    }

    public void setAtt2(double att2) {
        this.att2 = att2;
        calculateTotal();
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
        calculateTotal();
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public void calculateTotal() {
        this.totalScore = att1 * 0.3 + att2 * 0.3 + finalScore * 0.4;
    }

    public double calculateTotalScore() {
        calculateTotal();
        return totalScore;
    }

    public String getLetterGrade() {
        if (totalScore >= 95) {
            return "A";
        }
        if (totalScore >= 90) {
            return "A-";
        }
        if (totalScore >= 85) {
            return "B+";
        }
        if (totalScore >= 80) {
            return "B";
        }
        if (totalScore >= 75) {
            return "B-";
        }
        if (totalScore >= 70) {
            return "C+";
        }
        if (totalScore >= 65) {
            return "C";
        }
        if (totalScore >= 60) {
            return "C-";
        }
        if (totalScore >= 55) {
            return "D+";
        }
        if (totalScore >= 50) {
            return "D";
        }
        return "F";
    }

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

    public boolean isPassing() {
        return totalScore >= 50;
    }
}
