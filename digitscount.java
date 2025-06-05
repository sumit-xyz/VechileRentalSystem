import java.util.Scanner;
public class digitscount {
    public static void main (String[]args){
        Scanner scanner = new Scanner(System.in);
        
        //Get input from the user
        System.out.println("Enter the String:");
        String input = scanner.nextLine();

        int digitCount = 0;

        //Loop through eache character.

        for(int i=0; i < input.length(); i++){
            char ch = input.charAt(i);
            
            // Check if the character is digit
            if(Character.isDigit(ch)){
                digitCount++;           
             }
        }
        System.out.println("Number of digit:"+digitCount);

        scanner.close();
    }
}
    

