package VechileRentalManagementSystem;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class VehicleRentalSystem {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Rental> rentals = new ArrayList<>();
    private int rentalCounter = 1;

    // Vehicle methods
    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public void listVehicles() {
        for (Vehicle v : vehicles) v.displayDetails();
    }

    public Vehicle findVehicleById(String id) {
        for (Vehicle v : vehicles) {
            if (v.getId().equalsIgnoreCase(id)) return v;
        }
        return null;
    }

    // Customer methods
    public void addCustomer(Customer c) {
        customers.add(c);
    }

    public void listCustomers() {
        for (Customer c : customers) c.displayDetails();
    }

    public Customer findCustomerById(String id) {
        for (Customer c : customers) {
            if (c.getId().equalsIgnoreCase(id)) return c;
        }
        return null;
    }

    // Rental methods
    public void createRental(String customerId, String vehicleId, LocalDate start, LocalDate end) {
        Customer c = findCustomerById(customerId);
        Vehicle v = findVehicleById(vehicleId);

        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }
        if (v == null) {
            System.out.println("Vehicle not found.");
            return;
        }
        if (v.isUnderMaintenance()) {
            System.out.println("Vehicle under maintenance.");
            return;
        }
        if (start.isBefore(v.getAvailableFrom())) {
            System.out.println("Vehicle not available until " + v.getAvailableFrom());
            return;
        }
        String rentalId = "R" + rentalCounter++;
        Rental rental = new Rental(rentalId, c, v, start, end);
        rentals.add(rental);

        // Update vehicle availability
        v.setAvailableFrom(end.plusDays(1));

        System.out.println("Rental created: " + rentalId);
    }

    public void listRentals() {
        for (Rental r : rentals) r.displayDetails();
    }

    // CSV Persistence
    public void saveData() {
        saveVehicles("vehicles.csv");
        saveCustomers("customers.csv");
        saveRentals("rentals.csv");
    }

    public void loadData() {
        loadVehicles("vehicles.csv");
        loadCustomers("customers.csv");
        loadRentals("rentals.csv");
    }

    private void saveVehicles(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("id,type,brand,model,rentalRatePerDay,numberOfSeats,engineCapacity,underMaintenance,availableFrom");
            for (Vehicle v : vehicles) {
                String type = v instanceof Car ? "Car" : "Motorbike";
                String seats = "";
                String engineCap = "";
                if (v instanceof Car) seats = String.valueOf(((Car) v).getNumberOfSeats());
                else if (v instanceof Motorbike) engineCap = String.valueOf(((Motorbike) v).getEngineCapacity());
                pw.printf("%s,%s,%s,%s,%.2f,%s,%s,%b,%s\n",
                        v.getId(), type, v.getBrand(), v.getModel(),
                        v.getRentalRatePerDay(), seats, engineCap, v.isUnderMaintenance(), v.getAvailableFrom());
            }
        } catch (IOException e) {
            System.out.println("Error saving vehicles: " + e.getMessage());
        }
    }

    private void loadVehicles(String filename) {
        vehicles.clear();
        Path path = Paths.get(filename);
        if (!Files.exists(path)) return;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                String id = parts[0];
                String type = parts[1];
                String brand = parts[2];
                String model = parts[3];
                double rate = Double.parseDouble(parts[4]);
                boolean maintenance = Boolean.parseBoolean(parts[7]);
                LocalDate avail = LocalDate.parse(parts[8]);

                if (type.equalsIgnoreCase("Car")) {
                    int seats = parts[5].isEmpty() ? 0 : Integer.parseInt(parts[5]);
                    Car car = new Car(id, brand, model, rate, seats);
                    car.setUnderMaintenance(maintenance);
                    car.setAvailableFrom(avail);
                    vehicles.add(car);
                } else if (type.equalsIgnoreCase("Motorbike")) {
                    int engineCap = parts[6].isEmpty() ? 0 : Integer.parseInt(parts[6]);
                    Motorbike bike = new Motorbike(id, brand, model, rate, engineCap);
                    bike.setUnderMaintenance(maintenance);
                    bike.setAvailableFrom(avail);
                    vehicles.add(bike);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading vehicles: " + e.getMessage());
        }
    }

    private void saveCustomers(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("id,name,licenseNumber,phone");
            for (Customer c : customers) {
                pw.printf("%s,%s,%s,%s\n", c.getId(), c.getName(), c.getLicenseNumber(), c.getPhone());
            }
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }

    private void loadCustomers(String filename) {
        customers.clear();
        Path path = Paths.get(filename);
        if (!Files.exists(path)) return;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                String id = parts[0];
                String name = parts[1];
                String license = parts[2];
                String phone = parts[3];
                Customer c = new Customer(id, name, license, phone);
                customers.add(c);
            }
        } catch (IOException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }

    private void saveRentals(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("rentalId,customerId,vehicleId,startDate,endDate,totalCost");
            for (Rental r : rentals) {
                pw.printf("%s,%s,%s,%s,%s,%.2f\n",
                        r.getRentalId(), r.getCustomer().getId(), r.getVehicle().getId(),
                        r.getStartDate(), r.getEndDate(), r.getTotalCost());
            }
        } catch (IOException e) {
            System.out.println("Error saving rentals: " + e.getMessage());
        }
    }

    private void loadRentals(String filename) {
        rentals.clear();
        Path path = Paths.get(filename);
        if (!Files.exists(path)) return;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                String rentalId = parts[0];
                String customerId = parts[1];
                String vehicleId = parts[2];
                LocalDate start = LocalDate.parse(parts[3]);
                LocalDate end = LocalDate.parse(parts[4]);
                double cost = Double.parseDouble(parts[5]);

                Customer c = findCustomerById(customerId);
                Vehicle v = findVehicleById(vehicleId);
                if (c != null && v != null) {
                    Rental r = new Rental(rentalId, c, v, start, end);
                    rentals.add(r);
                    rentalCounter = Math.max(rentalCounter, Integer.parseInt(rentalId.substring(1)) + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading rentals: " + e.getMessage());
        }
    }
}

