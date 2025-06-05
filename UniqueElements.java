import java.util.HashMap;
public class UniqueElements {
    public static void main(String [] args){
        int[] arr = {2,3,4,2,5,3,6};


        // Step 1: Count occurrences
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr){
            map.put(num, map.getOrDefault(num, 0) +1);
        }

        // Step 2: Print elements that occur only once
        System.out.println("Unique elements:");
        for (int num : arr){
            if (map.get(num) == 1) {
                System.out.print(num + " ");
            }
        }
    }

    
}
