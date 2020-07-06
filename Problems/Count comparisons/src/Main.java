import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = Integer.parseInt(scanner.nextLine());
        int[] array = Stream.of(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        int value = Integer.parseInt(scanner.nextLine());
        int counter = 1;
        if (value == array[0]) {
            System.out.println(counter);
            return;
        }
        int step = (int) Math.sqrt(length);
        int prevRight = 0;
        int currentRight = Math.min(step, length - 1);
        while (prevRight < currentRight) {
            if (value <= array[currentRight]) {
                // value MUST be here or nowhere!!!
                for (int i = currentRight; i > prevRight; i--) {
                    counter++;
                    if (array[i] <= value) {
                        // here
                        System.out.println(counter);
                        return;
                    }
                }
                // nowhere
                System.out.println(counter);
                return;
            }
            counter++;
            prevRight = currentRight;
            currentRight = Math.min(currentRight + step, length - 1);
        }
        System.out.println(counter);
    }
}