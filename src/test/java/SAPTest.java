import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * @author Tetiana_Prynda
 * Created on 10/27/2017.
 */
public class SAPTest {

    @Test(expected = IndexOutOfBoundsException.class)
    public void sap_digraph1_IllegV_len() {
        Digraph G = new Digraph(new In("wordnet/digraph1.txt"));
        SAP sap = new SAP(G);
        final int v = -1;
        final int w = 0;
        sap.length(v, w);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void sap_digraph1_IllegV_anc() {
        Digraph G = new Digraph(new In("wordnet/digraph1.txt"));
        SAP sap = new SAP(G);
        final int v = -1;
        final int w = 0;
        sap.ancestor(v, w);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void sap_digraph1_IllegV_anc2() {
        Digraph G = new Digraph(new In("wordnet/digraph1.txt"));
        SAP sap = new SAP(G);
        final int v = 13;
        final int w = 0;
        sap.ancestor(v, w);
    }

    @Test
    public void sap_digraph1_3_11() {
        Digraph G = new Digraph(new In("wordnet/digraph1.txt"));
        SAP sap = new SAP(G);
        final int v = 3;
        final int w = 11;
        System.out.println(G);
        assertEquals(4, sap.length(v, w));
        assertEquals(1, sap.ancestor(v, w));
    }

    @Test
    public void sap_digraph1_9_12() {
        Digraph G = new Digraph(new In("wordnet/digraph1.txt"));
        SAP sap = new SAP(G);
        final int v = 9;
        final int w = 12;
        assertEquals(3, sap.length(v, w));
        assertEquals(5, sap.ancestor(v, w));
    }

    @Test
    public void sap_digraph1_7_2() {
        Digraph G = new Digraph(new In("wordnet/digraph1.txt"));
        SAP sap = new SAP(G);
        final int v = 7;
        final int w = 2;
        assertEquals(4, sap.length(v, w));
        assertEquals(0, sap.ancestor(v, w));
    }

    @Test
    public void sap_digraph1_1_6() {
        Digraph G = new Digraph(new In("wordnet/digraph1.txt"));
        SAP sap = new SAP(G);
        final int v = 1;
        final int w = 6;
        assertEquals(-1, sap.length(v, w));
        assertEquals(-1, sap.ancestor(v, w));
    }

    @Test
    public void sap_digraph1_3_8() {
        Digraph G = new Digraph(new In("wordnet/digraph1.txt"));
        SAP sap = new SAP(G);
        final int v = 3;
        final int w = 8;
        assertEquals(1, sap.length(v, w));
        assertEquals(3, sap.ancestor(v, w));
    }

    @Test
    public void sap_digraph2_3_8() {
        Digraph G = new Digraph(new In("wordnet/digraph2.txt"));
        SAP sap = new SAP(G);
        final int v = 4;
        final int w = 2;
        assertEquals(2, sap.length(v, w));
        assertEquals(4, sap.ancestor(v, w));
    }

    @Test
    public void sap_digraph3_2_5() {
        Digraph G = new Digraph(new In("wordnet/digraph3.txt"));
        SAP sap = new SAP(G);
        final int v = 2;
        final int w = 5;
        assertEquals(3, sap.length(v, w));
        assertEquals(5, sap.ancestor(v, w));
    }

    @Test
    public void sap_digraph3_13_11() {
        Digraph G = new Digraph(new In("wordnet/digraph3.txt"));
        SAP sap = new SAP(G);
        final int v = 13;
        final int w = 11;
        assertEquals(3, sap.length(v, w));
        assertEquals(11, sap.ancestor(v, w));
    }

    @Test
    public void sap_digraph3_13_10() {
        Digraph G = new Digraph(new In("wordnet/digraph3.txt"));
        SAP sap = new SAP(G);
        final int v = 13;
        final int w = 10;
        assertEquals(4, sap.length(v, w));
        assertEquals(11, sap.ancestor(v, w));
    }

    @Test
    public void sap_digraphWordnet1() {
        Digraph G = new Digraph(new In("wordnet/digraph-wordnet.txt"));
        SAP sap = new SAP(G);
        final int v = 65573;
        final int w = 61567;
        assertEquals(12, sap.length(v, w));
//        assertEquals(4, sap.ancestor(v, w));
    }

    @Test
    public void sap_digraphWordnet2() {
        Digraph G = new Digraph(new In("wordnet/digraph-wordnet.txt"));
        SAP sap = new SAP(G);
        final int v = 43471;
        final int w = 31781;
        assertEquals(16, sap.length(v, w));
//        assertEquals(4, sap.ancestor(v, w));
    }

    /*
    11 vertices, 11 edges
    0: 10 1
    1: 2
    2: 3
    3:
    4: 2
    5: 4
    6: 7 5
    7:
    8: 7
    9: 8
    10: 9
     */
    @Test
    public void sap_digraph_ambiguous_ancestor_0_5() {
        Digraph G = new Digraph(new In("wordnet/digraph-ambiguous-ancestor.txt"));
        SAP sap = new SAP(G);
        final int v = 0;
        final int w = 5;
        assertEquals(4, sap.length(v, w));
        assertEquals(2, sap.ancestor(v, w));
    }

    @Test
    public void sap_digraph_ambiguous_ancestor_0_7() {
        Digraph G = new Digraph(new In("wordnet/digraph-ambiguous-ancestor.txt"));
        SAP sap = new SAP(G);
        final int v = 0;
        final int w = 7;
        assertEquals(4, sap.length(v, w));
        assertEquals(7, sap.ancestor(v, w));
    }

    @Test
    public void sap_digraph_ambiguous_ancestor_1_6() {
        Digraph G = new Digraph(new In("wordnet/digraph-ambiguous-ancestor.txt"));
        SAP sap = new SAP(G);
        final int v = 1;
        final int w = 6;
        assertEquals(4, sap.length(v, w));
        assertEquals(2, sap.ancestor(v, w));
    }

    @Test
    public void sap_digraph_ambiguous_ancestor_10_5() {
        Digraph G = new Digraph(new In("wordnet/digraph-ambiguous-ancestor.txt"));
        SAP sap = new SAP(G);
        final int v = 10;
        final int w = 5;
        assertEquals(-1, sap.length(v, w));
        assertEquals(-1, sap.ancestor(v, w));
    }

    //@Test
    public void sap_digraph_ambiguous_ancestor_10_1_to_5() {
        Digraph G = new Digraph(new In("wordnet/digraph-ambiguous-ancestor.txt"));
        SAP sap = new SAP(G);
        final int v1 = 10;
        final int v2 = 1;
        final int w = 5;
        assertEquals(3, sap.length(Arrays.asList(v1, v2), Collections.singletonList(w)));
        assertEquals(2, sap.ancestor(Arrays.asList(v1, v2), Collections.singletonList(w)));
    }

}