package HospitalManagementSystem;

import java.io.*;
import java.util.*;

public class FileHandler {
    public static void writeToFile(String filename, List<String> lines) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (String line : lines) {
                pw.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + filename);
        }
    }

    public static List<String> readFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return lines;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
        }
        return lines;
    }
}