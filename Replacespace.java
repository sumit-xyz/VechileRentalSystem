public class Replacespace {
    public static void main(String [] args){
        String input = "Java is fun!";
        String result = input.replaceAll(" ", "");

        System.out.println("Original:"+input);
        System.out.println("Withoutspace:"+result);
    }
    
}
