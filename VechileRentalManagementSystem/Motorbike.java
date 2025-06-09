package VechileRentalManagementSystem;

public class Motorbike extends Vehicle {
    private int engineCapacity; // cc

    public Motorbike(String id, String brand, String model, double rentalRatePerDay, int engineCapacity) {
        super(id, brand, model, rentalRatePerDay);
        this.engineCapacity = engineCapacity;
    }

    public int getEngineCapacity() { return engineCapacity; }

    @Override
    public void displayDetails() {
        System.out.printf("Motorbike[ID:%s, Brand:%s, Model:%s, Rate:%.2f, Engine:%dcc, Maintenance:%b, AvailableFrom:%s]\n",
                getId(), getBrand(), getModel(), getRentalRatePerDay(),
                engineCapacity, isUnderMaintenance(), getAvailableFrom());
    }

    @Override
    public boolean matches(String keyword) {
        return super.matches(keyword) || Integer.toString(engineCapacity).contains(keyword);
    }
}

