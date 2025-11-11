import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class AttendanceManager {
    private List<Student> students = new ArrayList<>();
    private List<Attendance> records = new ArrayList<>();
    private final String FILE_NAME = "attendance.txt";

    // Add new student
    public void addStudent(int id, String name) {
        students.add(new Student(id, name));
        System.out.println(ConsoleColor.GREEN + "✅ Student added successfully!" + ConsoleColor.RESET);
    }

    // View all students
    public void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println(ConsoleColor.RED + "⚠️ No students found!" + ConsoleColor.RESET);
            return;
        }
        System.out.println(ConsoleColor.CYAN + "\n--- Student List ---" + ConsoleColor.RESET);
        students.forEach(System.out::println);
    }

    // Mark attendance
    public void markAttendance(int id, boolean present) {
        Optional<Student> student = students.stream().filter(s -> s.getId() == id).findFirst();
        if (student.isPresent()) {
            Attendance a = new Attendance(student.get(), LocalDate.now(), present);
            records.add(a);
            System.out.println(ConsoleColor.GREEN + "📋 Attendance marked for " + student.get().getName() + ConsoleColor.RESET);
        } else {
            System.out.println(ConsoleColor.RED + "❌ Student not found!" + ConsoleColor.RESET);
        }
    }

    // View attendance records
    public void viewAttendance() {
        if (records.isEmpty()) {
            System.out.println(ConsoleColor.RED + "⚠️ No attendance records!" + ConsoleColor.RESET);
            return;
        }
        System.out.println(ConsoleColor.CYAN + "\n--- Attendance Records ---" + ConsoleColor.RESET);
        records.forEach(System.out::println);
    }

    // Show attendance summary
    public void showAttendanceSummary() {
        if (students.isEmpty()) {
            System.out.println(ConsoleColor.RED + "⚠️ No students found!" + ConsoleColor.RESET);
            return;
        }
        System.out.println(ConsoleColor.CYAN + "\n--- Attendance Summary ---" + ConsoleColor.RESET);
        for (Student s : students) {
            long total = records.stream().filter(r -> r.getStudent().getId() == s.getId()).count();
            long present = records.stream().filter(r -> r.getStudent().getId() == s.getId() && r.isPresent()).count();
            double percent = (total == 0) ? 0 : (present * 100.0 / total);
            String grade = percent >= 90 ? "Excellent" : percent >= 75 ? "Good" : "Needs Improvement";
            System.out.printf("%s: %.2f%% (%d/%d) - %s%n", s.getName(), percent, present, total, grade);
        }
    }

    // Save records to file
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Attendance a : records) {
                bw.write(a.toString());
                bw.newLine();
            }
            System.out.println(ConsoleColor.GREEN + "💾 Attendance saved to file successfully!" + ConsoleColor.RESET);
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "❌ Error saving file: " + e.getMessage() + ConsoleColor.RESET);
        }
    }
}
