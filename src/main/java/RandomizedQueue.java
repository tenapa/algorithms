import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Tetiana_Prynda
 *         Created on 8/17/2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] content;
    //    private int[] links;
    private int size;
    private int nextItem;

    // construct an empty randomized queue
    public RandomizedQueue() {
        content = (Item[]) new Object[2];
//        links = new int[2];

    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
//        rq.enqueue(5);
        for (int i = 0; i < 10; i++) {
            rq.enqueue(i);
        }
        System.out.println(Arrays.toString(rq.content));
        System.out.println("Removing");
        for (int i = 0; i < 5; i++) {
            System.out.println(rq.dequeue());
            System.out.println(Arrays.toString(rq.content));
        }
        System.out.println("Stays");
        for (Integer integer : rq) {
            System.out.println(integer);
        }
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
        if (item == null) throw new IllegalArgumentException("Cannot insert Null value");
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
        if (size == 0) throw new NoSuchElementException("Cannot Dequeue from Empty Queue");
        checkSize();
        int index = StdRandom.uniform(nextItem);
        Item item = content[index];
        content[index] = content[--nextItem];
        content[nextItem] = null;
        size--;
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (size == 0) throw new NoSuchElementException("Cannot Sample from Empty Queue");
        int index = StdRandom.uniform(nextItem);
        return content[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private int items = size;
            private int [] links = new int[size];
            {
                for (int i = 0; i < size; i++) {
                    links[i] = i;
                }
            }

            public boolean hasNext() {
                return items > 0;
            }

            public Item next() {
                if (items == 0) throw new NoSuchElementException("No more Items in Queue");
                int itemIndex = StdRandom.uniform(items);
                Item item = content[links[itemIndex]];
                links[itemIndex] = links[--items];
                return item;
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
