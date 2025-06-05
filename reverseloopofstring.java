import java.util.Scanner;
public class reverseloopofstring {
    public static void main ( String[] args){
        Scanner scanner = new Scanner(System.in);

    //Get input from user
    System.out.println("Enter a string:");
    String input = scanner.nextLine();
    
    String reversed = "";

    // Backward loop from start to end

    for(int i = input.length()-1; i>=0; i-- ){
        reversed += input.charAt(i);//All character in reverse order
    }
    System.out.println("Reversed string:"+reversed);
    scanner.close();
    }
    
}
