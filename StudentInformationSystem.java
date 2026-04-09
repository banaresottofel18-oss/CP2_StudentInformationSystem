import java.util.Scanner;

public class StudentInformationSystem {

    private static final int MAX_STUDENTS = 100;

    private static Student[] students = new Student[MAX_STUDENTS];
    private static int studentCount = 0;

  
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("Student Information System!");

        
        while (running) {
            displayMenu();
            int choice = getValidInt("Enter your choice: ", 1, 6);

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    running = false;
                    System.out.println("Thank you!");
                    break;
                default:
               
                    break;
            }
        }

        scanner.close();
    }

    
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

   
    private static int getValidInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String inputStr = scanner.nextLine().trim();

            if (inputStr.isEmpty()) {
                System.out.println(" Input cannot be empty. Try again.");
                continue;
            }

            try {
                int value = Integer.parseInt(inputStr);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println(" Value must be between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Invalid number format. Please enter a valid integer.");
            }
        }
    }

 
    private static double getValidDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String inputStr = scanner.nextLine().trim();

            if (inputStr.isEmpty()) {
                System.out.println(" Input cannot be empty. Try again.");
                continue;
            }

            try {
                double value = Double.parseDouble(inputStr);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println(" Value must be between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Invalid number format. Please enter a valid decimal number.");
            }
        }
    }

   
    private static String getNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println(" Input cannot be empty. Try again.");
        }
    }


    private static boolean isIDExists(int id) {
        return findStudentIndex(id) != -1;
    }

    
    private static int findStudentIndex(int id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].id == id) {
                return i;
            }
        }
        return -1;
    }

   
    private static void addStudent() {
        if (studentCount >= MAX_STUDENTS) {
            System.out.println(" Database is full! Maximum " + MAX_STUDENTS + " students allowed.");
            return;
        }

        System.out.println("\n--- Add New Student ---");

        int id = getValidInt("Enter Student ID (positive integer): ", 1, Integer.MAX_VALUE);
        while (isIDExists(id)) {
            System.out.println(" ID already exists! Please enter a unique ID.");
            id = getValidInt("Enter Student ID: ", 1, Integer.MAX_VALUE);
        }

        String name = getNonEmptyString("Enter Full Name: ");
        int age = getValidInt("Enter Age (1-150): ", 1, 150);
        String course = getNonEmptyString("Enter Course/Program: ");
        double gpa = getValidDouble("Enter GPA (0.0 - 4.0): ", 0.0, 4.0);

       
        students[studentCount] = new Student(id, name, age, course, gpa);
        studentCount++;

        System.out.println(" Student added successfully! (Total students: " + studentCount + ")");
    }

    
    private static void viewAllStudents() {
        if (studentCount == 0) {
            System.out.println("ℹ️ No students found in the system.");
            return;
        }

        System.out.println("\n--- All Students (" + studentCount + ") ---");
        for (int i = 0; i < studentCount; i++) {
            System.out.println((i + 1) + ". " + students[i].toString());
        }
        System.out.println("=".repeat(40));
    }

    
    private static void searchStudent() {
        int id = getValidInt("Enter Student ID to search: ", 1, Integer.MAX_VALUE);
        int index = findStudentIndex(id);

        if (index == -1) {
            System.out.println(" No student found with ID " + id + ".");
        } else {
            System.out.println("\n Student Found:");
            System.out.println(students[index].toString());
        }
    }

   
    private static void updateStudent() {
        int id = getValidInt("Enter Student ID to update: ", 1, Integer.MAX_VALUE);
        int index = findStudentIndex(id);

        if (index == -1) {
            System.out.println(" Student not found!");
            return;
        }

        Student s = students[index];
        System.out.println("\nCurrent Information: " + s.toString());
        System.out.println("Update fields (press Enter to keep current value):");

       
        System.out.print("New Name: ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) {
            s.name = newName;
        }

        
        System.out.print("New Age: ");
        String ageInput = scanner.nextLine().trim();
        if (!ageInput.isEmpty()) {
            try {
                int newAge = Integer.parseInt(ageInput);
                if (newAge >= 1 && newAge <= 150) {
                    s.age = newAge;
                } else {
                    System.out.println(" Invalid age. Keeping original.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Invalid age format. Keeping original.");
            }
        }

       
        System.out.print("New Course: ");
        String newCourse = scanner.nextLine().trim();
        if (!newCourse.isEmpty()) {
            s.course = newCourse;
        }

        System.out.print("New GPA: ");
        String gpaInput = scanner.nextLine().trim();
        if (!gpaInput.isEmpty()) {
            try {
                double newGpa = Double.parseDouble(gpaInput);
                if (newGpa >= 0.0 && newGpa <= 4.0) {
                    s.gpa = newGpa;
                } else {
                    System.out.println(" Invalid GPA. Keeping original.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Invalid GPA format. Keeping original.");
            }
        }

        System.out.println(" Student information updated successfully!");
    }

   
    private static void deleteStudent() {
        int id = getValidInt("Enter Student ID to delete: ", 1, Integer.MAX_VALUE);
        int index = findStudentIndex(id);

        if (index == -1) {
            System.out.println(" Student not found!");
            return;
        }

        for (int i = index; i < studentCount - 1; i++) {
            students[i] = students[i + 1];
        }
        students[studentCount - 1] = null;
        studentCount--;

        System.out.println(" Student with ID " + id + " deleted successfully!");
    }
}

class Student {
    public int id;
    public String name;
    public int age;
    public String course;
    public double gpa;

    public Student(int id, String name, int age, String course, double gpa) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return String.format("ID: %-6d | Name: %-25s | Age: %-3d | Course: %-15s | GPA: %.2f",
                id, name, age, course, gpa);
    }
}