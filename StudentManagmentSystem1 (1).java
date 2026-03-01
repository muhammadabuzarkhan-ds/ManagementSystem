import java.io.*;
import java.util.*;

public class StudentManagmentSystem1{

    // Arrays to store student data
    static String[] names = new String[0];
    static int[] rolls = new int[0];
    static int[] ages = new int[0];
    static int[] marks = new int[0];
    static float[] attendance = new float[0];
    static int studentCount = 0;
    static int maxStudents = 0;
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // Display header
        System.out.println("==================================================");
        System.out.println("            STUDENT MANAGEMENT SYSTEM           ");
        System.out.println("          Java Console-Based Application        ");
        System.out.println("==================================================");
        System.out.println();
        
        System.out.println("               DEVELOPER INFORMATION              ");
        System.out.println("--------------------------------------------------");
        System.out.println("Developed by  : M Abuzar Khan");
        System.out.println("University    : COMSATS University Islamabad");
        System.out.println("Course        : BS Data Science");
        System.out.println();
        
        // Login credentials
        String adminUser = "admin@gmail.com";
        String adminPass = "123123";
        String studentPass = "321321";

        // Main menu
        while(true) {
            try {
                System.out.println("Press 1 for Admin Login ");
                System.out.println("Press 2 for Student Login ");
                System.out.println("Press -1 to Exit System");
                System.out.print("Enter your Choice (1, 2 or -1): ");
                int choice = input.nextInt();
                input.nextLine();

                // Exit condition
                if(choice == -1) {
                    System.out.println("Exiting system. Goodbye!");
                    break;
                }

                boolean loggedIn = false;
                boolean isAdmin = false;

                // Admin login
                if (choice == 1) {
                    System.out.print("Enter Admin Username: ");
                    String user = input.nextLine();
                    System.out.print("Enter Admin Password: ");
                    String pass = input.nextLine();

                    if (user.equals(adminUser) && pass.equals(adminPass)) {
                        System.out.println(" Admin Login Successful!\n");
                        loggedIn = true;
                        isAdmin = true;
                    } else {
                        System.out.println(" Incorrect Admin Username or Password.\n");
                        continue;
                    }

                    if (loggedIn && isAdmin) {
                        while (true) {
                            try {
                                System.out.print("Enter how many total student records you want to manage: ");
                                maxStudents = input.nextInt();
                                input.nextLine();
                                if (maxStudents <= 0) {
                                    System.out.println("Please enter a positive number.");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid number.");
                                input.nextLine();
                            }
                        }

                        // Initialize arrays with new size
                        names = new String[maxStudents];
                        rolls = new int[maxStudents];
                        ages = new int[maxStudents];
                        marks = new int[maxStudents];
                        attendance = new float[maxStudents];
                        studentCount = 0;

                        adminMenu(input);
                    }
                } 
                // Student login
                else if (choice == 2) {
                    studentLogin(input, studentPass);
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
            }
        }
        input.close();
    }

    // Admin menu method
    private static void adminMenu(Scanner input) {
        int adminChoice;
        do {
            try {
                System.out.println("\n   Admin Menu ");
                System.out.println("1. Add Student Record");
                System.out.println("2. View All Student Records");
                System.out.println("3. Student Search by Name and Roll Number");
                System.out.println("4. Update Student Record");
                System.out.println("5. Generate Report Card for Students");
                System.out.println("6. Total number of Students Added ");
                System.out.println("7. Export data to File");
                System.out.println("0. Logout");

                System.out.print("Enter your choice: ");
                adminChoice = input.nextInt();
                input.nextLine();

                switch (adminChoice) {
                    case 1:
                        addStudentRecord(input);
                        break;
                    case 2:
                        viewAllRecords();
                        break;
                    case 3:
                        searchStudent(input);
                        break;
                    case 4:
                        updateStudentRecord(input);
                        break;
                    case 5:
                        generateReportCards();
                        break;
                    case 6:
                        totalStudents();
                        break;
                    case 7:
                        exportToFile();
                        break;
                    case 0:
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid admin menu choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
                adminChoice = -1;
            }
        } 
        while (adminChoice != 0);
    }

    // Add student record method 
    private static void addStudentRecord(Scanner input) {
        int n = 0;
        while (true) {
            try {
                System.out.print("How many students do you want to add? ");
                n = input.nextInt();
                input.nextLine();
                if (n <= 0) {
                    System.out.println("Please enter a positive number.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                input.nextLine();
            }
        }

        if (studentCount + n > maxStudents) {
            System.out.println(" You cannot add. Total will exceed the limit (" + maxStudents + ").");
            return;
        }

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for Student " + (studentCount + 1));

            System.out.print(" Enter the Name of Student: ");
            names[studentCount] = input.next();

            while (true) {
                try {
                    System.out.print(" Enter the Roll Number of student( Should be unique) : ");
                    rolls[studentCount] = input.nextInt();
                    
                    // Check for duplicate roll numbers
                    boolean duplicate = false;
                    for (int j = 0; j < studentCount; j++) {
                        if (rolls[j] == rolls[studentCount]) {
                            duplicate = true;
                            break;
                        }
                    }
                    if (duplicate) {
                        System.out.println("Roll number already exists. Please enter a unique roll number.");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    input.nextLine();
                }
            }

            int age;
            do {
                try {
                    System.out.print(" Enter the Age of student (15 - 25): ");
                    age = input.nextInt();
                    if (age < 15 || age > 25) {
                        System.out.println(" Invalid age. Please enter a value between 15 and 25.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    input.nextLine();
                    age = 0; // force retry
                }
            } while (age < 15 || age > 25);
            ages[studentCount] = age;

            int mark;
            do {
                try {
                    System.out.print(" Enter the Marks of student (0 - 100): ");
                    mark = input.nextInt();
                    if (mark < 0 || mark > 100) {
                        System.out.println("Invalid marks. Must be between 0 and 100.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    input.nextLine();
                    mark = -1; // force retry
                }
            } while (mark < 0 || mark > 100);
            marks[studentCount] = mark;

            float attendances;
            do {
                try {
                    System.out.print(" Put the Attendance (0 - 100 %): ");
                    attendances = input.nextFloat();
                    if (attendances < 0 || attendances > 100) {
                        System.out.println("Invalid attendance. Must be between 0 and 100.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    input.nextLine();
                    attendances = -1; // force retry
                }
            } while (attendances < 0 || attendances > 100);
            attendance[studentCount] = attendances;

            studentCount++;
        }

        System.out.println(" Student(s) added successfully!");
    }

    // View all records method (exact same logic as original)
    private static void viewAllRecords() {
        if (studentCount == 0) {
            System.out.println(" No student records available.");
        } else {
            System.out.println("\n All Student Records ");
            for (int i = 0; i < studentCount; i++) {
                System.out.println("\nStudent " + (i + 1));
                System.out.println("Name       : " + names[i]);
                System.out.println("Roll No    : " + rolls[i]);
                System.out.println("Age        : " + ages[i]);
                System.out.println("Marks      : " + marks[i]);
                System.out.println("Attendance : " + attendance[i] + "%");
            }
        }
    }

    // Search student method (exact same logic as original)
    private static void searchStudent(Scanner input) {
        System.out.println("Search by: ");
        System.out.println("1. Name");
        System.out.println("2. Roll Number");
        System.out.print("Enter your choice: ");
        int searchChoice = 0;
        try {
            searchChoice = input.nextInt();
            input.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter 1 or 2.");
            input.nextLine();
            return;
        }

        boolean found = false;

        if (searchChoice == 1) {
            System.out.print("Enter name to search: ");
            String searchName = input.nextLine();

            for (int i = 0; i < studentCount; i++) {
                if (names[i].equalsIgnoreCase(searchName)) {
                    System.out.println("\nStudent Found:");
                    System.out.println("Name       : " + names[i]);
                    System.out.println("Roll No    : " + rolls[i]);
                    System.out.println("Age        : " + ages[i]);
                    System.out.println("Marks      : " + marks[i]);
                    System.out.println("Attendance : " + attendance[i] + "%");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Student with name '" + searchName + "' not found.");
            }
        } else if (searchChoice == 2) {
            int searchRoll = 0;
            try {
                System.out.print("Enter roll number to search: ");
                searchRoll = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid roll number.");
                input.nextLine();
                return;
            }

            for (int i = 0; i < studentCount; i++) {
                if (rolls[i] == searchRoll) {
                    System.out.println("\nStudent Found:");
                    System.out.println("Name       : " + names[i]);
                    System.out.println("Roll No    : " + rolls[i]);
                    System.out.println("Age        : " + ages[i]);
                    System.out.println("Marks      : " + marks[i]);
                    System.out.println("Attendance : " + attendance[i] + "%");
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Student with roll number " + searchRoll + " not found.");
            }
        } else {
            System.out.println("Invalid search choice.");
        }
    }

    // Update student record method (exact same logic as original)
    private static void updateStudentRecord(Scanner input) {
        int updateRoll = 0;
        try {
            System.out.print("Enter Roll Number of student to update: ");
            updateRoll = input.nextInt();
            input.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid roll number.");
            input.nextLine();
            return;
        }

        boolean updated = false;

        for (int i = 0; i < studentCount; i++) {
            if (rolls[i] == updateRoll) {
                System.out.println("\nCurrent Record:");
                System.out.println("Name       : " + names[i]);
                System.out.println("Age        : " + ages[i]);
                System.out.println("Marks      : " + marks[i]);
                System.out.println("Attendance : " + attendance[i] + "%");

                System.out.println("\nWhich field do you want to update?");
                System.out.println("1. Name");
                System.out.println("2. Age");
                System.out.println("3. Marks");
                System.out.println("4. Attendance");
                System.out.print("Enter choice (1-4): ");
                int fieldChoice = 0;
                try {
                    fieldChoice = input.nextInt();
                    input.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 1-4.");
                    input.nextLine();
                    return;
                }

                switch (fieldChoice) {
                    case 1:
                        System.out.print("Enter new name: ");
                        names[i] = input.nextLine();
                        break;
                    case 2:
                        int newAge;
                        do {
                            try {
                                System.out.print("Enter new age (15–25): ");
                                newAge = input.nextInt();
                                input.nextLine();
                                if (newAge < 15 || newAge > 25) {
                                    System.out.println(" Invalid age. Try again.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid age.");
                                input.nextLine();
                                newAge = 0; // force retry
                            }
                        } while (newAge < 15 || newAge > 25);
                        ages[i] = newAge;
                        break;
                    case 3:
                        int newMarks;
                        do {
                            try {
                                System.out.print("Enter new marks (0 - 100): ");
                                newMarks = input.nextInt();
                                input.nextLine();
                                if (newMarks < 0 || newMarks > 100) {
                                    System.out.println(" Invalid marks. Try again.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid mark.");
                                input.nextLine();
                                newMarks = -1; // force retry
                            }
                        } while (newMarks < 0 || newMarks > 100);
                        marks[i] = newMarks;
                        break;
                    case 4:
                        float newAttendance;
                        do {
                            try {
                                System.out.print("Enter new attendance (0 – 100%): ");
                                newAttendance = input.nextFloat();
                                input.nextLine();
                                if (newAttendance < 0 || newAttendance > 100) {
                                    System.out.println(" Invalid attendance. Try again.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid attendance percentage.");
                                input.nextLine();
                                newAttendance = -1; // force retry
                            }
                        } while (newAttendance < 0 || newAttendance > 100);
                        attendance[i] = newAttendance;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }

                System.out.println("Record updated successfully!");
                updated = true;
                break;
            }
        }

        if (!updated) {
            System.out.println("Student with roll number " + updateRoll + " not found.");
        }
    }

    // Generate report cards method (exact same logic as original)
    private static void generateReportCards() {
        if (studentCount == 0) {
            System.out.println("No records found to generate report cards.");
        } else {
            System.out.println("\n======= Report Cards =======");
            for (int i = 0; i < studentCount; i++) {
                System.out.println("\nStudent " + (i + 1));
                System.out.println("Name       : " + names[i]);
                System.out.println("Roll No    : " + rolls[i]);
                System.out.println("Marks      : " + marks[i]);

                String grade;
                if (marks[i] >= 85)
                    grade = "A";
                else if (marks[i] >= 70)
                    grade = "B";
                else if (marks[i] >= 60)
                    grade = "C";
                else if (marks[i] >= 50) 
                    grade = "D";
                else 
                    grade = "F";

                System.out.println("Grade      : " + grade);
                System.out.println("Attendance : " + attendance[i] + "%");

                String status = (marks[i] >= 50 && attendance[i] >= 75) ? "Pass" : "Fail";
                System.out.println("Status     : " + status);

                if (attendance[i] < 75) {
                    System.out.println(" Warning: Low Attendance (Below 75%)");
                }
            }
        }
    }

    // Total students method (exact same logic as original)
    private static void totalStudents() {
        if(studentCount == 0) {
            System.out.println("The Total Number of students are Zero");
        } else {
            System.out.println("The total students are "+studentCount);
        }
    }

    // Export to file method (exact same logic as original)
    private static void exportToFile() {
        try {
            String path = "Studentdata.txt";
            File file = new File(path);
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(studentCount + "\n"); // First line = total students

            for (int i = 0; i < studentCount; i++) {
                bw.write("Name :" +names[i] + ", " +"Roll Number :"+ rolls[i] + ", " +"Age :"+ ages[i] + ", " +"Marks :"+ marks[i] + ", " +"Attendance :"+ attendance[i] + "\n");
            }

            bw.close();
            System.out.println("Student data exported successfully to 'studentdata.txt'!");
        } catch (IOException e) {
            System.out.println("Error exporting data: " + e.getMessage());
        }
    }

    // Student login method (exact same logic as original)
    private static void studentLogin(Scanner input, String studentPass) {
        if (studentCount == 0) {
            System.out.println("No students registered yet. Please ask admin to add students first.");
            return;
        }

        int rollNo = 0;
        try {
            System.out.print("Enter Your Roll number : ");
            rollNo = input.nextInt();
            input.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid roll number.");
            input.nextLine();
            return;
        }

        System.out.print("Enter Password: ");
        String studPass = input.nextLine();

        boolean studentFound = false;

        for (int i = 0; i < studentCount; i++) {
            if (rolls[i] == rollNo && studPass.equals(studentPass)) { 
                studentFound = true;
                int studentChoice;
                do {
                    try {
                        System.out.println("\n   Student Menu ");
                        System.out.println("1. View My Details");
                        System.out.println("2. Check My Grade");
                        System.out.println("3. View Attendance");
                        System.out.println("0. Logout");

                        System.out.print("Enter your choice: ");
                        studentChoice = input.nextInt();
                        input.nextLine();

                        if (studentChoice == 1) {
                            System.out.println("\n===== My Details =====");
                            System.out.println("Name       : " + names[i]);
                            System.out.println("Roll No    : " + rolls[i]);
                            System.out.println("Marks      : " + marks[i]);
                            System.out.println("Attendance : " + attendance[i] + "%");

                        } else if (studentChoice == 2) {
                            String grade;
                            if (marks[i] >= 85)
                                grade = "A";
                            else if (marks[i] >= 70) 
                                grade = "B";
                            else if (marks[i] >= 60) 
                                grade = "C";
                            else if (marks[i] >= 50) 
                                grade = "D";
                            else 
                                grade = "F";

                            String status = (marks[i] >= 50 && attendance[i] >= 75) ? "Pass" : "Fail";

                            System.out.println("\n===== My Grade =====");
                            System.out.println("Marks  : " + marks[i]);
                            System.out.println("Grade  : " + grade);
                            System.out.println("Status : " + status);

                        } else if (studentChoice == 3) {
                            System.out.println("\nYour Attendance is: " + attendance[i] + "%");
                            if (attendance[i] < 75) {
                                System.out.println("Warning: Your attendance is low. Maintain 75%+ to avoid failure.");
                            }

                        } else if (studentChoice == 0) {
                            System.out.println("Logging out...");
                        } else {
                            System.out.println("Invalid choice!");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                        studentChoice = -1; // Continue loop
                    }
                } while (studentChoice != 0);
                break;
            }
        }

        if (!studentFound) {
            System.out.println("Login failed! Invalid Roll Number or Password.");
        }
    }
}
