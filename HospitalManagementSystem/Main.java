package HospitalManagementSystem;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        hospital.loadData();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("""
            ===== Hospital Management Menu =====
            1. Add Doctor
            2. Add Patient
            3. List Doctors
            4. List Patients
            5. Search Doctor by Name
            6. Search Patient by Disease
            7. Add Appointment
            8. View Appointments
            9. Save & Exit
            =====================================
            Enter choice:
            """);

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Doctor ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Specialty: ");
                    String spec = scanner.nextLine();
                    hospital.addDoctor(new Doctor(id, name, spec));
                }
                case 2 -> {
                    System.out.print("Enter Patient ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Disease: ");
                    String dis = scanner.nextLine();
                    hospital.addPatient(new Patient(id, name, dis));
                }
                case 3 -> hospital.listDoctors();
                case 4 -> hospital.listPatients();
                case 5 -> {
                    System.out.print("Enter doctor name: ");
                    hospital.searchDoctorByName(scanner.nextLine());
                }
                case 6 -> {
                    System.out.print("Enter disease: ");
                    hospital.searchPatientByDisease(scanner.nextLine());
                }
                case 7 -> {
                    System.out.print("Enter Doctor ID: ");
                    String did = scanner.nextLine();
                    System.out.print("Enter Patient ID: ");
                    String pid = scanner.nextLine();
                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter Time (HH:MM): ");
                    String time = scanner.nextLine();
                    hospital.addAppointment(did, pid, date, time);
                }
                case 8 -> hospital.viewAppointments();
                case 9 -> {
                    hospital.saveData();
                    System.out.println("Data saved. Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}