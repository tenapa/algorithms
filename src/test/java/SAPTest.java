import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Tetiana_Prynda
 * Created on 10/27/2017.
 */
public class SAPTest {

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

}