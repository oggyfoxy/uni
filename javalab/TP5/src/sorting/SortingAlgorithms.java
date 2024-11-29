package sorting;

import utilities.Utilities;

public class SortingAlgorithms {

    // Exercise 4.4: Selection Sort
    public static void selectionSort(int[] array) {
        int[] copy = array.clone();
        Utilities.startTimer();
        for (int i = 0; i < copy.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < copy.length; j++) {
                if (copy[j] < copy[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = copy[minIndex];
            copy[minIndex] = copy[i];
            copy[i] = temp;
        }
        Utilities.endTimer("Selection Sort");
    }

    // Exercise 4.5: Insertion Sort
    public static void insertionSort(int[] array) {
        int[] copy = array.clone();
        Utilities.startTimer();
        for (int i = 1; i < copy.length; i++) {
            int key = copy[i];
            int j = i - 1;
            while (j >= 0 && copy[j] > key) {
                copy[j + 1] = copy[j];
                j--;
            }
            copy[j + 1] = key;
        }
        Utilities.endTimer("Insertion Sort");
    }

    // Exercise 4.6: Bubble Sort (Iterative)
    public static void bubbleSort(int[] array) {
        int[] copy = array.clone();
        Utilities.startTimer();
        for (int i = 0; i < copy.length - 1; i++) {
            for (int j = 0; j < copy.length - i - 1; j++) {
                if (copy[j] > copy[j + 1]) {
                    int temp = copy[j];
                    copy[j] = copy[j + 1];
                    copy[j + 1] = temp;
                }
            }
        }
        Utilities.endTimer("Bubble Sort");
    }

    // Exercise 4.7: Quick Sort
    public static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(array, left, right);
            quickSort(array, left, partitionIndex - 1);
            quickSort(array, partitionIndex + 1, right);
        }
    }

    private static int partition(int[] array, int left, int right) {
        int pivot = array[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (array[j] <= pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[right];
        array[right] = temp;
        return i + 1;
    }
}
