package HospitalManagementSystem;
public class Patient extends Person {
    private String disease;

    public Patient(String id, String name, int age, String disease) {
        super(id, name, age);
        this.disease = disease;
    }

    @Override
    public void displayInfo() {
        System.out.println("Patient ID: " + id + ", Name: " + name + ", Age: " + age + ", Disease: " + disease);
    }

    public String getDisease() {
        return disease;
    }

    public String toCSV() {
        return id + "," + name + "," + age + "," + disease;
    }

    public static Patient fromCSV(String line) {
        String[] parts = line.split(",");
        return new Patient(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]);
    }
}
