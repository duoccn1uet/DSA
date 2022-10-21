import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by spec on 20.04.2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int size;

    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // return the number of items on the randomized queue
    public int size() {
        return size;
    }
    
    private void resize(int newSize) {
        Item[] temp = (Item[]) new Object[newSize];
        if (size >= 0)
            System.arraycopy(a, 0, temp, 0, size);
        a = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) 
            throw new IllegalArgumentException("null element added in enqueue function");
        if (size == a.length) 
            resize(2 * a.length);
        if (size == 0) {
            a[size++] = item;
            return;
        }
        int randomIndex = StdRandom.uniformInt(size);
        Item temp = a[randomIndex];
        a[randomIndex] = item;
        a[size++] = temp;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("no element to deque");
        if (size == a.length / 4)
            resize(a.length / 2);
        int randomIndex = StdRandom.uniformInt(size);
        Item item = a[randomIndex];
        a[randomIndex] = a[--size];
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("In function sample: Queue is empty");
        return a[StdRandom.uniformInt(size)];
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new QueueListIterator();
    }

    private class QueueListIterator implements Iterator<Item> {
        private int iter = 0;
        private int[] randomIndices = new int[size];
        public QueueListIterator() {
            for (int j = 0; j < size; j++) {
                randomIndices[j] = j;
            }
            StdRandom.shuffle(randomIndices);
        }

        @Override
        public boolean hasNext() {
            return iter < size;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("Queue does not have next element");
            return a[randomIndices[iter++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("method remove is unsupported in Iterator");
        }
    }

    // Unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 1; i <= 5; i++) {
            queue.enqueue(i);
        }
        System.out.println(queue.size());
        for (Integer i : queue) {
            System.out.print(i + " ");
        }
        queue.dequeue();
        System.out.println();
        for (Integer i : queue) {
            System.out.print(i + " ");
        }
    }
}