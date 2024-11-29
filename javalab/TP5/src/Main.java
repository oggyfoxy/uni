import recursion.Recursion;
import sorting.SortingAlgorithms;
import utilities.Utilities;

public class Main {
    public static void main(String[] args) {
        // Recursion exercises
        System.out.println("Number of digits: " + Recursion.countDigits(12345));
        System.out.println("String length: " + Recursion.stringLength("Hello"));
        System.out.println("Decimal to binary: " + Recursion.decimalToBinary(42));

        int[] array = Utilities.initializeArray(1000);
        System.out.println("Is array sorted? " + Recursion.isSorted(array, 0));

        System.out.println("String permutations:");
        Recursion.stringPermutations("abc", "");

        // Sorting exercises
        SortingAlgorithms.selectionSort(array);
        SortingAlgorithms.insertionSort(array);
        SortingAlgorithms.bubbleSort(array);

        SortingAlgorithms.quickSort(array, 0, array.length - 1);
        System.out.println("Quick sort completed!");
    }
}
