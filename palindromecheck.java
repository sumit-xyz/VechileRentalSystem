public class palindromecheck {
    public static void main (String [] args){
        String text = "madam";

        if (isPalindrome(text)) {
            System.out.println("The string is a palindrome.");
        }else{
            System.out.println("The string is not a palindrome.");
        }

    }

    public static boolean isPalindrome(String str){
        String reversed = new StringBuilder(str).reverse().toString();
        return str.equals(reversed);
    }
}
    

