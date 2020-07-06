import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        int[] array = Stream.of(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        System.out.println(bubbleSortAscSwaps(array));
    }

    public static int bubbleSortAscSwaps(int[] array) {
        int swaps = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                /* if a pair of adjacent elements has the wrong order it swaps them */
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swaps++;
                }
            }
        }

        return swaps;
    }
}