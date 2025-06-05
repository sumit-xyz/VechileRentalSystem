import java.util.Scanner;
public class firstnlastch {
    public static void main( String [] args){
        Scanner scanner = new Scanner(System.in);

        //Get input string from user
        System.out.println("Enter a string:");
        String input = scanner.nextLine();
        // Check if string is not empty
        if(input.length()>0){
            char firstchar = input.charAt(0);
            char lastchar = input.charAt(input.length()-1);

            System.out.println("Firstcharacter:"+firstchar);
            System.out.println("Lastcharacter:"+lastchar);
        }

            else{
                System.out.println("The  string is empty");

            }
            scanner.close();

        }

    }
    

