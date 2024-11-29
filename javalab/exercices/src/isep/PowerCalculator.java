package isep;

public class PowerCalculator {

    public static void main(String[] args) {
        PowerCalculator pc = new PowerCalculator();
        int x = 2;
        int n = 3;
        System.out.println(x + " to the power of " + n + " is " + pc.power(x,n));
    }


    public int power(int x, int n) {

        if (n == 0)
            return 1;
        return x * power(x, n-1);

    }


}
