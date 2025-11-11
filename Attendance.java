import java.time.LocalDate;

public class Attendance {
    private Student student;
    private LocalDate date;
    private boolean present;

    public Attendance(Student student, LocalDate date, boolean present) {
        this.student = student;
        this.date = date;
        this.present = present;
    }

    public Student getStudent() {
        return student;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isPresent() {
        return present;
    }

    @Override
    public String toString() {
        return date + " | " + student.getName() + " | " + (present ? "Present" : "Absent");
    }
}
