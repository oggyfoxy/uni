package recursion;

public class Recursion {

    // Exercise 3.1: Recursive Counting
    public static int countDigits(int n) {
        if (n == 0) {
            return 0;
        }
        return 1 + countDigits(n / 10);
    }

    // Exercise 3.2: String Length
    public static int stringLength(String str) {
        if (str.equals("")) {
            return 0;
        }
        return 1 + stringLength(str.substring(1));
    }

    // Exercise 3.3: Decimal to Binary Conversion
    public static String decimalToBinary(int n) {
        if (n == 0) {
            return "";
        }
        return decimalToBinary(n / 2) + (n % 2);
    }

    // Exercise 3.4: Sorted Array Check
    public static boolean isSorted(int[] array, int index) {
        if (index == array.length - 1) {
            return true;
        }
        return array[index] <= array[index + 1] && isSorted(array, index + 1);
    }

    // Exercise 3.5: String Permutations
    public static void stringPermutations(String str, String prefix) {
        if (str.length() == 0) {
            System.out.println(prefix);
        } else {
            for (int i = 0; i < str.length(); i++) {
                stringPermutations(str.substring(0, i) + str.substring(i + 1), prefix + str.charAt(i));
            }
        }
    }
}
