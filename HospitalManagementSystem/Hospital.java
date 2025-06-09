package HospitalManagementSystem;

import java.util.*;
import java.io.*;

public class Hospital {
    private List<Doctor> doctors;
    private List<Patient> patients;
    private List<Appointment> appointments;

    private final String appointmentFile = "appointments.csv";
    private int appointmentCounter = 1;

    public Hospital() {
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        appointments = new ArrayList<>();
    }

    // Doctor operations
    public void addDoctor(Doctor d) {
        doctors.add(d);
    }

    public void listDoctors() {
        for (Doctor d : doctors)
            d.displayInfo();
    }

    public void searchDoctorByName(String name) {
        for (Doctor d : doctors) {
            if (d.getName().equalsIgnoreCase(name))
                d.displayInfo();
        }
    }

    public boolean doctorExists(String id) {
        for (Doctor d : doctors)
            if (d.getId().equals(id)) return true;
        return false;
    }

    // Patient operations
    public void addPatient(Patient p) {
        patients.add(p);
    }

    public void listPatients() {
        for (Patient p : patients)
            p.displayInfo();
    }

    public void searchPatientByDisease(String disease) {
        for (Patient p : patients) {
            if (p.getDisease().equalsIgnoreCase(disease))
                p.displayInfo();
        }
    }

    public boolean patientExists(String id) {
        for (Patient p : patients)
            if (p.getId().equals(id)) return true;
        return false;
    }

    // Appointment operations
    public void addAppointment(String doctorId, String patientId, String date, String time) {
        if (!doctorExists(doctorId)) {
            System.out.println("Doctor ID not found.");
            return;
        }
        if (!patientExists(patientId)) {
            System.out.println("Patient ID not found.");
            return;
        }

        String appointmentId = "A" + appointmentCounter++;
        Appointment appointment = new Appointment(appointmentId, doctorId, patientId, date, time);
        appointments.add(appointment);
        System.out.println("Appointment booked with ID: " + appointmentId);
    }

    public void viewAppointments() {
        for (Appointment a : appointments)
            a.displayInfo();
    }

    // File operations
    public void saveData() {
        List<String> docLines = new ArrayList<>();
        for (Doctor d : doctors)
            docLines.add(d.toCSV());

        List<String> patLines = new ArrayList<>();
        for (Patient p : patients)
            patLines.add(p.toCSV());

        List<String> apptLines = new ArrayList<>();
        for (Appointment a : appointments)
            apptLines.add(a.toCSV());

        FileHandler.writeToFile("doctors.csv", docLines);
        FileHandler.writeToFile("patients.csv", patLines);
        FileHandler.writeToFile(appointmentFile, apptLines);
    }

    public void loadData() {
        doctors.clear();
        patients.clear();
        appointments.clear();

        for (String line : FileHandler.readFromFile("doctors.csv")) {
            doctors.add(Doctor.fromCSV(line));
        }

        for (String line : FileHandler.readFromFile("patients.csv")) {
            patients.add(Patient.fromCSV(line));
        }

        for (String line : FileHandler.readFromFile(appointmentFile)) {
            Appointment a = Appointment.fromCSV(line);
            appointments.add(a);
            int id = Integer.parseInt(a.getAppointmentId().substring(1));
            if (id >= appointmentCounter) appointmentCounter = id + 1;
        }
    }
}


