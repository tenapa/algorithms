import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author Tetiana_Prynda
 * Created on 8/17/2017.
 */
public class Permutation {
    public static void main(String[] args) {
        final int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            final String string = StdIn.readString();
            queue.enqueue(string);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }

    }
}
