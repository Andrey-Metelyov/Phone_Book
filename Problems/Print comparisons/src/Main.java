import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int blockSize = (int) Math.sqrt(n);
        int currentRight = 0;
        int prevRight = 0;
        int step = 1;
        System.out.print(step + " ");
        step++;
        currentRight = Math.min(currentRight + blockSize, n - 1);
        while (prevRight < currentRight) {
            for (int i = prevRight + 1; i <= currentRight; i++) {
                System.out.print((step + currentRight - i) + " ");
            }
            step++;
            prevRight = currentRight;
            currentRight = Math.min(currentRight + blockSize, n - 1);
        }
    }
}