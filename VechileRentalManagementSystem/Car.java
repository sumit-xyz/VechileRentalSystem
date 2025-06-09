package VechileRentalManagementSystem;

public class Car extends Vehicle {
    private int numberOfSeats;

    public Car(String id, String brand, String model, double rentalRatePerDay, int numberOfSeats) {
        super(id, brand, model, rentalRatePerDay);
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() { return numberOfSeats; }

    @Override
    public void displayDetails() {
        System.out.printf("Car[ID:%s, Brand:%s, Model:%s, Rate:%.2f, Seats:%d, Maintenance:%b, AvailableFrom:%s]\n",
                getId(), getBrand(), getModel(), getRentalRatePerDay(),
                numberOfSeats, isUnderMaintenance(), getAvailableFrom());
    }

    @Override
    public boolean matches(String keyword) {
        return super.matches(keyword) || Integer.toString(numberOfSeats).contains(keyword);
    }
}

