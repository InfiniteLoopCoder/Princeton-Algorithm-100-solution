import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITIAL_CAPACITY = 4;
    private Item[] items;
    private int size;
    private int capacity;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[INITIAL_CAPACITY];
        capacity = INITIAL_CAPACITY;
        size = 0;
    }

    private void capacityManagement() {
        if (capacity > 4 && size < capacity / 4) {
            shrink();
        }
        if (size == capacity) {
            extend();
        }
    }

    private void shrink() {
        Item[] newItems = (Item[]) new Object[capacity / 2];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[i];
        }
        capacity = capacity / 2;
        items = newItems;
    }

    private void extend() {
        Item[] newItems = (Item[]) new Object[2 * capacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[i];
        }
        capacity = capacity * 2;
        items = newItems;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        capacityManagement();
        items[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniformInt(size); // Generate random index in the range [0, size)
        Item toDelete = items[index];
        // Move the last element to the position of the element to be removed
        items[index] = items[size - 1];
        items[size - 1] = null; // Clear the reference for garbage collection
        size--;
        capacityManagement();
        return toDelete;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniformInt(size)];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] indexes;
        private int current;

        RandomizedQueueIterator() {
            indexes = new int[size];
            for (int i = 0; i < size; i++) {
                indexes[i] = i;
            }
            StdRandom.shuffle(indexes);
            current = 0;
        }

        public boolean hasNext() {
            return current < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[indexes[current++]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        // Test enqueue
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        rq.enqueue(7);
        rq.enqueue(8);
        rq.enqueue(9);
        rq.enqueue(10);
        rq.enqueue(11);
        rq.enqueue(12);
        rq.enqueue(13);
        rq.enqueue(14);
        rq.enqueue(15);
        rq.enqueue(16);
        rq.enqueue(17);
        rq.enqueue(18);
        rq.enqueue(19);
        rq.enqueue(20);
        rq.enqueue(21);
        rq.enqueue(22);
        rq.enqueue(23);
        rq.enqueue(24);


        // Test size and isEmpty
        System.out.println("Size: " + rq.size()); // Expected output: 4
        System.out.println("Is empty? " + rq.isEmpty()); // Expected output: false

        // Test sample
        System.out.println("Sample: " + rq.sample()); // Expected output: Random element from {10, 20, 30, 40}

        // Test dequeue
        System.out.println("Dequeue: " + rq.dequeue()); // Expected output: Randomly dequeued element

        // Test iterator
        System.out.println("Iterating over RandomizedQueue:");
        Iterator<Integer> iter = rq.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }


}
