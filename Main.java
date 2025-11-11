import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AttendanceManager manager = new AttendanceManager();

        System.out.println(ConsoleColor.CYAN + "===== SMART CLASSROOM ATTENDANCE SYSTEM =====" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.YELLOW + "Login Required" + ConsoleColor.RESET);

        // 🔐 Teacher Login
        if (!login(sc)) {
            System.out.println(ConsoleColor.RED + "Access Denied! Exiting..." + ConsoleColor.RESET);
            return;
        }

        int choice;
        do {
            System.out.println(ConsoleColor.CYAN + "\n===== MENU =====" + ConsoleColor.RESET);
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Mark Attendance");
            System.out.println("4. View Attendance Records");
            System.out.println("5. Attendance Summary");
            System.out.println("6. Save & Exit");
            System.out.print(ConsoleColor.YELLOW + "Enter your choice: " + ConsoleColor.RESET);
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();
                    manager.addStudent(id, name);
                    break;

                case 2:
                    manager.viewAllStudents();
                    break;

                case 3:
                    System.out.print("Enter Student ID: ");
                    int sid = sc.nextInt();
                    System.out.print("Is present? (true/false): ");
                    boolean present = sc.nextBoolean();
                    manager.markAttendance(sid, present);
                    break;

                case 4:
                    manager.viewAttendance();
                    break;

                case 5:
                    manager.showAttendanceSummary();
                    break;

                case 6:
                    manager.saveToFile();
                    System.out.println(ConsoleColor.GREEN + "👋 Exiting... Goodbye!" + ConsoleColor.RESET);
                    break;

                default:
                    System.out.println(ConsoleColor.RED + "❌ Invalid choice! Try again." + ConsoleColor.RESET);
            }

        } while (choice != 6);

        sc.close();
    }

    // 🔑 Simple Teacher Login
    public static boolean login(Scanner sc) {
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        if (user.equals("admin") && pass.equals("1234")) {
            System.out.println(ConsoleColor.GREEN + "✅ Login Successful!" + ConsoleColor.RESET);
            return true;
        } else {
            System.out.println(ConsoleColor.RED + "❌ Wrong username or password!" + ConsoleColor.RESET);
            return false;
        }
    }
}
