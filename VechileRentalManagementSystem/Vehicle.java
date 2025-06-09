package VechileRentalManagementSystem;

import java.time.LocalDate;

public abstract class Vehicle implements Searchable {
    private String id;
    private String brand;
    private String model;
    private double rentalRatePerDay;
    private boolean underMaintenance = false;
    private LocalDate availableFrom = LocalDate.now();

    public Vehicle(String id, String brand, String model, double rentalRatePerDay) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.rentalRatePerDay = rentalRatePerDay;
    }

    public String getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getRentalRatePerDay() { return rentalRatePerDay; }

    public boolean isUnderMaintenance() { return underMaintenance; }
    public void setUnderMaintenance(boolean status) { this.underMaintenance = status; }

    public LocalDate getAvailableFrom() { return availableFrom; }
    public void setAvailableFrom(LocalDate date) { this.availableFrom = date; }

    public abstract void displayDetails();

    @Override
    public boolean matches(String keyword) {
        keyword = keyword.toLowerCase();
        return id.toLowerCase().contains(keyword) ||
               brand.toLowerCase().contains(keyword) ||
               model.toLowerCase().contains(keyword);
    }
}

