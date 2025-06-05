public class Findlargest {
    public static void main(String[] args){
        int[] arr = {12, 45, 7, 89, 34};

        // Assume the first element is the Largest initially
        int max = arr[0];

        // Loop through the array
        for (int num : arr) {
            if (num > max) {
                max = num;// Update max if a bigger number is found
            }
        }

        System.out.println("The largest number is:" + max);
    
    }
    
}
