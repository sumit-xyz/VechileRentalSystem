package HospitalManagementSystem;

public class Doctor extends Person {
    private String specialization;

    public Doctor(String id, String name, int age, String specialization) {
        super(id, name, age);
        this.specialization = specialization;
    }

    @Override
    public void displayInfo() {
        System.out.println("Doctor ID: " + id + ", Name: " + name + ", Age: " + age + ", Specialization: " + specialization);
    }

    public String getSpecialization() {
        return specialization;
    }

    public String toCSV() {
        return id + "," + name + "," + age + "," + specialization;
    }

    public static Doctor fromCSV(String line) {
        String[] parts = line.split(",");
        return new Doctor(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]);
    }
}