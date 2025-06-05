import java.lang.Runtime.Version;
import java.util.*;

public class Student {
    String id, name, roll, email, phone, address, studentClass;

    public Student(String id, String name, String roll, String email, String phone, String address,
            String studentClass) {
        this.id = id;
        this.name = name;
        this.roll = roll;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.studentClass = studentClass;
    }

    public String toString() {
        return "ID:" + id + ", Name: " + name + ", Roll: " + roll + ", Class: " + studentClass + ", Phone: " + phone
                + ", Email:" + email + ",Address: " + address;
    }

}

class Mark {
    String subject;
    int marks;
    String studentId;

    public Mark(String subject, int marks, String studentId) {
        this.subject = subject;
        this.marks = marks;
        this.studentId = studentId;
    }
}

    class StudentManagement {
    static Map<String, Student> students = new HashMap<>();
    static List<Mark> marks = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
    int choice;

    do
    {
        System.out.println("\n===== Student Management Menu =====");
        System.out.println("1. Add Student");
         System.out.println("2. View All Students");
         System.out.println("3. Get Student by ID");
         System.out.println("4. Update Student by ID");
         System.out.println("5. Delete Student by ID");
         System.out.println("6. Add Marks for Student");
         System.out.println("7. Total Marks For Student");
         System.out.println("8. Update Roll Number");
         System.out.println("0. Exit");
         System.out.println("Enter your choice: ");
         choice = Integer.parseInt(sc.nextLine());

         switch (choice){
             case 1:
                addStudent();
                break;
             case 2:
                viewAllStudents();
                break;
            case 3:
                getStudentById();
                break;
            case 4:
                updateStudentById();
                break;
            case 5:
                deleteStudentById();
                break; 
            case 6:
                addMarks();
                break; 
            case 7:
                totalMarks();
                break;
            case 8:
                updateRollNumber();
                break;
            case 0:
            System.out.println("Exiting program....");              
         
                default:
                System.out.println("Invalid choice!");              
         }
    }   while (choice !=0);
}
    static void addStudent() {
    System.out.print("Enter Student ID: ");
    String id= sc.nextLine();
    if (students.containsKey(id)){
        System.out.println("Student already exists.");
        return;
    }

    System.out.print("Enter Name: ");
    String name = sc.nextLine();
    System.out.print("Enter Roll No: ");
    String roll= sc.nextLine();
    System.out.print("Enter Email: ");
    String email = sc.nextLine();
    System.out.print("Enter Phone: ");
    String phone = sc.nextLine();
    System.out.print("Enter Address: ");
    String address = sc.nextLine();
    System.out.print("Enter Class: ");
    String studentClass = sc.nextLine();

    students.put(id, new Student(id, name, roll, email, phone, address, studentClass));
    System.out.println("Student added succhessfully!");
   }

   static void viewAllStudents() {
    if (students.isEmpty()) {
        System.out.println("No students available.");
    } else {
        for (Student s : students.values()){
            System.out.println(s);
        }
    }
    }

    static void getStudentById() {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine();
        Student student = students.get(id);
    if (student != null) {
        System.out.println(student);
    } else {
    
            System.out.println("Student not found.");
    }
   }
   

    static void updateStudentById() {
        System.out.print("Enter Student ID to update: ");
        String id = sc.nextLine();
        if (!students.containsKey(id)) {
            System.out.println("Student  not found.");
            return;
        }


        
    System.out.print("Enter New Name: ");
    String name = sc.nextLine();
    System.out.print("Enter New Roll No: ");
    String roll= sc.nextLine();
    System.out.print("Enter New Email: ");
    String email = sc.nextLine();
    System.out.print("Enter New Phone: ");
    String phone = sc.nextLine();
    System.out.print("Enter New Address: ");
    String address = sc.nextLine();
    System.out.print("Enter New Class: ");
    String studentClass = sc.nextLine();

    
    students.put(id, new Student(id, name, roll, email, phone, address, studentClass));
    System.out.println("Student added succhessfully!");

    }


    static void deleteStudentById() {
        System.out.print("Enter Student ID to delete: ");
        String id = sc.nextLine();
        if (students.remove(id) != null) {
            marks.removeIf(m -> m.studentId.equals(id));
            System.out.println("Student and related marks deleted.");
        } else {
            System.out.println("Student not found.");
        }
        }

        static void addMarks() {
        System.out.print("Enter Student ID: ");
        String studentId = sc.nextLine();
        if (!students.containsKey(studentId)) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Subject: ");
        String subject = sc.nextLine();
        System.out.print("Enter Marks: ");
        int score = Integer.parseInt(sc.nextLine());

        marks.add(new Mark(subject, score, studentId));
        System.out.println("Marks added.");
    }

    static void totalMarks() {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine();
        if (!students.containsKey(id)) {
            System.out.println("Student not found.");
            return;
        }

        int total = 0;
        boolean found = false;

        for (Mark m : marks) {
            if (m.studentId.equals(id)) {
                System.out.println(m.subject + ": " + m.marks);
                total += m.marks;
                found = true;
            }
        }

        if (found) {
            System.out.println("Total Marks: " + total);
        } else {
            System.out.println("No marks found for this student.");
        }
    }

    static void updateRollNumber() {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine();
        Student student = students.get(id);
        if (student != null) {
            System.out.print("Enter New Roll Number: ");
            student.roll = sc.nextLine();
            System.out.println("Roll number updated.");
        } else {
            System.out.println("Student not found.");
        }
    }
}
    

    



        
    


        
    








    

