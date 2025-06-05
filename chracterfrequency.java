import java.util.Scanner;
public class chracterfrequency {
    public static void main ( String[] args){
        Scanner scanner = new Scanner (System.in);   
        //input from user

        System.out.println("Enter a string:");
        String input = scanner.nextLine();

        // Input  chracter to search for

        System.out.println("Enter the character to count:");
        char target = scanner.next().charAt(0);

        int count = 0;

        // Convert to  same case to make the count case-insenstive

        input = input.toLowerCase();
        target = Character.toLowerCase(target);

        //loop through the string

        for(int i=0; i < input.length(); i++){
            if (input.charAt(i) == target){
                count++;
            }

            }
            System.out.println("Character:'"+target+"'appears"+count+"times.");
            scanner.close();


    }

    
}
