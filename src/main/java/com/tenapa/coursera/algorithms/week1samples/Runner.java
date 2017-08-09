package com.tenapa.coursera.algorithms.week1samples;

import com.tenapa.coursera.algorithms.utils.StdIn;
import com.tenapa.coursera.algorithms.utils.StdOut;

/**
 * @author Tetiana_Prynda
 * Created on 8/2/2017.
 */
public class Runner {
    public static void main(String[] args) {
        int N = StdIn.readInt();
        UF uf = new QuickFindUF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                StdOut.println(p + " " + q);
            }
        }
    }
}
