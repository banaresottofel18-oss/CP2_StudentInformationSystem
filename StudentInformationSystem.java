import java.util.*;

public class StudentInformationSystem {
    public static void main(String[] args) {
        StudentManager manager = new Student Manager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Student Information System!");

        //Main loop
        while (running) {
            displayMenu();
            int choice = getValidInt(scanner,"Enter your choice: ", 1, 6);

         //Choose the number from 1-6
            switch (choice) {
                    // execute each option
                case 1: manager.addStudent(scanner);
                        break;
                case 2: manager.viewAllStudents();
                        break;
                case 3: managersearchStudent(scanner);
                        break;
                case 4: manager.updateStudent(scanner);
                        break;
                case 5: manager.deleteStudent(scanner);
                        break;
                case 6:
                    running = false;
                    System.out.println("thank you for using the Student Information System!");
                        break;  
                default:
                    System.out.printlm(" Invalid choice! Please try again.");
                    break;
            }
        }
    }
    scanner.close();
    }

    ///Display the system menu
    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("     STUDENT INFORMATION SYSTEM");
        System.out.println("=".repeat(40));
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Update Student Information");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
        System.out.println("=".repeat(40));
    }
    private static int getValidInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty.");
                continue;
            }
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Value must be between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format.");
            }
        }
    }
}

//student class
class Student {
    private final int id;
    private String name;
    private int age;
    private String course;
    private double gpa;

    public Student(int id, String name, int age, String course, double gpa) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
        this.gpa = gpa;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCourse() { return course; }
    public double getGpa() { return gpa; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setCourse(String course) { this.course = course; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    @Override
    public String toString() {
        return String.format("ID: %-6d | Name: %-25s | Age: %-3d | Course: %-15s | GPA: %.2f",
                id, name, age, course, gpa);
    }
}

//student manager
class StudentManager{
    private static final int MAX_STUDENTS = 100;
    private final List<Student> students = new ArrayList<>();

    public void addStudent(Scanner scanner) {
        if (students.size() >= MAX_STUDENTS) {
            System.out.println("Database is full! Maximum " + MAX_STUDENTS + " students allowed.");
            return;
        }

        System.out.println("\n--- Add New Student ---");

        int id = getValidId(scanner);
        if (id == -1) return;

        String name = getNonEmptyString(scanner, "Enter Full Name: ");
        int age = InputValidator.getValidInt(scanner, "Enter Age (1-150): ", 1, 150);
        String course = getNonEmptyString(scanner, "Enter Course/Program: ");
        double gpa = InputValidator.getValidDouble(scanner, "Enter GPA (0.0 - 4.0): ", 0.0, 4.0);

        students.add(new Student(id, name, age, course, gpa));
        System.out.println("Student added successfully! (Total: " + students.size() + ")");
    }

    private int getValidId(Scanner scanner) {
        while (true) {
            int id = InputValidator.getValidInt(scanner, "Enter Student ID: ", 1, Integer.MAX_VALUE);
            if (findStudentById(id) == null) {
                return id;
            }
            System.out.println("ID already exists! Please enter a unique ID.");
        }
    }

    public void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found in the system.");
            return;
        }

        System.out.println("\n--- All Students (" + students.size() + ") ---");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i));
        }
        System.out.println("=".repeat(50));
    }

    public void searchStudent(Scanner scanner) {
        int id = InputValidator.getValidInt(scanner, "Enter Student ID to search: ", 1, Integer.MAX_VALUE);
        Student student = findStudentById(id);

        if (student == null) {
            System.out.println("No student found with ID " + id + ".");
        } else {
            System.out.println("\nStudent Found:\n" + student);
        }
    }

    public void updateStudent(Scanner scanner) {
        int id = InputValidator.getValidInt(scanner, "Enter Student ID to update: ", 1, Integer.MAX_VALUE);
        Student student = findStudentById(id);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("\nCurrent Information: " + student);
        System.out.println("Leave blank to keep current value.\n");

        // Name
        String newName = getNonEmptyString(scanner, "New Name: ");
        if (!newName.isEmpty()) student.setName(newName);

        // Age
        String ageStr = scanner.nextLine().trim();
        if (!ageStr.isEmpty()) {
            try {
                int newAge = Integer.parseInt(ageStr);
                if (newAge >= 1 && newAge <= 150) student.setAge(newAge);
                else System.out.println("Invalid age. Keeping original.");
            } catch (Exception e) {
                System.out.println("Invalid input. Keeping original.");
            }
        }

        // Course
        String newCourse = getNonEmptyString(scanner, "New Course: ");
        if (!newCourse.isEmpty()) student.setCourse(newCourse);

        // GPA
        String gpaStr = scanner.nextLine().trim();
        if (!gpaStr.isEmpty()) {
            try {
                double newGpa = Double.parseDouble(gpaStr);
                if (newGpa >= 0.0 && newGpa <= 4.0) student.setGpa(newGpa);
                else System.out.println("Invalid GPA. Keeping original.");
            } catch (Exception e) {
                System.out.println("Invalid input. Keeping original.");
            }
        }

        System.out.println("Student information updated successfully!");
    }

    public void deleteStudent(Scanner scanner) {
        int id = InputValidator.getValidInt(scanner, "Enter Student ID to delete: ", 1, Integer.MAX_VALUE);
        Student student = findStudentById(id);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        students.remove(student);
        System.out.println("Student with ID " + id + " deleted successfully!");
    }

    private Student findStudentById(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    private String getNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Input cannot be empty.");
        }
    }
}

//Helper class
class InputValidator {
    public static int getValidInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty.");
                continue;
            }
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) return value;
                System.out.println("Value must be between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format.");
            }
        }
    }

    public static double getValidDouble(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty.");
                continue;
            }
            try {
                double value = Double.parseDouble(input);
                if (value >= min && value <= max) return value;
                System.out.println("Value must be between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid decimal number.");
            }
        }
    }
}
