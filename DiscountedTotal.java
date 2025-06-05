public class DiscountedTotal {
    public static void main(String[] args){
        double[] prices = {80, 120, 60, 150, 90};

        double total = 0;
        
        for (double price : prices){
            if (price > 100){
                price = price * 0.9; //Apply 10% discount
            }
            total += price;
            }
            System.out.printf("Total price after discount: %.2f", total);
            }
        
    }
    
