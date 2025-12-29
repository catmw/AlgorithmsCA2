
//Reference: Mastering LRU Caching: From Implementation to
//Thread Safety by Parimal Kumar
import java.util.HashMap;

class LRUCache {
    // Node class for doubly linked list
    class Node {
        int key, value;
        Node prev, next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private HashMap<Integer, Node> cache;
    private int capacity, size;
    private Node head, tail;

    // Constructor
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.cache = new HashMap<>();
        this.head = null;
        this.tail = null;
    }

    // Move a node to the front (most recently used)
    private void moveToFront(Node node) {
        if (node == head)
            return;
        remove(node);
        addToFront(node);
    }

    // Add a node to the front of the list
    private void addToFront(Node node) {
        node.prev = null;
        node.next = head;
        if (head != null) {
            head.prev = node;
        }
        head = node;
        if (tail == null) {
            tail = node;
        }
    }

    // Remove a node from the list
    private void remove(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        node.prev = null;
        node.next = null;
    }

    // Get the value of a key if it exists in the cache
    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            return -1; // Key not found
        }
        moveToFront(node);
        return node.value;
    }

    // Insert a key value pair into the cache
    public void put(int key, int value) {
        Node node = cache.get(key);

        // Key already exists update and move to front
        if (node != null) {
            node.value = value;
            moveToFront(node);
            return;
        }

        // Cache full remove tail
        if (size == capacity) {
            cache.remove(tail.key);
            remove(tail);
            size--;
        }

        // Insert new node
        Node newNode = new Node(key, value);
        addToFront(newNode);
        cache.put(key, newNode);
        size++;
    }

    // Print the cache for debugging
    public void printCache() {
        System.out.print("Data == ");
        Node current = head;
        while (current != null) {
            System.out.print("[" + current.key + ": " + current.value + "] ");
            current = current.next;
        }
        System.out.println();
    }

    // Main method to test the LRU Cache implementation
    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(20); // Cache capacity of 20

        // Insert items
        lruCache.put(1, 102345);
        lruCache.put(2, 102342);
        lruCache.put(3, 102303);
        lruCache.put(4, 102378);
        lruCache.put(5, 102389);
        lruCache.put(6, 102456);
        lruCache.put(7, 102467);
        lruCache.put(8, 102234);
        lruCache.put(9, 102569);

        lruCache.put(10, 107655);
        lruCache.put(11, 103445);
        lruCache.put(12, 101866);
        lruCache.put(13, 102445);
        lruCache.put(14, 101898);
        lruCache.put(15, 101999);
        lruCache.put(16, 101376);
        lruCache.put(17, 101654);
        lruCache.put(18, 101765);
        lruCache.put(19, 101456);
        lruCache.put(20, 101345);
        lruCache.printCache();

        // Access key 2 (this will make key 2 the most recently used)
        System.out.println("Make key 2 most recently used:");
        System.out.println("Get 2: " + lruCache.get(2)); // Should return 2
        lruCache.printCache();

        // Insert a new key, which will evict key 1 (the LRU)
        System.out.println("Insert key 21 with value 105444, evict key 1");
        lruCache.put(21, 105444);
        lruCache.printCache();
        // Access key 3
        System.out.println("Make key 3 most recently used:");
        System.out.println("Get 3: " + lruCache.get(3));
        lruCache.printCache();

        System.out.println("Insert another new key, which will evict key 4 (the LRU)");
        lruCache.put(22, 103455);
        lruCache.printCache();
    }
}