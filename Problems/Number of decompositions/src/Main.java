import java.util.*;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        printDecomposition(number);
    }

    private static void printDecomposition(int number) {
        List<List<Integer>> result = new ArrayList<>();
        decompose(number, result);
        for (List<Integer> integers : result) {
            for (Integer integer : integers) {
                System.out.print(integer + " ");
            }
            System.out.println("");
        }
    }

    private static void decompose(int number, List<List<Integer>> decompositions) {
        if (number == 1) {
            return "1";
        }
        return number - 1 + decompose();
    }
}