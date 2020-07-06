import java.util.Scanner;

public class Main {
    private static class TableEntry<T> {
        private final int key;
        private final T value;
        private boolean removed;

        public TableEntry(int key, T value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }

        public void remove() {
            removed = true;
        }

        public boolean isRemoved() {
            return removed;
        }
    }

    private static class HashTable<T> {
        private int size;
        private TableEntry[] table;

        public HashTable(int size) {
            this.size = size;
            table = new TableEntry[size];
        }

        public boolean put(int key, T value) {
            // put your code here
            int index = findKey(key);
            if (index == -1) {
                return false;
            }
            table[index] = new TableEntry(key, value);
            return true;
        }

        public T get(int key) {
            // put your code here
            int index = findKey(key);
            if (index == -1 || table[index] == null) {
                return null;
            }
            return (T) table[index].getValue();
        }

        public void remove(int key) {
            // put your code here
            int index = findKey(key);
            if (index != -1) {
                table[index] = null;
            }
        }

        private int findKey(int key) {
            // put your code here
            int hash = key % size;

            while (!(table[hash] == null || table[hash].getKey() == key)) {
                hash = (hash + 1) % size;
                if (hash == key % size) {
                    return -1;
                }
            }
            return hash;
        }
    }

    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int size = Integer.parseInt(scanner.nextLine());

        HashTable<String> hashTable = new HashTable<>(size);
        for (int i = 0; i < size; i++) {
            String[] query = scanner.nextLine().split(" ");
            int key = Integer.parseInt(query[1]);
            switch (query[0]) {
                case "put":
                    String value = query[2];
                    hashTable.put(key, value);
                    break;
                case "get":
                    String phone = hashTable.get(key);
                    System.out.println(phone == null ? -1 : phone);
                    break;
                case "remove":
                    hashTable.remove(key);
                    break;
                default:
                    System.out.println("Error: command '" + query[0] + "' not found");
                    break;
            }
        }
    }
}