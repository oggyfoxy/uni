package tp1;

import java.util.Scanner;

// Main class
public class Main {
    public static void main(String[] args) {

        /*

        Interaction with the User
        ---

        Scanner scanner = new Scanner(System.in);
        int myInt = scanner.nextInt();
        float myFloat = scanner.nextFloat();
        System.out.println("I have an int: " + myInt);
        System.out.println("I have a float: " + myFloat);


        - result predicted: asks user an int and float. outputs the user's int and float with the text.
        - scanner.nextInt scans the next token of input as int. scanner.nextFloat scans the next token of input as float.
        - same thing: i need to first insert an int and then a float then outputs the text + my value for both.


        Displaying messages in the console
        ---

        System.out.println("Hello, what is your first name?");
        Scanner scanner = new Scanner(System.in);
        String firstName = scanner.nextLine();
        System.out.println("Hello, " + firstName);
        scanner.close();


        TP1 functions
        ---

        sum();
        division();
        volume()
        discriminant();
        parity();
        max();
        min();
        factorial();
        countdown();
        squares();
        multiplicationTable();

         */
    }



    /* 5.1 - Sum of two integer */
    public static void sum() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the first integer");
        int firstInteger = scanner.nextInt();
        System.out.println("Please enter the second integer");
        int secondInteger = scanner.nextInt();
        int sum = firstInteger + secondInteger;

        System.out.println("The sum of " + firstInteger + " and " + secondInteger + " is equal to " + sum);
        scanner.close();

    }


    /* 5.2 - Division between two Integers */

    public static void division() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the first number");
        float firstNumber = scanner.nextFloat();
        System.out.println("Please enter the second number");
        float secondNumber = scanner.nextFloat();

        if(secondNumber == 0.0) {
            System.out.println("[Error] division par 0 impossible !");
            System.exit(0);
        }
        else {
            float division = firstNumber / secondNumber;
            System.out.println("The division of " + firstNumber + " by " + secondNumber + " is equal to " + division);

        }
        scanner.close();


    }

    /* 5.3 - Calculation of the Volume of a Rectangular Prism */

    public static void volume() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Length?");
        double length = scanner.nextDouble();
        System.out.println("Width?");
        double width = scanner.nextDouble();
        System.out.println("Height?");
        double height = scanner.nextDouble();

        double volume = length * width * height;
        System.out.println("Volume: " + volume);

        scanner.close();


        /*

        1.
        3 variables: length, width, and height.

        2.
        double or float if we want to allow decimal values, or int if only whole numbers are needed

        3.
        using scanner to ask user to insert the values

        4.
        Volume=length×width×height
        5.
        We should display the calculated volume to the user in the console using System.out.println().

        Question: What issues does this program encounter?
        - large values or decimal values so we use double here.

         */
    }


    /* 7.1 Calculation of the Discriminant of a Quadratic Equation */
    public static void discriminant() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is the value of a?");
        int a = scanner.nextInt();
        System.out.println("What is the value of b?");
        int b = scanner.nextInt();
        System.out.println("What is the value of c?");
        int c = scanner.nextInt();

        int delta = (int) (Math.pow(b, 2) - 4 * a * c);

        if (delta > 0) {
            double root1 = (-b + Math.sqrt(delta)) / (2 * a);
            double root2 = (-b - Math.sqrt(delta)) / (2 * a);
            System.out.println("The polynomial has two distinct real roots:");
            System.out.println("x1 = " + root1);
            System.out.println("x2 = " + root2);
        }
        else if (delta == 0) {
            double root = (double) -b / (2 * a);
            System.out.println("The polynomial has one double root:");
            System.out.println("x0 = " + root);
        }
        else {
            double realPart = (double) -b / (2 * a);
            double imaginaryPart = Math.sqrt(-delta) / (2 * a);
            System.out.println("The polynomial has two complex roots:");
            System.out.println("x1 = " + realPart + " + i" + imaginaryPart);
            System.out.println("x2 = " + realPart + " - i" + imaginaryPart);
        }

        scanner.close();
    }


    /* 7.2 The Parity of a Number */
    public static void parity() {


        System.out.println("Please enter an integer.");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();

        if (number % 2 == 0) {
            System.out.println("The number " + number + " is even");
        }
        else {
            System.out.println("The number " + number + " is odd");
        }

        scanner.close();


    }

    /* 7.3 Extremum Calculation */
    public static void max() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the first number");
        int firstNumber = scanner.nextInt();
        System.out.println("Please enter the second number");
        int secondNumber = scanner.nextInt();

        if (firstNumber > secondNumber) {
            System.out.println(firstNumber);
        }
        else {
            System.out.println(secondNumber);
        }

        scanner.close();

    }


    public static void min() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the first number");
        int firstNumber = scanner.nextInt();
        System.out.println("Please enter the second number");
        int secondNumber = scanner.nextInt();

        if (firstNumber < secondNumber) {
            System.out.println(firstNumber);
        }
        else {
            System.out.println(secondNumber);
        }

        scanner.close();

    }

    /* 7.4 Iterative structures */

    public static void factorial() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a non-negative integer");

        int n = scanner.nextInt();
        int factorial = 1;

        if (n < 0) {
            System.out.println("Factorial doesn't support negative numbers.");
            return;
        }

        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }

        System.out.println(n + "! = " + factorial);
        scanner.close();

        // output for n=1 was 0. changed i to 1 and fixed the problem.

    }


    /* 7.5 Countdown */
    public static void countdown() {
       for (int i = 10; i >= 0; i--) {
           System.out.println(i);
        }

        System.out.println("BOOM!");

    }

    /* 7.6 Squares */
    public static void squares() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter an integer");
        int x = scanner.nextInt();

        System.out.println(x + "\t" + x * x);
        scanner.close();

    }

    /* 7.7 Multiplication Table */
    public static void multiplicationTable() {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j < 10; j++) {
                System.out.print("\t"+i*j);
            }
            System.out.println(); // print a new line for i++
        }
           /*

            1. most appropriate loop would be a for loop
            3.         for (int i = 1; i <= 10; i++) {
                           System.out.print("\t"+i);
                       }

            */
    }

}


// additional notes: could have used Scanner scanner as arguments instead of creating each time.
