package VechileRentalManagementSystem;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        VehicleRentalSystem system = new VehicleRentalSystem();
        system.loadData();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                ===== Vehicle Rental Management Menu =====
                1. Add Car
                2. Add Motorbike
                3. List Vehicles
                4. Register Customer
                5. List Customers
                6. Create Rental
                7. List Rentals
                8. Save & Exit
                ==========================================
                Enter choice:
                """);

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Car ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Brand: ");
                    String brand = scanner.nextLine();
                    System.out.print("Enter Model: ");
                    String model = scanner.nextLine();
                    System.out.print("Enter Rental Rate per Day: ");
                    double rate = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter Number of Seats: ");
                    int seats = Integer.parseInt(scanner.nextLine());
                    Car car = new Car(id, brand, model, rate, seats);
                    system.addVehicle(car);
                    System.out.println("Car added.");
                }
                case 2 -> {
                    System.out.print("Enter Motorbike ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Brand: ");
                    String brand = scanner.nextLine();
                    System.out.print("Enter Model: ");
                    String model = scanner.nextLine();
                    System.out.print("Enter Rental Rate per Day: ");
                    double rate = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter Engine Capacity (cc): ");
                    int engineCap = Integer.parseInt(scanner.nextLine());
                    Motorbike bike = new Motorbike(id, brand, model, rate, engineCap);
                    system.addVehicle(bike);
                    System.out.println("Motorbike added.");
                }
                case 3 -> system.listVehicles();
                case 4 -> {
                    System.out.print("Enter Customer ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter License Number: ");
                    String license = scanner.nextLine();
                    System.out.print("Enter Phone: ");
                    String phone = scanner.nextLine();
                    Customer customer = new Customer(id, name, license, phone);
                    system.addCustomer(customer);
                    System.out.println("Customer registered.");
                }
                case 5 -> system.listCustomers();
                case 6 -> {
                    System.out.print("Enter Customer ID: ");
                    String custId = scanner.nextLine();
                    System.out.print("Enter Vehicle ID: ");
                    String vehId = scanner.nextLine();
                    System.out.print("Enter Start Date (YYYY-MM-DD): ");
                    String startDateStr = scanner.nextLine();
                    System.out.print("Enter End Date (YYYY-MM-DD): ");
                    String endDateStr = scanner.nextLine();
                    try {
                        LocalDate startDate = LocalDate.parse(startDateStr);
                        LocalDate endDate = LocalDate.parse(endDateStr);
                        system.createRental(custId, vehId, startDate, endDate);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                    }
                }
                case 7 -> system.listRentals();
                case 8 -> {
                    system.saveData();
                    System.out.println("Data saved. Exiting...");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
