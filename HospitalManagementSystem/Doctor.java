package HospitalManagementSystem;

public class Doctor {
    private String id;
    private String name;
    private String specialization;

    public Doctor(String id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toCSV() {
        return id + "," + name + "," + specialization;
    }

    public static Doctor fromCSV(String line) {
        String[] parts = line.split(",");
        return new Doctor(parts[0], parts[1], parts[2]);
    }

    public void displayInfo() {
        System.out.printf("Doctor ID: %s | Name: %s | Specialization: %s%n", id, name, specialization);
    }
}