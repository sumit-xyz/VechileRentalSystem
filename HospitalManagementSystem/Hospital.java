package HospitalManagementSystem;

import java.util.*;

public class Hospital {
    private List<Doctor> doctors;
    private List<Patient> patients;

    public Hospital() {
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
    }

    public void addDoctor(Doctor d) {
        doctors.add(d);
    }

    public void addPatient(Patient p) {
        patients.add(p);
    }

    public void listDoctors() {
        for (Doctor d : doctors) d.displayInfo();
    }

    public void listPatients() {
        for (Patient p : patients) p.displayInfo();
    }

    public void saveData() {
        List<String> docLines = new ArrayList<>();
        for (Doctor d : doctors) docLines.add(d.toCSV());

        List<String> patLines = new ArrayList<>();
        for (Patient p : patients) patLines.add(p.toCSV());

        FileHandler.writeToFile("doctors.csv", docLines);
        FileHandler.writeToFile("patients.csv", patLines);
    }

    public void loadData() {
        doctors.clear();
        patients.clear();

        for (String line : FileHandler.readFromFile("doctors.csv")) {
            doctors.add(Doctor.fromCSV(line));
        }
        for (String line : FileHandler.readFromFile("patients.csv")) {
            patients.add(Patient.fromCSV(line));
        }
    }

    public void searchDoctorByName(String name) {
        for (Doctor d : doctors) {
            if (d.getName().equalsIgnoreCase(name)) d.displayInfo();
        }
    }

    public void searchPatientByDisease(String disease) {
        for (Patient p : patients) {
            if (p.getDisease().equalsIgnoreCase(disease)) p.displayInfo();
        }
    }
}
