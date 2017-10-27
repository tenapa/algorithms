import edu.princeton.cs.algs4.Digraph;

/**
 * @author Tetiana_Prynda
 * Created on 10/27/2017.
 */
public class SAP {
    private final Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("Input can not be null");
        graph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || v > graph.V() || w < 0 || w > graph.V())
            throw new IllegalArgumentException("Input must be withing bounds");

        final Iterable<Integer> adjV = getPathToRoot(v);
        final Iterable<Integer> adjW = graph.adj(w);
        return 0;
    }

    private Iterable<Integer> getPathToRoot(int v) {
        return null;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || v > graph.V() || w < 0 || w > graph.V())
            throw new IllegalArgumentException("Input must be withing bounds");

        return 0;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("Input can not be null");
        return 0;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("Input can not be null");
        return 0;
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
