package isep;

// ex 3-4
public class SortedArray {


    public static boolean isSorted(int[] array, int index) {
        if (index == array.length - 1) {
            return true;
        }

        if (array[index] > array[index + 1]) {
            return false;
        }

        return isSorted(array, index + 1);
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5};
        System.out.println("Is the array sorted? " + isSorted(array, 0)); // Output: true

        int[] unsortedArray = {1, 3, 2, 4, 5};
        System.out.println("Is the array sorted? " + isSorted(unsortedArray, 0)); // Output: false
    }
}