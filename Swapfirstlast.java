public class Swapfirstlast {
    public static void main(String[] args){
        int[] arr = {10, 20, 30, 40, 50};

        // Print original array

        System.out.println("Before swap:");
        for (int num : arr) {
            System.out.print(num + " ");
        }

        // Swap first and last elements
        int temp = arr[0];
        arr[0] = arr[arr.length - 1];
        arr[arr.length -1] = temp;

        //Print array after swap
        System.out.println("\nAfter swap:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }    
}
