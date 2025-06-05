import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HashMapExample {

    public static void main(String[] args) {
        ArrayList<HashMap<String, String>> students = new ArrayList<>();
        HashMap<String, ArrayList<Integer>> marks = new HashMap<>();

        Scanner scanner = new Scanner(System.in);
        int idCounter = 1;
        int id = 0;

        //file handling check if exist else create


        // file read students and add to students arraylist


        // roll, class,
        //marks


        //Initialize writer 



        while (true) {
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
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            scanner.nextLine(); // consume leftover newline

            switch (choice) {
                case 1:
                    scanner.nextLine();
                    HashMap<String, String> student = new HashMap<>();
                    System.out.print("Enter full name: ");
                    String name = scanner.nextLine();
                    student.put("Id", Integer.toString(idCounter));
                    student.put("Name", name);
                    System.out.print("Enter roll number: ");
                    String roll = scanner.nextLine();
                    student.put("Roll", roll);
                    System.out.print("Enter class: ");
                    String sclass = scanner.nextLine();
                    student.put("Class", sclass);

                    students.add(student);
                    marks.put(Integer.toString(idCounter), new ArrayList<>());
                    System.out.println("Student added with ID: " + idCounter);
                    idCounter++;


                    // write new student
                    break;

                case 2:
                    System.out.println("All Students:");
                    for (HashMap<String, String> s : students) {
                        System.out.println(s);
                    }
                    break;

                case 3:
                    System.out.print("Enter student ID to view: ");
                    String viewId = scanner.nextLine();
                    boolean found = false;
                    for (HashMap<String, String> s : students) {
                        if (s.get("Id").equals(viewId)) {
                            System.out.println(s);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter student ID to update: ");
                    String updateId = scanner.nextLine();
                    boolean updated = false;
                    for (HashMap<String, String> s : students) {
                        if (s.get("Id").equals(updateId)) {
                            System.out.print("Enter new name: ");
                            s.put("Name", scanner.nextLine());
                            System.out.print("Enter new class: ");
                            s.put("Class", scanner.nextLine());
                            updated = true;
                            System.out.println("Student updated.");
                            break;
                        }
                    }
                    if (!updated) {
                        System.out.println("Student not found.");
                    }
                    break;

                case 5:
                    System.out.print("Enter student ID to delete: ");
                    String deleteId = scanner.nextLine();
                    boolean removed = false;
                    for (int i = 0; i < students.size(); i++) {
                        if (students.get(i).get("Id").equals(deleteId)) {
                            students.remove(i);
                            marks.remove(deleteId);
                            removed = true;
                            System.out.println("Student deleted.");
                            break;
                        }
                    }
                    if (!removed) {
                        System.out.println("Student not found.");
                    }
                    break;

                case 6:
                    System.out.print("Enter student ID to add marks: ");
                    String markId = scanner.nextLine();
                    if (marks.containsKey(markId)) {
                        System.out.print("Enter mark (integer): ");
                        int mark = scanner.nextInt();
                        marks.get(markId).add(mark);
                        System.out.println("Mark added.");
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 7:
                    System.out.print("Enter student ID to view total marks: ");
                    String totalId = scanner.nextLine();
                    if (marks.containsKey(totalId)) {
                        ArrayList<Integer> studentMarks = marks.get(totalId);
                        int sum = 0;
                        for (int m : studentMarks) {
                            sum += m;
                        }
                        System.out.println("Total marks: " + sum);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 8:
                    System.out.print("Enter student ID to update roll number: ");
                    String rollId = scanner.nextLine();
                    boolean rollUpdated = false;
                    for (HashMap<String, String> s : students) {
                        if (s.get("Id").equals(rollId)) {
                            System.out.print("Enter new roll number: ");
                            s.put("Roll", scanner.nextLine());
                            rollUpdated = true;
                            System.out.println("Roll number updated.");
                            break;
                        }
                    }
                    if (!rollUpdated) {
                        System.out.println("Student not found.");
                    }
                    break;

                case 0:
                    System.out.println("Exiting program.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}