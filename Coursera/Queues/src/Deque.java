import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node prev;

        Node() {
            next = null;
            prev = null;
        }
    }

    private int size = 0;
    private Node head = null;
    private Node tail = null;

    // construct an empty deque
    public Deque() {

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
        if (item == null)
            throw new IllegalArgumentException("null element added in addFirst function");
        Node oldHead = head;
        head = new Node();
        head.item = item;
        head.next = oldHead;
        if (tail == null)
            tail = head;
        else
            head.next.prev = head;
        ++size;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("null element added in addLast function");
        Node oldTail = tail;
        tail = new Node();
        tail.item = item;
        tail.prev = oldTail;
        if (head == null)
            head = tail;
        else
            tail.prev.next = tail;
        ++size;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Can not removeFirst because deque is empty");
        Item item = head.item;
        --size;
        if (isEmpty()) {
            tail = null;
            head = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Can not removeLast because deque is empty");
        Item item = tail.item;
        --size;
        if (isEmpty()) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        return item;
    }

    private class DequeListIterator implements Iterator<Item> {
        private Node current = head;

        @Override
        public void remove() {
            throw new UnsupportedOperationException("method remove is unsupported in Iterator");
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("have no next element");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new DequeListIterator();
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addLast(2);
        Iterator<Integer> it = deque.iterator();
        for (; it.hasNext(); )
            System.out.println(it.next());
    }
}
