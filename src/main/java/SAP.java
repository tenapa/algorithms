import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Tetiana_Prynda
 * Created on 10/27/2017.
 */
public class SAP {
    private final Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("Input can not be null");
        graph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        checkBounds(v);
        checkBounds(w);

        BreadthFirstDirectedPaths ancestorsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths ancestorsW = new BreadthFirstDirectedPaths(graph, w);

        final int ancestor = getFirstCommonAncestor(v, ancestorsV, w, ancestorsW);
        if (ancestor == -1) return -1;
        return ancestorsV.distTo(ancestor) + ancestorsW.distTo(ancestor);

    }

    private int getFirstCommonAncestor(int v, BreadthFirstDirectedPaths ancestorsV, int w, BreadthFirstDirectedPaths ancestorsW) {
        AncestorNode ancestor = new AncestorNode(-1, Integer.MAX_VALUE);

        ancestor = getAncestorNode(ancestorsV, w, ancestor);
        ancestor = getAncestorNode(ancestorsW, v, ancestor);

        Queue<AncestorNode> queue = new Queue<>();
        queue.enqueue(new AncestorNode(v, 0));

        Collection<Integer> used = new ArrayList<>();
        used.add(v);

        while (!queue.isEmpty()) {
            AncestorNode c = queue.dequeue();
            for (int cc : graph.adj(c.getId())) {
                if (!used.contains(cc)) {
                    if (ancestorsW.hasPathTo(cc)) {
                        final int dist = ancestorsW.distTo(cc) + c.getDist();
                        if (dist < ancestor.getDist()) {
                            ancestor = new AncestorNode(cc, dist);
                        }
                    }
                    used.add(cc);
                    queue.enqueue(new AncestorNode(cc, c.getDist() + 1));
                }
            }
        }

        used.clear();
        queue.enqueue(new AncestorNode(w, 0));
        while (!queue.isEmpty()) {
            AncestorNode c = queue.dequeue();
            for (int cc : graph.adj(c.getId())) {
                if (!used.contains(cc)) {
                    if (ancestorsV.hasPathTo(cc)) {
                        final int dist = ancestorsV.distTo(cc) + c.getDist();
                        if (dist < ancestor.getDist()) {
                            ancestor = new AncestorNode(cc, dist);
                        }
                    }
                    used.add(cc);
                    queue.enqueue(new AncestorNode(cc, c.getDist() + 1));
                }
            }
        }

        return ancestor.getId();

    }

    private AncestorNode getAncestorNode(BreadthFirstDirectedPaths from, int to, AncestorNode current) {
        if (from.hasPathTo(to)) {
            final int dist = from.distTo(to);
            if (dist < current.getDist()) {
                return new AncestorNode(to, dist);

            }
        }
        return current;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        checkBounds(v);
        checkBounds(w);

        BreadthFirstDirectedPaths ancestorsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths ancestorsW = new BreadthFirstDirectedPaths(graph, w);

        return getFirstCommonAncestor(v, ancestorsV, w, ancestorsW);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("Input can not be null");
        checkBounds(v);
        checkBounds(w);
        BreadthFirstDirectedPaths ancestorsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths ancestorsW = new BreadthFirstDirectedPaths(graph, w);

        final int commonAncestor = getFirstCommonAncestor(v, w, ancestorsV, ancestorsW);
        return commonAncestor == -1 ? -1 : ancestorsV.distTo(commonAncestor) + ancestorsW.distTo(commonAncestor);
    }
    private void checkBounds(Iterable<Integer> values) {
        for (int v : values) {
            checkBounds(v);
        }
    }

    private void checkBounds(int v) {
        if (v < 0 || v >= graph.V())
            throw new IndexOutOfBoundsException("Input must be withing bounds");
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("Input can not be null");
        checkBounds(v);
        checkBounds(w);
        BreadthFirstDirectedPaths ancestorsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths ancestorsW = new BreadthFirstDirectedPaths(graph, w);

        return getFirstCommonAncestor(v, w, ancestorsV, ancestorsW);
    }

    private int getFirstCommonAncestor(Iterable<Integer> v, Iterable<Integer> w, BreadthFirstDirectedPaths ancestorsV, BreadthFirstDirectedPaths ancestorsW) {
        int ancestor = -1;
        int minLen = Integer.MAX_VALUE;
        for (int vv : v) {
            for (int ww : w) {
                final int commonAncestor = getFirstCommonAncestor(vv, ancestorsV, ww, ancestorsW);
                if (commonAncestor == -1) continue;
                final int dist = ancestorsV.distTo(commonAncestor) + ancestorsW.distTo(commonAncestor);
                if (dist < minLen) {
                    minLen = dist;
                    ancestor = commonAncestor;
                }
            }
        }
        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
//        intentionally empty
    }

    private class AncestorNode {
        private final int id;
        private final int dist;

        public AncestorNode(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }

        public int getId() {
            return id;
        }

        public int getDist() {
            return dist;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            AncestorNode that = (AncestorNode) obj;
            return id == that.id &&
                    dist == that.dist;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, dist);
        }
    }
}
