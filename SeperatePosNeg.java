import java.util.ArrayList;
import java.util.List;

public class SeperatePosNeg {
    public static void main (String[] args){
        int[] numbers = {4, -3, 7, -1, 0, 5, -8};

        List<Integer> positives = new ArrayList<>();
        List<Integer> negatives = new ArrayList<>();

        for (int num : numbers){
            if (num >= 0){
                positives.add(num);
            } else {
                negatives.add(num);
            }
        }

        // Print results
        System.out.println("Positive numbers: " + positives);
        System.out.println("Negative numbers: " + negatives);
    }
    
}
