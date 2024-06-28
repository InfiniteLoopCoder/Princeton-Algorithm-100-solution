import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item content;
        private Node next;
        private Node last;

        Node(Item c, Node l, Node n) {
            content = c;
            next = n;
            last = l;
        }
    }

    private Node beginHelper;
    private Node endHelper;
    private int size;


    // construct an empty deque
    public Deque() {
        beginHelper = new Node(null, null, null);
        endHelper = new Node(null, beginHelper, null);
        beginHelper.next = endHelper;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node target = new Node(item, beginHelper, beginHelper.next);
        beginHelper.next.last = target;
        beginHelper.next = target;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node target = new Node(item, endHelper.last, endHelper);
        endHelper.last.next = target;
        endHelper.last = target;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item result = beginHelper.next.content;
        beginHelper.next = beginHelper.next.next;
        beginHelper.next.last = beginHelper;
        size--;
        return result;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item result = endHelper.last.content;
        endHelper.last = endHelper.last.last;
        endHelper.last.next = endHelper;
        size--;
        return result;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = beginHelper.next;

        public boolean hasNext() {
            return current.next != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.content;
            current = current.next;
            return item;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

        // Test isEmpty() on an empty deque
        System.out.println("Testing isEmpty() on an empty deque...");
        if (deque.isEmpty()) {
            System.out.println("isEmpty() test passed.");
        } else {
            System.out.println("isEmpty() test failed.");
        }

        // Test addFirst() method
        System.out.println("Testing addFirst() method...");
        deque.addFirst(1);
        if (!deque.isEmpty() && deque.size() == 1 && deque.iterator().next() == 1) {
            System.out.println("addFirst() test passed.");
        } else {
            System.out.println("addFirst() test failed.");
        }

        // Test addLast() method
        System.out.println("Testing addLast() method...");
        deque.addLast(2);
        Iterator<Integer> iter = deque.iterator();
        if (deque.size() == 2 && iter.next() == 1 && iter.next() == 2) {
            System.out.println("addLast() test passed.");
        } else {
            System.out.println("addLast() test failed.");
        }

        // Test removeFirst() method
        System.out.println("Testing removeFirst() method...");
        int removedFirst = deque.removeFirst();
        if (removedFirst == 1 && deque.size() == 1 && deque.iterator().next() == 2) {
            System.out.println("removeFirst() test passed.");
        } else {
            System.out.println("removeFirst() test failed.");
        }

        // Test removeLast() method
        System.out.println("Testing removeLast() method...");
        int removedLast = deque.removeLast();
        if (removedLast == 2 && deque.isEmpty() && deque.size() == 0) {
            System.out.println("removeLast() test passed.");
        } else {
            System.out.println("removeLast() test failed.");
        }

        // Test iterator() method
        System.out.println("Testing iterator() method...");
        deque.addFirst(3);
        deque.addLast(4);

        boolean iteratorTestPassed = true;
        Iterator<Integer> iterator = deque.iterator();
        if (iterator.hasNext() && iterator.next() == 3 && iterator.hasNext() && iterator.next() == 4 && !iterator.hasNext()) {
            System.out.println("iterator() test passed.");
        } else {
            System.out.println("iterator() test failed.");
        }
    }
}

