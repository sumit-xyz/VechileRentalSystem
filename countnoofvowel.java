import java.util.Scanner;
public class countnoofvowel {
    public static void main (String[]args){
        Scanner scanner = new Scanner(System.in);
        
        //Get input from the user
        System.out.println("Enter the letter:");
        String input = scanner.nextLine();

        //Convert to LowerCase to make it case insensitive.

        input = input.toLowerCase();

        int vowelcount = 0;

        //Loop through each character.

        for(int i=0; i < input.length(); i++){
            char ch = input.charAt(i);
            // Check if the character is vowel
            if(ch == 'a' || ch == 'e' || ch == 'i'  || ch =='o' || ch =='u');{
                vowelcount++;            }
        }
        System.out.println("Number of vowel:"+vowelcount);

        scanner.close();
    }

    
}
