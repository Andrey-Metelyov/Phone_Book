import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int[] arrSorted = Stream.of(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        int k = Integer.parseInt(scanner.nextLine());
        int[] arrToFind = Stream.of(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

//        int[] arrSorted = new int[]{1, 5, 8, 12, 13};
//        int[] arrToFind = new int[]{8, 1, 23, 1, 11};

        int[] result = extendedBinarySearch(arrSorted, arrToFind);

        for (int i : result) {
            System.out.print((i >= 0 ? (i + 1) : i) + " ");
        }
    }

    private static int[] extendedBinarySearch(int[] arrSorted, int[] arrToFind) {
        int[] result = new int[arrToFind.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = binarySearch(arrSorted, arrToFind[i], 0, arrSorted.length - 1);
        }
        return result;
    }


    private static int binarySearch(int[] arr, int elem, int left, int right) {
        if (left > right) {
            return -1;
        }
        int mid = left + (right - left) / 2;
        if (elem == arr[mid]) {
            return mid;
        } else if (elem < arr[mid]) {
            return binarySearch(arr, elem, left, mid - 1);
        } else {
            return binarySearch(arr, elem, mid + 1, right);
        }
    }

}