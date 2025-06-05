import java.io.*;
import java.util.*;

public class HotelManagementSystem {
    public static void main(String[] args) {
        final String ROOMS_FILE = "rooms.csv";
        final String BOOKINGS_FILE = "bookings.csv";

        ArrayList<HashMap<String, String>> rooms = new ArrayList<>();
        ArrayList<HashMap<String, String>> bookings = new ArrayList<>();

        int roomIdCounter = 1;
        int bookingIdCounter = 1;

        try {
            File roomFile = new File(ROOMS_FILE);
            if (!roomFile.exists()) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(ROOMS_FILE))) {
                    pw.println("roomID,type,pricePerNight,isAvailable");
                }
            }
            File bookingFile = new File(BOOKINGS_FILE);
            if (!bookingFile.exists()) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(BOOKINGS_FILE))) {
                    pw.println("bookingID,guestName,roomID,nights,totalPrice,status");
                }
            }
        } catch (IOException e) {
            System.out.println("Error initializing files.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ROOMS_FILE))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                HashMap<String, String> room = new HashMap<>();
                room.put("roomID", parts[0]);
                room.put("type", parts[1]);
                room.put("pricePerNight", parts[2]);
                room.put("isAvailable", parts[3]);
                rooms.add(room);
                int id = Integer.parseInt(parts[0]);
                if (id >= roomIdCounter) roomIdCounter = id + 1;
            }
        } catch (IOException e) {
            System.out.println("Error reading rooms file.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                HashMap<String, String> booking = new HashMap<>();
                booking.put("bookingID", parts[0]);
                booking.put("guestName", parts[1]);
                booking.put("roomID", parts[2]);
                booking.put("nights", parts[3]);
                booking.put("totalPrice", parts[4]);
                booking.put("status", parts[5]);
                bookings.add(booking);
                int id = Integer.parseInt(parts[0]);
                if (id >= bookingIdCounter) bookingIdCounter = id + 1;
            }
        } catch (IOException e) {
            System.out.println("Error reading bookings file.");
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("""
                ***********************
                     Hotel System Menu
                1. Add Room
                2. View All Rooms
                3. Update Room Availability
                4. Book a Room
                5. View All Bookings
                6. Cancel Booking
                0. Exit
                ***********************
                Enter your choice:
                """);

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter room type (Single/Double/Suite): ");
                String type = scanner.nextLine();
                System.out.print("Enter price per night: ");
                double price = scanner.nextDouble();
                scanner.nextLine();
                HashMap<String, String> room = new HashMap<>();
                room.put("roomID", String.valueOf(roomIdCounter++));
                room.put("type", type);
                room.put("pricePerNight", String.valueOf(price));
                room.put("isAvailable", "true");
                rooms.add(room);
                System.out.println("Room added.");

            } else if (choice == 2) {
                for (HashMap<String, String> r : rooms) {
                    System.out.printf("RoomID: %s | Type: %s | Price: %s | Available: %s%n",
                            r.get("roomID"), r.get("type"), r.get("pricePerNight"), r.get("isAvailable"));
                }

            } else if (choice == 3) {
                System.out.print("Enter roomID to update: ");
                String id = scanner.nextLine();
                boolean found = false;
                for (HashMap<String, String> r : rooms) {
                    if (r.get("roomID").equals(id)) {
                        System.out.print("Enter new availability (true/false): ");
                        String availability = scanner.nextLine();
                        r.put("isAvailable", availability);
                        found = true;
                        System.out.println("Availability updated.");
                        break;
                    }
                }
                if (!found) System.out.println("Room not found.");

            } else if (choice == 4) {
                System.out.print("Enter guest name: ");
                String guest = scanner.nextLine();
                System.out.print("Enter roomID: ");
                String roomID = scanner.nextLine();
                System.out.print("Enter number of nights: ");
                int nights = scanner.nextInt();
                scanner.nextLine();

                boolean booked = false;
                for (HashMap<String, String> r : rooms) {
                    if (r.get("roomID").equals(roomID) && r.get("isAvailable").equals("true")) {
                        double price = Double.parseDouble(r.get("pricePerNight"));
                        double total = price * nights;

                        HashMap<String, String> booking = new HashMap<>();
                        booking.put("bookingID", String.valueOf(bookingIdCounter++));
                        booking.put("guestName", guest);
                        booking.put("roomID", roomID);
                        booking.put("nights", String.valueOf(nights));
                        booking.put("totalPrice", String.valueOf(total));
                        booking.put("status", "confirmed");
                        bookings.add(booking);

                        r.put("isAvailable", "false");
                        System.out.println("Room booked successfully. Total: " + total);
                        booked = true;
                        break;
                    }
                }
                if (!booked) System.out.println("Room not available or not found.");

            } else if (choice == 5) {
                for (HashMap<String, String> b : bookings) {
                    System.out.printf("BookingID: %s | Guest: %s | RoomID: %s | Nights: %s | Total: %s | Status: %s%n",
                            b.get("bookingID"), b.get("guestName"), b.get("roomID"),
                            b.get("nights"), b.get("totalPrice"), b.get("status"));
                }

            } else if (choice == 6) {
                System.out.print("Enter bookingID to cancel: ");
                String cancelID = scanner.nextLine();
                boolean cancelled = false;
                for (HashMap<String, String> b : bookings) {
                    if (b.get("bookingID").equals(cancelID)) {
                        b.put("status", "cancelled");
                        for (HashMap<String, String> r : rooms) {
                            if (r.get("roomID").equals(b.get("roomID"))) {
                                r.put("isAvailable", "true");
                                break;
                            }
                        }
                        System.out.println("Booking cancelled.");
                        cancelled = true;
                        break;
                    }
                }
                if (!cancelled) System.out.println("Booking not found.");

            } else if (choice == 0) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(ROOMS_FILE))) {
                    pw.println("roomID,type,pricePerNight,isAvailable");
                    for (HashMap<String, String> r : rooms) {
                        pw.printf("%s,%s,%s,%s%n", r.get("roomID"), r.get("type"), r.get("pricePerNight"), r.get("isAvailable"));
                    }
                } catch (IOException e) {
                    System.out.println("Error saving rooms.");
                }

                try (PrintWriter pw = new PrintWriter(new FileWriter(BOOKINGS_FILE))) {
                    pw.println("bookingID,guestName,roomID,nights,totalPrice,status");
                    for (HashMap<String, String> b : bookings) {
                        pw.printf("%s,%s,%s,%s,%s,%s%n", b.get("bookingID"), b.get("guestName"), b.get("roomID"),
                                b.get("nights"), b.get("totalPrice"), b.get("status"));
                    }
                } catch (IOException e) {
                    System.out.println("Error saving bookings.");
                }

                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }
}