public class ArrayOperations {
    public static void main(String[] args){
        int[] numbers = {5, 2, 5, 7, 8, 5, 3};
        int target = 5;


        //1. Reverse the array
        System.out.println("Reversed array:");
        for (int i = numbers.length - 1; i >= 0; i--){
            System.out.print(numbers[i] + "");
        }

        //2. Count occurrences of target number
        int count = 0;
        for (int num : numbers) {
            if (num == target) {
                count++;
            }        
        }
        System.out.println("\n\nNumber" + target + "appears" + count + "times.");
    }
    
}
