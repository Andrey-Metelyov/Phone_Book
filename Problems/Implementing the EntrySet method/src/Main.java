import java.util.*;

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
        private Map<Integer, List<T>> set;

        public HashTable(int size) {
            this.size = size;
            table = new TableEntry[size];
            set = new HashMap<>();
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
            if (set.containsKey(key)) {
                set.get(key).add(value);
            } else {
                set.put(key, new ArrayList<T>());
                set.get(key).add(value);
            }
            return true;
        }

        public T get(int key) {
            // put your code here          
            int idx = findKey(key);

            if (idx == -1 || table[idx] == null) {
                return null;
            }

            return (T) table[idx].getValue();
        }

        public Map<Integer, List<T>> entrySet() {
            // put your code here
            return set;
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
        }
        Map<Integer, List<String>> map = hashTable.entrySet();
        for (Map.Entry<Integer, List<String>> integerListEntry : map.entrySet()) {
            System.out.print(integerListEntry.getKey() + ":");
            for (String s : integerListEntry.getValue()) {
                System.out.print(" " + s);
            }
            System.out.println("");
        }
//        System.out.println(hashTable.entrySet().toString());
//        System.out.println();

    }
}