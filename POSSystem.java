import java.io.*;
import java.util.*;

public class POSSystem {
    static String productFile = "products.csv";
    static String salesFile = "sales.csv";
    static ArrayList<HashMap<String, String>> products = new ArrayList<>();
    static int nextProductId = 1;
    static int nextSaleId = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadProducts();
        initializeSalesId();

        while (true) {
            System.out.println("\n********** Menu **********");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Update Product Stock by ID");
            System.out.println("4. Delete Product by ID");
            System.out.println("5. Make a Sale");
            System.out.println("6. View All Sales");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addProduct(scanner);
                case 2 -> viewProducts();
                case 3 -> updateProduct(scanner);
                case 4 -> deleteProduct(scanner);
                case 5 -> makeSale(scanner);
                case 6 -> viewSales();
                case 0 -> {
                    saveProducts();
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void loadProducts() {
        File file = new File(productFile);
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(productFile))) {
                pw.println("id,name,price,quantity");
            } catch (IOException e) {
                System.out.println("Error creating product file.");
            }
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                br.readLine(); // skip header
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        HashMap<String, String> product = new HashMap<>();
                        product.put("id", parts[0]);
                        product.put("name", parts[1]);
                        product.put("price", parts[2]);
                        product.put("quantity", parts[3]);
                        products.add(product);
                        nextProductId = Math.max(nextProductId, Integer.parseInt(parts[0]) + 1);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading products.");
            }
        }
    }

    static void initializeSalesId() {
        File file = new File(salesFile);
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(salesFile))) {
                pw.println("saleID,productID,quantitySold,unitPrice,discountPercent,totalPrice");
            } catch (IOException e) {
                System.out.println("Error creating sales file.");
            }
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                br.readLine(); // skip header
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 1) {
                        nextSaleId = Math.max(nextSaleId, Integer.parseInt(parts[0]) + 1);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading sales.");
            }
        }
    }

    static void saveProducts() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(productFile))) {
            pw.println("id,name,price,quantity");
            for (HashMap<String, String> p : products) {
                pw.println(p.get("id") + "," + p.get("name") + "," + p.get("price") + "," + p.get("quantity"));
            }
        } catch (IOException e) {
            System.out.println("Error saving products.");
        }
    }

    static void addProduct(Scanner scanner) {
        HashMap<String, String> product = new HashMap<>();
        product.put("id", String.valueOf(nextProductId));
        System.out.print("Enter product name: ");
        product.put("name", scanner.nextLine());
        System.out.print("Enter price: ");
        product.put("price", scanner.nextLine());
        System.out.print("Enter quantity: ");
        product.put("quantity", scanner.nextLine());
        products.add(product);
        nextProductId++;
        saveProducts();
        System.out.println("Product added.");
    }

    static void viewProducts() {
        for (HashMap<String, String> p : products) {
            System.out.println("ID: " + p.get("id") + " | Name: " + p.get("name") + " | Price: " + p.get("price") + " | Quantity: " + p.get("quantity"));
        }
    }

    static void updateProduct(Scanner scanner) {
        System.out.print("Enter product ID to update: ");
        String id = scanner.nextLine();
        for (HashMap<String, String> p : products) {
            if (p.get("id").equals(id)) {
                System.out.print("Enter new quantity: ");
                p.put("quantity", scanner.nextLine());
                saveProducts();
                System.out.println("Product updated.");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    static void deleteProduct(Scanner scanner) {
        System.out.print("Enter product ID to delete: ");
        String id = scanner.nextLine();
        Iterator<HashMap<String, String>> it = products.iterator();
        while (it.hasNext()) {
            if (it.next().get("id").equals(id)) {
                it.remove();
                saveProducts();
                System.out.println("Product deleted.");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    static void makeSale(Scanner scanner) {
        System.out.print("Enter product ID to sell: ");
        String id = scanner.nextLine();
        for (HashMap<String, String> p : products) {
            if (p.get("id").equals(id)) {
                int available = Integer.parseInt(p.get("quantity"));
                System.out.print("Enter quantity to sell: ");
                int quantity = Integer.parseInt(scanner.nextLine());
                if (quantity > available) {
                    System.out.println("Not enough stock.");
                    return;
                }

                int unitPrice = Integer.parseInt(p.get("price"));
                int total = unitPrice * quantity;

                System.out.print("Enter discount percentage: ");
                int discount = Integer.parseInt(scanner.nextLine());
                int discountAmount = (total * discount) / 100;
                int finalPrice = total - discountAmount;

                // update stock
                p.put("quantity", String.valueOf(available - quantity));
                saveProducts();

                // record sale
                try (PrintWriter pw = new PrintWriter(new FileWriter(salesFile, true))) {
                    pw.println(nextSaleId + "," + id + "," + quantity + "," + unitPrice + "," + discount + "," + finalPrice);
                    nextSaleId++;
                    System.out.println("Sale recorded. Total Price: " + finalPrice);
                } catch (IOException e) {
                    System.out.println("Error recording sale.");
                }
                return;
            }
        }
        System.out.println("Product not found.");
    }

    static void viewSales() {
        try (BufferedReader br = new BufferedReader(new FileReader(salesFile))) {
            String line;
            br.readLine(); // header
            System.out.println("Sales Records:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading sales.");
        }
    }
}
