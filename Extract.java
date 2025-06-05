public class Extract {
    public static void main (String[] args){
        String text = "Hello";

        if (text.length() >= 3) {
            String firstThree = text.substring(0, 3);
            System.out.println(firstThree);
        } else {
            System.out.println("The string is too short.");
        }
        
    }
    
}
