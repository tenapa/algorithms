package com.tenapa.coursera.algorithms.week1samples;

import java.util.Arrays;

/**
 * @author Tetiana_Prynda
 * Created on 8/2/2017.
 */
public class QuickUnionUF implements UF {

    private int[] items;

    public QuickUnionUF(int size) {
        items = new int[size];
        for (int i = 0; i < size; i++) {
            items[i] = i;
        }
    }

    public static void main(String[] args){
        QuickUnionUF demo = new QuickUnionUF(10);
        demo.union(4,3);
        demo.union(3,8);
        demo.union(6,5);
        demo.union(9,4);
        demo.union(2,1);
        demo.union(5,0);
        demo.union(7,2);
        demo.union(6,1);
        demo.union(7,3);

    }

    public void union(int p, int q) {
        if(!connected(p, q)){
            final int rootP = root(p);
            items[rootP] = root(q);
        }
        System.out.println(Arrays.toString(items));
    }

    public boolean connected(int p, int q) {
        return root(p)==root(q);
    }

    public int find(int p) {
        return root(p);
    }

    private int root(int p){
        if(items[p]==p) return p;
        else return root(items[p]);

//        while (items[p]!=p){
//            p=items[p];
//        }
//        return p;
    }

    public int count() {
        return 0;
    }
}
