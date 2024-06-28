import java.util.Iterator;

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
        Node target = new Node(item, beginHelper, beginHelper.next);
        beginHelper.next.last = target;
        beginHelper.next = target;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        Node target = new Node(item, endHelper.last, endHelper);
        endHelper.last.next = target;
        endHelper.last = target;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        Item result = beginHelper.next.content;
        beginHelper.next = beginHelper.next.next;
        beginHelper.next.last = beginHelper;
        return result;
    }

    // remove and return the item from the back
    public Item removeLast() {
        Item result = endHelper.last.content;
        endHelper.last = endHelper.last.last;
        endHelper.last.next = endHelper;
        return result;
    }

    private class DequeIterator implements Iterator<Item>{
        private Node current = beginHelper.next;
        public boolean hasNext(){
            return current.next != null;
        }

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()

    // unit testing (required)
    public static void main(String[] args)

}
