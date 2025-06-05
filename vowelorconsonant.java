import java.util.Scanner;
public class vowelorconsonant {
    public static void main( String [] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the letter");// Ask the user to enter the character
        char ch = scanner.next().charAt(0);
        //Convert to lowercase for easy comparison
        ch=Character.toLowerCase(ch);

        if(!Character. isLetter(ch)){
            System.out.println("Invalid input.Please enter alphabet character");
        }else if(ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'){
            System.out.println(ch+"is a vowel.");
            }
        else{
            System.out.println(ch+"is a consonant.");

        }
        scanner.close();
                



        }
        

    }
    

