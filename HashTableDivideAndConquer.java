//Reference: Algorithms by Sedgewick and wayne 4th Edition

public class HashTableDivideAndConquer {
    private String[] table;
    private int capacity;
    private int size;

    // Constructor to initialize the hash table with a given capacity
    public HashTableDivideAndConquer(int capacity) {
        this.capacity = capacity;
        this.table = new String[capacity];
        this.size = 0;
    }

    // Simple hash function to map a string key to an index
    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    // Insert a key into the hash table
    public void insert(String key) {
        if ((size + 1.0) / capacity > 0.75) {
            resize();
        }

        int index = hash(key);

        while (table[index] != null) {
            if (table[index].equals(key)) {
                return;
            }
            index = (index + 1) % capacity;
        }

        table[index] = key;
        size++;
    }

    // Search for a key in the hash table
    public boolean search(String key) {
        int index = hash(key);
        while (table[index] != null) {
            if (table[index].equals(key)) {
                return true;
            }
            index = (index + 1) % capacity;
        }
        return false;
    }

    // Delete a key from the hash table
    public void delete(String key) {
        int index = hash(key);
        while (table[index] != null) {
            if (table[index].equals(key)) {
                table[index] = null;
                size--;
                rehash();
                return;
            }
            index = (index + 1) % capacity;
        }
    }

    // Divide and Conquer Resize: Rehash the table by dividing the task into smaller
    // chunks
    private void resize() {
        String[] oldTable = table;
        int oldCapacity = capacity;
        capacity *= 2;
        table = new String[capacity];
        size = 0;

        resize(oldTable, 0, oldCapacity - 1);
    }

    private void resize(String[] oldTable, int firstIndex, int lastIndex) {
        if (firstIndex > lastIndex)
            return;

        if (firstIndex == lastIndex) {
            if (oldTable[firstIndex] != null) {
                insert(oldTable[firstIndex]);
            }
            return;
        }
        // Split and resize both halves
        int middle = (firstIndex + lastIndex) / 2;
        resize(oldTable, firstIndex, middle);
        resize(oldTable, middle + 1, lastIndex);
    }

    // Rehash remaining elements to fill gaps after a deletion (Divide and Conquer
    // Approach)
    private void rehash() {
        String[] oldTable = table;
        int oldCapacity = capacity;
        table = new String[capacity];
        size = 0;

        rehash(oldTable, 0, oldCapacity - 1);
    }

    private void rehash(String[] oldTable, int firstIndex, int lastIndex) {
        if (firstIndex > lastIndex)
            return;

        if (firstIndex == lastIndex) {
            if (oldTable[firstIndex] != null) {
                insert(oldTable[firstIndex]);
            }
            return;
        }
        int middle = (firstIndex + lastIndex) / 2;
        rehash(oldTable, firstIndex, middle);
        rehash(oldTable, middle + 1, lastIndex);
    }

    // Print the hash table for debugging
    public void printTable() {
        System.out.println("Capacity " + capacity + " Size " + size + " Load Factor: " + (size * 1.0) / capacity);
        for (int i = 0; i < capacity; i++) {
            System.out.println(i + " : " + table[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        HashTableDivideAndConquer unihashTable = new HashTableDivideAndConquer(20);
        // Insert keys
        unihashTable.insert("ATU Letterkenny");
        unihashTable.insert("ATU Killybegs");
        unihashTable.insert("ATU Sligo");
        unihashTable.insert("ATU Galway Mayo");
        unihashTable.insert("Test University");
        unihashTable.insert("Catherine Whiteside Institute of Technology");
        unihashTable.insert("DCU");
        unihashTable.insert("UCD");
        unihashTable.insert("UCC");
        unihashTable.insert("Maynooth University");
        unihashTable.insert("North West");
        unihashTable.insert("RCSI");
        unihashTable.insert("Ulster University Derry");
        unihashTable.insert("Ulster University Belfast");
        unihashTable.insert("Trinity College Dublin");
        unihashTable.insert("TU Dublin");
        unihashTable.insert("University of Limerick");
        unihashTable.insert("TUS");
        unihashTable.insert("University of Galway");
        unihashTable.insert("MTU");

        // Print the hash table
        unihashTable.printTable();
        // Search for a key
        System.out.println("Is 'ATU Sligo' in the table? " + unihashTable.search("ATU Sligo")); // true
        System.out.println("Is 'ATU Dundalk' in the table? " + unihashTable.search("ATU Dundalk")); // false
        // Delete a key
        unihashTable.delete("ATU Galway Mayo");
        System.out.println("Is 'ATU Galway Mayo' in the table? " + unihashTable.search("ATU Galway Mayo")); // false
        unihashTable.printTable();
    }
}