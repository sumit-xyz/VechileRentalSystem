public class charcheck {
    public static void main(String[] args){
        String input = "He is Great";
        char ch = 'e';
        
    if(input.indexOf(ch) != -1){
        System.out.println("Character'"+ ch + "' exists in the string.");
    } else {
        System.out.println("Character'" + ch + "' doesnot exist in the string.");

    }
    }
    
}
