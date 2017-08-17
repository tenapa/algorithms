import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Tetiana_Prynda
 * Created on 8/17/2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] content;
    private int size;
    private int nextItem;

    // construct an empty randomized queue
    public RandomizedQueue() {
        content = (Item[]) new Object[2];

    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        for (int i = 0; i < 64; i++) {
            rq.enqueue(i);
        }

        for (int i = 0; i < 63; i++) {
            rq.dequeue();
        }

        System.out.println(rq.size());
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if(item == null) throw new IllegalArgumentException("Cannot insert Null value");
        checkSize();
        content[nextItem++] = item;
        size++;
    }

    private void checkSize() {
        if (nextItem == content.length) {
            resize(2 * content.length);
        } else if (size > 0 && size == content.length / 4) {
            resize(content.length / 2);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if(size == 0) throw new NoSuchElementException("Cannot Dequeue from Empty Queue");
        Item item = null;
        int index = 0;
        do {
            index = StdRandom.uniform(nextItem);
            item = content[index];
        }
        while (item == null);
        content[index] = null;
        size--;
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if(size == 0) throw new NoSuchElementException("Cannot Sample from Empty Queue");
        Item item = null;
        do {
            int index = StdRandom.uniform(size);
            item = content[index];
        }
        while (item == null);
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private int items = size;

            public boolean hasNext() {
                return items > 0;
            }

            public Item next() {
                if (items == 0) throw new NoSuchElementException("No more Items in Queue");
                final Item sample = sample();
                items--;
                return sample;
            }

            public void remove() {
                throw new UnsupportedOperationException("Remove operation not allowed on RandomizedQueue");
            }
        };
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int j = 0;
        for (int i = 0; i < nextItem; i++) {
            final Item currentItem = content[i];
            if (currentItem != null) {
                copy[j++] = currentItem;
            }
        }
        content = copy;
        nextItem = j;
    }

}
