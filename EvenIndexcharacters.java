public class EvenIndexcharacters {
    public static void main(String[] args){
        String input = "Programming";

        System.out.println("Characters at even position:");
        for (int i = 0; i < input.length(); i += 2){
            System.out.println(input.charAt(i));
        }
    }
    
}
