package HospitalManagementSystem;

public class Appointment {
    private String appointmentId;
    private String doctorId;
    private String patientId;
    private String date;
    private String time;

    public Appointment(String appointmentId, String doctorId, String patientId, String date, String time) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.time = time;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String toCSV() {
        return appointmentId + "," + doctorId + "," + patientId + "," + date + "," + time;
    }

    public static Appointment fromCSV(String line) {
        String[] parts = line.split(",");
        return new Appointment(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    public void displayInfo() {
        System.out.printf("Appointment ID: %s | Doctor ID: %s | Patient ID: %s | Date: %s | Time: %s%n",
                appointmentId, doctorId, patientId, date, time);
    }
}