package VechileRentalManagementSystem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Rental {
    private String rentalId;
    private Customer customer;
    private Vehicle vehicle;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalCost;

    public Rental(String rentalId, Customer customer, Vehicle vehicle, LocalDate startDate, LocalDate endDate) {
        this.rentalId = rentalId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = calculateCost();
    }

    public String getRentalId() { return rentalId; }
    public Customer getCustomer() { return customer; }
    public Vehicle getVehicle() { return vehicle; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public double getTotalCost() { return totalCost; }

    public double calculateCost() {
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return days * vehicle.getRentalRatePerDay();
    }

    public void displayDetails() {
        System.out.printf("Rental[ID:%s, Customer:%s, Vehicle:%s, From:%s, To:%s, TotalCost:%.2f]\n",
                rentalId, customer.getName(), vehicle.getId(), startDate, endDate, totalCost);
    }
}

