package com.tenapa.coursera.algorithms.week1samples;

/**
 * @author Tetiana_Prynda
 * Created on 8/2/2017.
 */
public interface UF {
    /**
     * initialize union-find data structure with N objects (0 to N – 1)
     *
     * @param size
     */

    /**
     * add connection between p and q
     *
     * @param p
     * @param q
     */
    void union(int p, int q);

    /**
     * are p and q in the same component?
     *
     * @param p
     * @param q
     * @return
     */
    boolean connected(int p, int q);

    /**
     * component identifier for p (0 to N – 1)
     *
     * @param p
     * @return
     */
    int find(int p);

    /**
     * number of components
     *
     * @return
     */
    int count();

}
