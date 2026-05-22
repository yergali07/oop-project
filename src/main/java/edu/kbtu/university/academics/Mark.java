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
        this.totalScore = att1 + att2 + finalScore;
    }

    public double calculateTotalScore() {
        calculateTotal();
        return totalScore;
    }

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
        String grade = getLetterGrade();
        return !grade.equals("F") && !grade.equals("FX");
    }
}
