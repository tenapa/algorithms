import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Tetiana_Prynda
 * Created on 8/17/2017.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;

    private int size;

    // construct an empty deque
    public Deque() {
        Node<Item> firstNode = new Node<Item>(null, null, null);
        first = firstNode;
        last = firstNode;

    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the end

    // add the item to the front
    public void addFirst(Item item) {
        validateParam(item);
        if (first.value == null) {
            first.value = item;
        } else {
            Node<Item> newFirst = new Node<Item>(item, first, null);
            first.previous = newFirst;
            first = newFirst;
        }
        size++;
    }

    public void addLast(Item item) {
        validateParam(item);
        if (last.value == null) {
            last.value = item;
        } else {
            Node<Item> newLast = new Node<Item>(item, null, last);
            last.next = newLast;
            last = newLast;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first.value == null) throw new NoSuchElementException("No elements in Deque");
        Item firstValue = first.value;
        if (first.next == null) {
            first.value = null;
        } else {
            final Node<Item> next = first.next;
            next.previous = null;
            first = next;
        }
        size--;
        return firstValue;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (last.value == null) throw new NoSuchElementException("No elements in Deque");
        Item lastValue = last.value;
        if (last.previous == null) {
            last.value = null;
        } else {
            final Node<Item> previous = last.previous;
            previous.next = null;
            last = previous;
        }
        size--;
        return lastValue;

    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node<Item> current = first;

            public boolean hasNext() {
                return current != null && current.value != null;
            }

            public Item next() {
                if (current == null) throw new NoSuchElementException("No more elements in Deque");
                Item currentValue = current.value;
                current = current.next;
                return currentValue;
            }

            public void remove() {
                throw new UnsupportedOperationException("Remove Operation not supported for Deque");
            }
        };
    }

    private void validateParam(Item item) {
        if (item == null) throw new IllegalArgumentException("Value stored cannot be null");
    }

//    @Override
//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        final Iterator<Item> iterator = this.iterator();
//        builder.append("[");
//        while (iterator.hasNext()) {
//            builder.append(iterator.next()).append(", ");
//        }
//        builder.append("]");
//        return builder.toString();
//    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value, Node<T> next, Node<T> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }

        public T getValue() {
            return value;
        }

        public Node<T> getNext() {
            return next;
        }

        public Node<T> getPrevious() {
            return previous;
        }
    }

}