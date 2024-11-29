package isep; // Ex 3-3

public class Decimal2Binary {

    public static String decimalToBinary(int n) {
        if (n == 0) {
            return "0";
        } else if (n == 1) {
            return "1";
        } else {
            return decimalToBinary(n / 2) + (n % 2);
        }
    }

    public static void main(String[] args) {
        int number = 10;
        String binary = decimalToBinary(number);
        System.out.println("Binary of " + number + " is: " + binary);
    }
}