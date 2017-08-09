package com.tenapa.coursera.algorithms.week1samples;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Tetiana_Prynda
 * Created on 8/2/2017.
 */
public class QuickFindUF implements UF {
    private int[] items;

    /**
     * initialize union-find data structure with N objects (0 to N – 1)
     *
     * @param size
     */
    public QuickFindUF(int size) {
        items = new int[size];
        for (int i = 0; i < size; i++) {
            items[i] = i;
        }
    }

    public void union(int p, int q) {
        final int itemP = items[p];
        final int itemQ = items[q];
        if (itemP != itemQ) {
            for (int i = 0; i < items.length; i++) {
                if (items[i] == itemP) items[i] = itemQ;
            }
        }
    }

    public boolean connected(int p, int q) {
        return items[p] == items[q];
    }

    public int find(int p) {
        return items[p];
    }

    public int count() {
        Set<Integer> components = new HashSet<Integer>(items.length);
        for (int item : items) {
            components.add(item);
        }
        return components.size();
    }
}
