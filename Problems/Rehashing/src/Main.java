import java.util.Scanner;

public class Main {
    private static class TableEntry<T> {
        private final int key;
        private final T value;

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

        public T get(int key) {
            int idx = findKey(key);

            if (idx == -1 || table[idx] == null) {
                return null;
            }

            return (T) table[idx].getValue();          
        }

        private int findKey(int key) {
            int hash = key % size;

            while (!(table[hash] == null || table[hash].getKey() == key)) {
                hash = (hash + 1) % size;

                if (hash == key % size) {
                    return -1;
                }
            }

            return hash;
        }

        private void rehash() {
            // put your code here
            TableEntry[] oldTable = table.clone();
            size *= 2;
            table = new TableEntry[size];

            for (TableEntry entry : oldTable) {
                if (entry != null) {
                    put(entry.getKey(), (T) entry.getValue());
                }
            }
//            System.out.println("rehash. old: " + oldTable.length + " new: " + table.length);
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

    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int size = Integer.parseInt(scanner.nextLine());

        HashTable<String> hashTable = new HashTable<>(5);
        for (int i = 0; i < size; i++) {
            String[] entry = scanner.nextLine().split(" ");
            int key = Integer.parseInt(entry[0]);
            String value = entry[1];
            hashTable.put(key, value);
//            System.out.println(hashTable.toString());
        }
        System.out.println(hashTable.toString());
    }
}