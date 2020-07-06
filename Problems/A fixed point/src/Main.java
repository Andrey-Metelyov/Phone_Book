import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int[] arr = Stream.of(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        System.out.println(haveFixedPoint(arr));
    }

    private static boolean haveFixedPoint(int[] arr) {
        return binarySearch(arr, 0, arr.length);
    }

    private static boolean binarySearch(int[] arr, int left, int right) {
        if (left > right) {
            return false;
        }
        int mid = left + (right - left) / 2;
        if (arr[mid] == mid) {
            return true;
        }
        if (mid < arr[mid]) {
            return binarySearch(arr, left, mid - 1);
        } else {
            return binarySearch(arr, mid + 1, right);
        }
    }
}