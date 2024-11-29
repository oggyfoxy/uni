package utilities;

import java.util.Random;
import java.time.Instant;
import java.time.Duration;

public class Utilities {

    private static Instant start;

    public static int[] initializeArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size);
        }
        return array;
    }

    public static void startTimer() {
        start = Instant.now();
    }

    public static void endTimer(String operationName) {
        Instant end = Instant.now();
        long duration = Duration.between(start, end).toMillis();
        System.out.println(operationName + " took " + duration + " ms");
    }
}
