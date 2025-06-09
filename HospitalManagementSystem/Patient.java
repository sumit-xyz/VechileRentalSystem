package HospitalManagementSystem;

public class Patient {
    private String id;
    private String name;
    private String disease;

    public Patient(String id, String name, String disease) {
        this.id = id;
        this.name = name;
        this.disease = disease;
    }

    public String getId() {
        return id;
    }

    public String getDisease() {
        return disease;
    }

    public String toCSV() {
        return id + "," + name + "," + disease;
    }

    public static Patient fromCSV(String line) {
        String[] parts = line.split(",");
        return new Patient(parts[0], parts[1], parts[2]);
    }

    public void displayInfo() {
        System.out.printf("Patient ID: %s | Name: %s | Disease: %s%n", id, name, disease);
    }
}