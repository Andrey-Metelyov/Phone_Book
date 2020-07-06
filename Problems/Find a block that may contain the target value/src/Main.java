import java.util.Scanner;
import java.util.stream.Stream;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int length = Integer.parseInt(scanner.nextLine());
        int[] array = Stream.of(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        int value = Integer.parseInt(scanner.nextLine());
        int step = (int) Math.sqrt(length);
        int prevRight = 0;
        int currentRight = Math.min(step - 1, length - 1);
        while (prevRight <= currentRight) {
            if (value <= array[currentRight]) {
                System.out.println(prevRight + " " + currentRight);
                return;
            }
            prevRight = currentRight + 1;
            currentRight = Math.min(currentRight + step, length - 1);
        }
        System.out.println(-1);
    }
}