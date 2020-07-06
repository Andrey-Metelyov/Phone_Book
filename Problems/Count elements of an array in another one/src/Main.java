import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static int[] countOccurrences(int[] first, int[] second) {
        // write your code here
        int[] result = new int[first.length];
        for (int i = 0; i < first.length; i++) {
            result[i] = count(second, first[i]);
        }
        return result;
    }

    public static int count(int[] numbers, int value) {
        int count = 0;
        for (int number : numbers) {
            if (number == value) {
                ++count;
            }
        }
        return count;
    }

    /* Do not change code below */
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int[] first = Arrays.stream(scanner.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        final int[] second = Arrays.stream(scanner.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        final String result = Arrays.toString(countOccurrences(first, second))
                .replace(", ", " ")
                .replace("[", "")
                .replace("]", "");
        System.out.println(result);
    }
}