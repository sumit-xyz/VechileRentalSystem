import java.util.Scanner;
public class stringcharacter {
     public static void main ( String[] args){
        Scanner scanner = new Scanner(System.in);


        //Get string input from user
        System.out.print("Enter a string:");
        String input = scanner.nextLine();

        //Loop through each character
        for (int i = 0; i < input.length(); i++){
            char ch = input.charAt(i);
            System.out.println(ch);
        }
        scanner.close();
     }
    
}
