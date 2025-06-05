public class Extractnumbers {
    public static void main(String[] args) {
        String input = "Your OTP is 67000 Please Dont share";
        StringBuilder digits = new StringBuilder ();

        for (char c: input.toCharArray()) {
            if (Character.isDigit(c)) {
                digits.append(c);
            }
        }
        System.out.println("Extracted number:" + digits.toString());
    }
    
}
