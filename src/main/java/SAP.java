import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.Iterator;

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

        BreadthFirstDirectedPaths ancestorsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths ancestorsW = new BreadthFirstDirectedPaths(graph, w);

        final int ancestor = getFirstCommonAncestor(v, ancestorsV, w, ancestorsW);
        if(ancestor == -1) return -1;
        return ancestorsV.distTo(ancestor)+ancestorsW.distTo(ancestor);

    }

    private int getFirstCommonAncestor(int v, BreadthFirstDirectedPaths ancestorsV, int w, BreadthFirstDirectedPaths ancestorsW){
        int minPath = Integer.MAX_VALUE;
        int ancestor = -1;
        if (ancestorsV.hasPathTo(w)) {
            final int dist = ancestorsV.distTo(w);
            minPath = dist < minPath ? dist : minPath;
            ancestor = w;
        }

        Iterator<Integer> iterator = graph.adj(v).iterator();
        int dist = 0;
        boolean hasParent = iterator.hasNext();
        while (hasParent) {
            dist++;
            final Integer parentV = iterator.next();
            if(ancestorsW.hasPathTo(parentV)){
                final int distToW = ancestorsW.distTo(parentV) + dist;
                if(distToW < minPath){
                    minPath =  distToW;
                    ancestor = parentV;
                }
            }
            if(!iterator.hasNext()){
                iterator = graph.adj(parentV).iterator();
                hasParent = iterator.hasNext();
            }
        }

        return ancestor;

    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || v > graph.V() || w < 0 || w > graph.V())
            throw new IllegalArgumentException("Input must be withing bounds");

        BreadthFirstDirectedPaths ancestorsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths ancestorsW = new BreadthFirstDirectedPaths(graph, w);

        return getFirstCommonAncestor(v, ancestorsV, w, ancestorsW);
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
