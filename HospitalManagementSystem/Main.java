package HospitalManagementSystem;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hospital hospital = new Hospital();
        hospital.loadData();

        while (true) {
            System.out.println("\n--- Hospital Management System ---");
            System.out.println("1. Add Doctor");
            System.out.println("2. Add Patient");
            System.out.println("3. List Doctors");
            System.out.println("4. List Patients");
            System.out.println("5. Search Doctor by Name");
            System.out.println("6. Search Patient by Disease");
            System.out.println("7. Save & Exit");

            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter Doctor ID: ");
                    String did = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String dname = sc.nextLine();
                    System.out.print("Enter Age: ");
                    int dage = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Specialization: ");
                    String spec = sc.nextLine();
                    hospital.addDoctor(new Doctor(did, dname, dage, spec));
                    break;

                case 2:
                    System.out.print("Enter Patient ID: ");
                    String pid = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String pname = sc.nextLine();
                    System.out.print("Enter Age: ");
                    int page = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Disease: ");
                    String dis = sc.nextLine();
                    hospital.addPatient(new Patient(pid, pname, page, dis));
                    break;

                case 3:
                    hospital.listDoctors();
                    break;

                case 4:
                    hospital.listPatients();
                    break;

                case 5:
                    System.out.print("Enter Doctor Name: ");
                    String searchDoc = sc.nextLine();
                    hospital.searchDoctorByName(searchDoc);
                    break;

                case 6:
                    System.out.print("Enter Disease: ");
                    String searchDis = sc.nextLine();
                    hospital.searchPatientByDisease(searchDis);
                    break;

                case 7:
                    hospital.saveData();
                    System.out.println("Data saved. Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
