import com.sun.tools.javac.Main;

import java.util.Arrays;
import java.util.Scanner;

// TP2
public class Main2 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("What exercice ? Enter :");
        System.out.println("[1] Discriminant");
        System.out.println("[2] Parite d'un nombre");
        System.out.println("[3] Calcul d'extremum");
        System.out.println("[4] Factorielle");
        System.out.println("[5] Compte a rebours");
        System.out.println("[6] Valeurs de carrés");
        System.out.println("[7] Table de multiplication");
        System.out.println("[8] Division d'entier");
        System.out.println("[9] Regle graduee");
        System.out.println("[10] Nombres premiers");
        System.out.println("[11] Manipulation sur tableau");
        System.out.println("[12] Inverse d'un tableau");
        System.out.println("[13] Rechercher un caractere");
        System.out.println("[14] Distance de hamming");
        System.out.println("[15] Suppression d'une chaine de caractere");
        System.out.println("[16] Scrabble");
        System.out.println("[17] Anagramme");
        System.out.println("[18] Calculatrice");



        System.out.print("\nChoisissez une fonction : ");

        int c = scanner.nextInt();
        Integer[] intArray = {10,20,30,40,50,60,70,80};

        switch(c)
        {
            case 1:
                discriminant();
                break;
            case 2:
                parity();
                break;
            case 3:
                max();
                break;
            case 4:
                factorial();
                break;
            case 5:
                countdown();
                break;
            case 6:
                squares();
                break;
            case 7:
                multiplicationTable();
                break;
            case 8:
                division();
                break;
            case 9:
                regle();
                break;
            case 10:
                nombrePremier();
                break;
            case 11:
                initialisationTableau();
                break;
            case 12:
                inverseTableau(intArray);
                break;
            case 13:
                int result = cherche('a', "ecole");
                System.out.println(result);
                break;
            case 14:
                int result2 = hamming("stylo", "bouteille");
                System.out.println(result2);
                break;
            case 15:
                String suppr = suppresion('a', "baldaquin");
                System.out.println(suppr);
                break;
            case 16:
                boolean scra = scrabble("bungalows", "hbteslo");
                System.out.println(scra);
                break;
            case 17:
                boolean ana = anagramme("parisien", "aspirine");
                System.out.println(ana);
                break;
            case 18:
                int nombre = calculatrice("2+6+74+13");
                System.out.println(nombre);
                break;
            default:
                System.out.println("Erreur");
                break;

        }

    }


    /* 5.2 - Division between two Integers */
    public static void division() {
        Main1.division();
    }


    /* 7.1 Calculation of the Discriminant of a Quadratic Equation */
    public static void discriminant() {
        Main1.discriminant();
    }


    /* 7.2 The Parity of a Number */
    public static void parity() {
        Main1.parity();
    }

    /* 7.3 Extremum Calculation */
    public static void max() {
        Main1.max();
    }

    /* 7.4 Iterative structures */
    public static void factorial() {
        Main1.factorial();
    }


    /* 7.5 Countdown */
    public static void countdown() {
        Main1.countdown();
    }

    /* 7.6 Squares */
    public static void squares() {
       Main1.squares();
    }

    /* 7.7 Multiplication Table */
    public static void multiplicationTable() {
        Main1.multiplicationTable();
    }


    public static void regle() {
        Scanner scanner = new Scanner(System.in);
        int l;
        do {
            System.out.print("Saisir la longueur de la regle (superieur a 0) : ");
            l = scanner.nextInt();
        } while (l <= 0);

        System.out.print("|");
        for (int i = 1; i <= l; i++) {
            if (i % 10 == 0)
                System.out.print("|");
            else
                System.out.print("-");

        }
        System.out.print("\n");

    }

    public static void nombrePremier() {

        Scanner scanner = new Scanner(System.in);
        int n;

        do {
            System.out.print("Saisir une valeur (superieur a 0) : ");
            n = scanner.nextInt();
        } while (n <= 0);

        int diviseur = 0;
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                diviseur++;
            }
        }

        if (diviseur == 2)
            System.out.println(n + " est un nombre premier");
        else
            System.out.println(n + " n'est pas un nombre premier");
    }


    public static void initialisationTableau() {
        Scanner scanner = new Scanner(System.in);
        int[] tableau = new int[10];

        System.out.println("Saisir un entier");
        int entier = scanner.nextInt();
        tableau[0] = entier;


        /*

        min value
        ---

        int entier = scanner.nextInt();
        tableau[0] = entier;
        int min = entier;


          for (int i = 1; i < tableau.length; i++) {
            entier = scanner.nextInt();
            tableau[i] = entier;
            if (entier < min) {
                min = entier;
            }
        }

        System.out.println("min is: " + min);

        max values
        ---

        int max = entier;

        for (int i = 1; i < tableau.length; i++) {
            entier = scanner.nextInt();
            tableau[i] = entier;
            if (entier > max) {
                max = entier;
            }
        }

        System.out.println("max is: " + max);


        elements pairs
        ---

        for (int i = 1; i < tableau.length; i++) {
            entier = scanner.nextInt();
            if (entier % 2 == 0) {
                tableau[i] = entier;
            }
        }

        */

        for (int i = 1; i < tableau.length; i++) {
            entier = scanner.nextInt();
            if (i % 2 == 0) {
                tableau[i] = entier;
            }
        }

        System.out.println(Arrays.toString(tableau));

    }

    public static void inverseTableau(Integer[] tableau) {

        int l = tableau.length-1;
        int[] newtableau = new int[tableau.length];

        System.out.println("Saisir un entier");
        for (int i = 0; i < tableau.length; i++) {
            newtableau[l] = tableau[i];
            l--;
        }
        System.out.println(Arrays.toString(newtableau));

    }

    public static int cherche(char c, String s) {

        /*


        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                return true;
            }
        }
        return false;
        */
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                return i;
            }
        }
        return -1;

    }


    public static int hamming(String s, String t) {
        if (s.length() != t.length())
                return -1;

        int dist = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != t.charAt(i))
                dist++;
        }
        return dist;
    }

    public static String suppresion(char c, String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                String str = String.valueOf(c);
                s = s.replaceFirst(str,"");
                break;
            }

        }
        return s;
    }

    public static boolean scrabble(String mot, String lettres_disponibles) {

        for (int i = 0; i < mot.length(); i++) {
            if (lettres_disponibles.indexOf(mot.charAt(i)) == -1 ) {
                return false;
            }
            else {
                suppresion(mot.charAt(i), lettres_disponibles);
            }
        }
        return true;
    }

    public static boolean anagramme(String u, String v) {
        if (u.length() != v.length())
            return false;
        char[] string1 = u.toCharArray();
        char[] string2 = v.toCharArray();
        Arrays.sort(string1);
        Arrays.sort(string2);

        return Arrays.equals(string1, string2);

    }

    public static int calculatrice(String s) {

        if (s.length() == 0 || s.startsWith("+") || s.endsWith("+") || s.contains("++"))
            return -1;

        String[] numbers = s.split("\\+");
        int totalSum = 0;

        for (String number : numbers) {
            if (!number.matches("\\d+")) {
                return -1;
            }
            totalSum += Integer.parseInt(number);
        }
        return totalSum;

    }

}