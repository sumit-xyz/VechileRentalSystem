import java.util.Scanner;
public class consonantcount {
    
    public static void main(String [] args){
        Scanner scanner = new Scanner (System.in);

        //user input

        System.out.println("Enter a String:");
        String input = scanner.nextLine();

        // convert into lowercase for easy program

        input = input.toLowerCase();
        int consonantCount = 0;

        //loop through character

        for(int i = 0; i < input.length(); i++ ){
        char ch = input.charAt(i);

        // check if it is a letter
        if(Character.isLetter(ch)){
            //check if its not a vowel->then its a consonant
           if(!(ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u')){
             consonantCount++;
           }
        }

    }
    System.out.println("Number of consonants:"+consonantCount);
    scanner.close();
}
}