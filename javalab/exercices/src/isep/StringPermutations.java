package isep;

import java.util.ArrayList;
import java.util.List;

public class StringPermutations {

    public static List<String> generatePermutations(String str) {
        List<String> permutations = new ArrayList<>();
        permute(str, "", permutations);
        return permutations;
    }

    private static void permute(String str, String prefix, List<String> permutations) {
        // Base case: if the string is empty, add the prefix as a permutation
        if (str.isEmpty()) {
            permutations.add(prefix);
        } else {
            // Recursive case: generate permutations by removing one character at a time
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                String remaining = str.substring(0, i) + str.substring(i + 1);
                permute(remaining, prefix + ch, permutations);
            }
        }
    }

    public static void main(String[] args) {
        String input = "ABC";
        List<String> permutations = generatePermutations(input);
        System.out.println("Permutations of " + input + ":");
        for (String permutation : permutations) {
            System.out.println(permutation);
        }
    }
}
