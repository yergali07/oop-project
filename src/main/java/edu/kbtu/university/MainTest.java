package edu.kbtu.university;

import edu.kbtu.university.academics.*;
import java.time.LocalTime;
import java.time.DayOfWeek;

public class MainTest {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТИРОВАНИЕ ШАГА 4 (Конфликты уроков) ===");

        Lesson lesson1 = new Lesson();
        lesson1.setDay(DayOfWeek.MONDAY);
        lesson1.setRoom("301 L");
        lesson1.setStartTime(LocalTime.of(9, 0)); // 09:00
        lesson1.setDurationMinutes(50);           // до 09:50

        Lesson lesson2 = new Lesson();
        lesson2.setDay(DayOfWeek.MONDAY);
        lesson2.setRoom("301 L");
        lesson2.setStartTime(LocalTime.of(9, 30)); // 09:30 (накладка!)
        lesson2.setDurationMinutes(50);

        boolean hasConflict = lesson1.conflictsWith(lesson2);
        System.out.println("Есть ли конфликт (ожидается true): " + hasConflict);


        System.out.println("\n=== ТЕСТИРОВАНИЕ ШАГА 5 (Расчет GPA) ===");

        Course javaCourse = new Course();
        javaCourse.setCredits(3);

        Mark javaMark = new Mark();
        javaMark.setAtt1(30);
        javaMark.setAtt2(30);
        javaMark.setFinalScore(40); // Итого 100 баллов -> Это "A" (4.0 GPA)
        javaMark.calculateTotal();

        Transcript transcript = new Transcript();
        transcript.addMark(javaCourse, javaMark);

        double finalGpa = transcript.calculateGPA();
        System.out.println("Буквенная оценка (ожидается A): " + javaMark.getLetterGrade());
        System.out.println("Посчитанный GPA (ожидается 4.0): " + finalGpa);
    }
}
