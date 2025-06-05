public class exchangevowels {
public static void main (String  []args){
    String input = "Hello, World!";
    String result = input.replaceAll("(?i)[aeiou]", "*");
    System.out.println("Original:"+ input);
    System.out.println("Modified:"+ result);
}
       
}
