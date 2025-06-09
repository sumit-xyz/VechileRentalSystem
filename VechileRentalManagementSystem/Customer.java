package VechileRentalManagementSystem;

 
public class Customer {
    private String id;
    private String name;
    private String licenseNumber;
    private String phone;

    public Customer(String id, String name, String licenseNumber, String phone) {
        this.id = id;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.phone = phone;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getLicenseNumber() { return licenseNumber; }
    public String getPhone() { return phone; }

    public void displayDetails() {
        System.out.printf("Customer[ID:%s, Name:%s, License:%s, Phone:%s]\n", id, name, licenseNumber, phone);
    }

    public boolean matches(String keyword) {
        keyword = keyword.toLowerCase();
        return id.toLowerCase().contains(keyword) ||
               name.toLowerCase().contains(keyword) ||
               licenseNumber.toLowerCase().contains(keyword) ||
               phone.toLowerCase().contains(keyword);
    }
}

