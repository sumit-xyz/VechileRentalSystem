import java.io.*;
import java.util.*;

public class POS2 {

    public static void main(String[] args) {
        final String PRODUCT_FILE = "products2.csv";
        final String SALES_FILE = "sales2.csv";

        ArrayList<HashMap<String, String>> products = new ArrayList<>();
        int productIdCounter = 1;
        int saleIdCounter = 1;

        try {
            File productsFile = new File(PRODUCT_FILE);
            if (!productsFile.exists()) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCT_FILE))) {
                    pw.println("id,name,price,quantity");
                }
            }
            File salesFile = new File(SALES_FILE);
            if (!salesFile.exists()) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(SALES_FILE))) {
                    pw.println("saleID,productID,quantitySold,unitPrice,discountPercent,totalPrice");
                }
            }
        } catch (IOException e) {
            System.out.println("Error initializing files.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE))) {
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
                if (id >= productIdCounter)
                    productIdCounter = id + 1;
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
                    Enter your choice:""");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                // Add Product
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

                // Save products to file
                try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCT_FILE))) {
                    pw.println("id,name,price,quantity");
                    for (HashMap<String, String> p : products) {
                        pw.println(p.get("ID") + "," + p.get("Name") + "," +
                                p.get("Price") + "," + p.get("Quantity"));
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
                // Update product stock by ID
                System.out.print("Enter Product ID to update: ");
                String id = scanner.nextLine();
                boolean found = false;
                for (HashMap<String, String> p : products) {
                    if (p.get("ID").equals(id)) {
                        System.out.print("Enter new quantity: ");
                        String quantity = scanner.nextLine();
                        p.put("Quantity", quantity);
                        found = true;

                        // Save updated products
                        try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCT_FILE))) {
                            pw.println("id,name,price,quantity");
                            for (HashMap<String, String> pr : products) {
                                pw.println(pr.get("ID") + "," + pr.get("Name") + "," +
                                        pr.get("Price") + "," + pr.get("Quantity"));
                            }
                        } catch (IOException e) {
                            System.out.println("Error saving products.");
                        }

                        System.out.println("Stock updated.");
                        break;
                    }
                }
                if (!found)
                    System.out.println("Product not found.");

            } else if (choice == 4) {
                // Delete product by ID
                System.out.print("Enter Product ID to delete: ");
                String id = scanner.nextLine();
                Iterator<HashMap<String, String>> it = products.iterator();
                boolean found = false;
                while (it.hasNext()) {
                    if (it.next().get("ID").equals(id)) {
                        it.remove();
                        found = true;

                        // Save updated products
                        try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCT_FILE))) {
                            pw.println("id,name,price,quantity");
                            for (HashMap<String, String> pr : products) {
                                pw.println(pr.get("ID") + "," + pr.get("Name") + "," +
                                        pr.get("Price") + "," + pr.get("Quantity"));
                            }
                        } catch (IOException e) {
                            System.out.println("Error saving products.");
                        }

                        System.out.println("Product deleted.");
                        break;
                    }
                }
                if (!found)
                    System.out.println("Product not found.");

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
                            scanner.nextLine();
                            found = true;
                            break;
                        }

                        System.out.print("Enter discount percentage (0 if none): ");
                        double discount = scanner.nextDouble();
                        scanner.nextLine();

                        double price = Double.parseDouble(p.get("Price"));
                        double total = qty * price;
                        double discountAmount = total * (discount / 100.0);
                        double finalPrice = total - discountAmount;

                        // Update stock
                        p.put("Quantity", String.valueOf(available - qty));

                        // Save products after sale
                        try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCT_FILE))) {
                            pw.println("id,name,price,quantity");
                            for (HashMap<String, String> pr : products) {
                                pw.println(pr.get("ID") + "," + pr.get("Name") + "," +
                                        pr.get("Price") + "," + pr.get("Quantity"));
                            }
                        } catch (IOException e) {
                            System.out.println("Error saving products.");
                        }

                        // Append sale to sales file
                        try (PrintWriter pw = new PrintWriter(new FileWriter(SALES_FILE, true))) {
                            pw.printf("%d,%s,%d,%.2f,%.2f,%.2f%n",
                                    saleIdCounter++, id, qty, price, discount, finalPrice);
                        } catch (IOException e) {
                            System.out.println("Error recording sale.");
                        }

                        System.out.println("Sale successful! Final total: " + finalPrice);
                        found = true;
                        break;
                    }
                }
                if (!found)
                    System.out.println("Product not found.");

            } else if (choice == 6) {
                // View all sales
                try (BufferedReader br = new BufferedReader(new FileReader(SALES_FILE))) {
                    String line = br.readLine();
                    System.out.println("Sales Records:");
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(",");
                        System.out.printf(
                                "SaleID: %s | ProductID: %s | Quantity: %s | UnitPrice: %s | Discount: %s%% | Total: %s%n",
                                parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading sales.");
                }

            } else if (choice == 0) {
                // Exit: save products and stop program
                try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCT_FILE))) {
                    pw.println("id,name,price,quantity");
                    for (HashMap<String, String> p : products) {
                        pw.println(p.get("ID") + "," + p.get("Name") + "," +
                                p.get("Price") + "," + p.get("Quantity"));
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
}
