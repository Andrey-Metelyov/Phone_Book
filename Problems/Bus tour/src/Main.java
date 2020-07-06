import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        int busHeight = scanner.nextInt();
        int n = scanner.nextInt();
        scanner.nextLine();
        int[] bridges = Stream.of(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        for (int i = 0; i < bridges.length; i++) {
            int bridge = bridges[i];
            if (busHeight >= bridge) {
                System.out.println("Will crash on bridge " + (i + 1));
                return;
            }
        }
        System.out.println("Will not crash");
    }
}