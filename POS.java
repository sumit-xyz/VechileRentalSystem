import java.io.*;
import java.util.*;

public class POS {

    public static void runPOS() {
        final String PRODUCTS_FILE = "products.csv";
        final String SALES_FILE = "sales.csv";

        ArrayList<HashMap<String, String>> products = new ArrayList<>();
        int productIdCounter = 1;
        int saleIdCounter = 1;

        // Ensure files exist
        try {
            File productsFile = new File(PRODUCTS_FILE);
            if (!productsFile.exists()) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCTS_FILE))) {
                    pw.println("ID,Name,Price,Quantity");
                }
            }
            File salesFile = new File(SALES_FILE);
            if (!salesFile.exists()) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(SALES_FILE))) {
                    pw.println("SaleID,ProductID,QuantitySold,TotalPrice");
                }
            }
        } catch (IOException e) {
            System.out.println("Error initializing files.");
        }

        // Load products
        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCTS_FILE))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                HashMap<String, String> product = new HashMap<>();
                product.put("ID", parts[0]);
                product.put("Name", parts[1]);
                product.put("Price", parts[2]);
                product.put("Quantity", parts[3]);
                products.add(product);
                int id = Integer.parseInt(parts[0]);
                if (id >= productIdCounter) productIdCounter = id + 1;
            }
        } catch (IOException e) {
            System.out.println("Error loading products.");
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
            \n*************** Menu ***************
            1. Add Product
            2. View All Products
            3. Update Product Stock by ID
            4. Delete Product by ID
            5. Make a Sale
            6. View All Sales
            0. Exit
            ***********************************
            Enter your choice: """);

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1) {
                // Add product
                System.out.print("Enter product name: ");
                String name = scanner.nextLine();
                System.out.print("Enter price: ");
                double price = scanner.nextDouble();
                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();

                HashMap<String, String> product = new HashMap<>();
                product.put("ID", String.valueOf(productIdCounter++));
                product.put("Name", name);
                product.put("Price", String.valueOf(price));
                product.put("Quantity", String.valueOf(quantity));
                products.add(product);

                // Save products
                try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCTS_FILE))) {
                    pw.println("ID,Name,Price,Quantity");
                    for (HashMap<String, String> p : products) {
                        pw.println(p.get("ID") + "," + p.get("Name") + "," + p.get("Price") + "," + p.get("Quantity"));
                    }
                } catch (IOException e) {
                    System.out.println("Error saving products.");
                }

                System.out.println("Product added.");

            } else if (choice == 2) {
                // View all products
                System.out.println("Available Products:");
                for (HashMap<String, String> p : products) {
                    System.out.printf("ID: %s | Name: %s | Price: %s | Quantity: %s%n",
                            p.get("ID"), p.get("Name"), p.get("Price"), p.get("Quantity"));
                }

            } else if (choice == 3) {
                // Update product stock
                System.out.print("Enter Product ID to update: ");
                String id = scanner.nextLine();
                boolean found = false;
                for (HashMap<String, String> p : products) {
                    if (p.get("ID").equals(id)) {
                        System.out.print("Enter new quantity: ");
                        String quantity = scanner.nextLine();
                        p.put("Quantity", quantity);

                        // Save products
                        try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCTS_FILE))) {
                            pw.println("ID,Name,Price,Quantity");
                            for (HashMap<String, String> pr : products) {
                                pw.println(pr.get("ID") + "," + pr.get("Name") + "," + pr.get("Price") + "," + pr.get("Quantity"));
                            }
                        } catch (IOException e) {
                            System.out.println("Error saving products.");
                        }

                        System.out.println("Stock updated.");
                        found = true;
                        break;
                    }
                }
                if (!found) System.out.println("Product not found.");

            } else if (choice == 4) {
                // Delete product
                System.out.print("Enter Product ID to delete: ");
                String id = scanner.nextLine();
                Iterator<HashMap<String, String>> it = products.iterator();
                boolean found = false;
                while (it.hasNext()) {
                    if (it.next().get("ID").equals(id)) {
                        it.remove();

                        // Save products
                        try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCTS_FILE))) {
                            pw.println("ID,Name,Price,Quantity");
                            for (HashMap<String, String> pr : products) {
                                pw.println(pr.get("ID") + "," + pr.get("Name") + "," + pr.get("Price") + "," + pr.get("Quantity"));
                            }
                        } catch (IOException e) {
                            System.out.println("Error saving products.");
                        }

                        System.out.println("Product deleted.");
                        found = true;
                        break;
                    }
                }
                if (!found) System.out.println("Product not found.");

            } else if (choice == 5) {
                // Make a sale
                System.out.print("Enter Product ID: ");
                String id = scanner.nextLine();
                boolean found = false;
                for (HashMap<String, String> p : products) {
                    if (p.get("ID").equals(id)) {
                        int available = Integer.parseInt(p.get("Quantity"));
                        System.out.print("Enter quantity to sell: ");
                        int qty = scanner.nextInt();
                        if (qty > available) {
                            System.out.println("Not enough stock.");
                            scanner.nextLine(); // consume leftover newline
                            found = true;
                            break;
                        }

                        System.out.print("Enter discount percentage (0 if none): ");
                        double discount = scanner.nextDouble();
                        scanner.nextLine();

                        double price = Double.parseDouble(p.get("Price"));
                        double total = qty * price;
                        total -= total * (discount / 100.0);

                        p.put("Quantity", String.valueOf(available - qty));

                        // Save products
                        try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCTS_FILE))) {
                            pw.println("ID,Name,Price,Quantity");
                            for (HashMap<String, String> pr : products) {
                                pw.println(pr.get("ID") + "," + pr.get("Name") + "," + pr.get("Price") + "," + pr.get("Quantity"));
                            }
                        } catch (IOException e) {
                            System.out.println("Error saving products.");
                        }

                        // Save sale
                        try (PrintWriter pw = new PrintWriter(new FileWriter(SALES_FILE, true))) {
                            pw.printf("%d,%s,%d,%.2f%n", saleIdCounter++, id, qty, total);
                        } catch (IOException e) {
                            System.out.println("Error recording sale.");
                        }

                        System.out.println("Sale successful! Total: " + total);
                        found = true;
                        break;
                    }
                }
                if (!found) System.out.println("Product not found.");

            } else if (choice == 6) {
                // View all sales
                try (BufferedReader br = new BufferedReader(new FileReader(SALES_FILE))) {
                    String line = br.readLine(); // header
                    System.out.println("Sales Records:");
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(",");
                        System.out.printf("SaleID: %s | ProductID: %s | Quantity: %s | Total: %s%n",
                                parts[0], parts[1], parts[2], parts[3]);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading sales.");
                }

            } else if (choice == 0) {
                // Exit and save products
                try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCTS_FILE))) {
                    pw.println("ID,Name,Price,Quantity");
                    for (HashMap<String, String> p : products) {
                        pw.println(p.get("ID") + "," + p.get("Name") + "," + p.get("Price") + "," + p.get("Quantity"));
                    }
                } catch (IOException e) {
                    System.out.println("Error saving products.");
                }
                System.out.println("Exiting...");
                break;

            } else {
                System.out.println("Invalid choice!");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        runPOS();
    }
}