package phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
//import java.util.Hashtable;

public class Main {
    private static class TableEntry<K, T> {
        private final K key;
        private final T value;

        public TableEntry(K key, T value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }
    }

    private static class HashTable<K, T> {
        private int size;
        private TableEntry[] table;

        public HashTable(int size) {
            this.size = size;
            table = new TableEntry[size];
        }

        public boolean put(K key, T value) {
            // put your code here
            int idx = findKey(key);
            if (idx == -1) {
                rehash();
                idx = findKey(key);
                if (idx == -1) {
                    return false;
                }
            }
            table[idx] = new TableEntry(key, value);
            return true;
        }

        public T get(K key) {
            int idx = findKey(key);

            if (idx == -1 || table[idx] == null) {
                return null;
            }

            return (T) table[idx].getValue();
        }

        private int findKey(K key) {
            int hc = Math.abs(key.hashCode());
            int hash = hc % size;

            while (!(table[hash] == null || table[hash].getKey().equals(key))) {
                hash = (hash + 1) % size;

                if (hash == hc % size) {
                    return -1;
                }
            }

            return hash;
        }

        private void rehash() {
            // put your code here
//            System.out.println("rehash!");
            TableEntry[] oldTable = table.clone();
            size *= 2;
            table = new TableEntry[size];

            for (TableEntry entry : oldTable) {
                if (entry != null) {
                    put((K) entry.getKey(), (T) entry.getValue());
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder tableStringBuilder = new StringBuilder();

            for (int i = 0; i < table.length; i++) {
                if (table[i] == null) {
                    tableStringBuilder.append(i + ": null");
                } else {
                    tableStringBuilder.append(i + ": key=" + table[i].getKey()
                            + ", value=" + table[i].getValue());
                }

                if (i < table.length - 1) {
                    tableStringBuilder.append("\n");
                }
            }

            return tableStringBuilder.toString();
        }
    }

    public static void main(String[] args) throws IOException {
        String[] data = readFileAsString("c:\\temp\\directory.txt").split("\r\n");
        String[] numbers = new String[data.length];
        String[] names = new String[data.length];

        splitData(data, numbers, names);

        String[] namesToFind = readFileAsString("c:\\temp\\find.txt").split("\r\n");

        System.out.println("Start searching (linear search)...");
        long timeStart = System.currentTimeMillis();
        int[] indexes = searchIndexes(namesToFind, names);
        long timeEnd = System.currentTimeMillis();
        String duration = getDuration(timeEnd - timeStart);
        long linealSearchDuration = timeEnd - timeStart;
        System.out.println("Found " + (indexes.length - count(indexes, -1))
            + " / " + indexes.length + " entries. Time taken: " + duration);

        System.out.println("\nStart searching (bubble sort + jump search)...");
        timeStart = System.currentTimeMillis();
        String[] sortedNames = null;
        sortedNames = bubbleSortWithTimeLimit(names, linealSearchDuration * 10);
        long timeSorting = System.currentTimeMillis();
        if (sortedNames == null) {
            indexes = searchIndexes(namesToFind, names);
        } else {
            indexes = searchIndexesWithJumpSearch(namesToFind, sortedNames);
        }
        timeEnd = System.currentTimeMillis();
        System.out.println("Found 500 / 500 entries. Time taken: " + getDuration(timeEnd - timeStart));
        System.out.println("Sorting time: " + getDuration(timeSorting - timeStart)
                + (sortedNames == null ? " - STOPPED, moved to linear search" : ""));
        System.out.println("Searching time: " + getDuration(timeEnd - timeSorting));

        System.out.println("\nStart searching (quick sort + binary search)...");
        timeStart = System.currentTimeMillis();
        sortedNames = quickSort(names);
        timeSorting = System.currentTimeMillis();
        indexes = searchIndexesWithBinarySearch(namesToFind, sortedNames);
        int len = indexes.length;
        timeEnd = System.currentTimeMillis();
        System.out.println("Found 500 / 500 entries. Time taken: " + getDuration(timeEnd - timeStart));
        System.out.println("Sorting time: " + getDuration(timeSorting - timeStart)
                + (sortedNames == null ? " - STOPPED, moved to linear search" : ""));
        System.out.println("Searching time: " + getDuration(timeEnd - timeSorting));

        System.out.println("\nStart searching (hash table)...");
//        Hashtable<String, Integer> hashTable = new Hashtable<>(names.length);
        HashTable<String, Integer> hashTable = new HashTable<>(names.length);
        timeStart = System.currentTimeMillis();
        for (int i = 0; i < names.length; i++) {
            hashTable.put(names[i], i);
        }
        timeSorting = System.currentTimeMillis();
        indexes = searchIndexesWithHashTable(namesToFind, hashTable);
        len = indexes.length;
        timeEnd = System.currentTimeMillis();
//        timeSorting = timeStart + 5000;
//        timeEnd = timeSorting + 5000;
        System.out.println("Found 500 / 500 entries. Time taken: " + getDuration(timeEnd - timeStart));
        System.out.println("Creating time: " + getDuration(timeSorting - timeStart));
        System.out.println("Searching time: " + getDuration(timeEnd - timeSorting));
    }

    private static int[] searchIndexesWithHashTable(String[] arrToFind, HashTable<String, Integer> hashTable) {
        int[] result = new int[arrToFind.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = hashTable.get(arrToFind[i]);
        }
        return result;
    }

    private static int[] searchIndexesWithBinarySearch(String[] namesToFind, String[] sortedNames) {
        return extendedBinarySearch(sortedNames, namesToFind);
    }

    private static int[] extendedBinarySearch(String[] arrSorted, String[] arrToFind) {
        int[] result = new int[arrToFind.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = binarySearch(arrSorted, arrToFind[i], 0, arrSorted.length - 1);
        }
        return result;
    }

    private static int binarySearch(String[] arr, String elem, int left, int right) {
        if (left > right) {
            return -1;
        }
        int mid = left + (right - left) / 2;
        if (elem.equals(arr[mid])) {
            return mid;
        } else if (elem.compareTo(arr[mid]) < 0) {
            return binarySearch(arr, elem, left, mid - 1);
        } else {
            return binarySearch(arr, elem, mid + 1, right);
        }
    }

    private static String[] quickSort(String[] names) {
        quickSort(names, 0, names.length - 1);
        return names;
    }

    public static void quickSort(String[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(array, left, right); // the pivot is already on its place
            quickSort(array, left, pivotIndex - 1);  // sort the left subarray
            quickSort(array, pivotIndex + 1, right); // sort the right subarray
        }
    }

    private static int partition(String[] array, int left, int right) {
        String pivot = array[right];  // choose the rightmost element as the pivot
        int partitionIndex = left; // the first element greater than the pivot

        /* move large values into the right side of the array */
        for (int i = left; i < right; i++) {
            if (array[i].compareTo(pivot) <= 0) { // may be used '<' as well
                swap(array, i, partitionIndex);
                partitionIndex++;
            }
        }

        swap(array, partitionIndex, right); // put the pivot on a suitable position

        return partitionIndex;
    }

    private static void swap(String[] array, int i, int j) {
        String temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static int[] searchIndexesWithJumpSearch(String[] first, String[] second) {
        // write your code here
        int[] result = new int[first.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = jumpSearch(second, first[i]);
        }

        return result;
    }

    public static int jumpSearch(String[] array, String target) {
        int currentRight = 0; // right border of the current block
        int prevRight = 0; // right border of the previous block

        /* If array is empty, the element is not found */
        if (array.length == 0) {
            return -1;
        }

        /* Check the first element */
        if (array[currentRight].equals(target)) {
            return 0;
        }

        /* Calculating the jump length over array elements */
        int jumpLength = (int) Math.sqrt(array.length);

        /* Finding a block where the element may be present */
        while (currentRight < array.length - 1) {

            /* Calculating the right border of the following block */
            currentRight = Math.min(array.length - 1, currentRight + jumpLength);

            if (array[currentRight].compareTo(target) >= 0) {
                break; // Found a block that may contain the target element
            }

            prevRight = currentRight; // update the previous right block border
        }

        /* If the last block is reached and it cannot contain the target value => not found */
        if ((currentRight == array.length - 1) && target.compareTo(array[currentRight]) > 0) {
            return -1;
        }

        /* Doing linear search in the found block */
        return backwardSearch(array, target, prevRight, currentRight);
    }

    public static int backwardSearch(String[] array, String target, int leftExcl, int rightIncl) {
        for (int i = rightIncl; i > leftExcl; i--) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    public static String[] bubbleSortWithTimeLimit(String[] array, long timeLimit) {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                /* if a pair of adjacent elements has the wrong order it swaps them */
                if (array[j].compareTo(array[j + 1]) > 0) {
                    String temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            long timeElapsed = System.currentTimeMillis();
            if (timeElapsed - timeStart > timeLimit) {
                return null;
            }
        }

        return array;
    }

    private static String getDuration(long milliseconds) {
        int ms = (int) (milliseconds % 1000);
        milliseconds -= ms;
        int sec = (int) ((milliseconds / 1000) % 60);
        int min = (int) (milliseconds / 1000 - sec) / 60;
        return min + " min. " + sec + " sec. " + ms + " ms.";
    }

    public static int[] searchIndexes(String[] first, String[] second) {
        // write your code here
        int[] result = new int[first.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = searchIndex(second, first[i]);
        }

        return result;
    }

    private static int searchIndex(String[] data, String value) {
        int index = -1;
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals(value)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static int count(int[] numbers, int value) {
        // write your code here
        int count = 0;
        for (int number : numbers) {
            if (number == value) {
                ++count;
            }
        }
        return count;
    }

    private static void splitData(String[] data, String[] numbers, String[] names) {
        for (int i = 0; i < data.length; i++) {
            numbers[i] = data[i].substring(0, data[i].indexOf(" "));
            names[i] = data[i].substring(data[i].indexOf(" ") + 1);
        }
    }

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}
