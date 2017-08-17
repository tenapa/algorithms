/**
 * Created by tetianaprynda on 09.08.17.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final int top;
    private final int bottom;
    private final int[][] map;
    private final WeightedQuickUnionUF uf;
    private int count;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Size should be greater then Zero");
        uf = new WeightedQuickUnionUF(n * n + 2);
        top = 0;
        bottom = n * n + 1;
        for (int i = 1; i <= n; i++) {
            uf.union(top, i);
        }
        for (int i = n * (n - 1) + 1; i <= n * n; i++) {
            uf.union(bottom, i);
        }
        this.n = n;
        this.map = new int[n][n];
        this.count = 0;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkArguments(row, col);
        if (isOpen(row, col)) return;
        map[row - 1][col - 1] = 1;
        if (row > 1 && isOpen(row - 1, col))
            uf.union(calcNode(row - 1, col), calcNode(row, col));
        if (row <= n - 1 && isOpen(row + 1, col))
            uf.union(calcNode((row + 1), col), calcNode(row, col));
        if (col > 1 && isOpen(row, col - 1))
            uf.union(calcNode(row, (col - 1)), calcNode(row, col));
        if (col <= n - 1 && isOpen(row, col + 1))
            uf.union(calcNode(row, (col + 1)), calcNode(row, col));
        count++;
    }

    private int calcNode(int row, int col) {
        return (row - 1) * n + col;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkArguments(row, col);
        return map[row - 1][col - 1] == 1;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkArguments(row, col);
        return isOpen(row, col) && uf.connected(calcNode(row, col), top);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return count > 0 && uf.connected(top, bottom);
    }

    private void checkArguments(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Row and Column index should be in range [1.." + n + "]");
        }
    }

    public static void main(String[] args) {
        int n = 1;
        Percolation p = new Percolation(n);
//        while (!p.percolates()) {
//            p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
//        }
        System.out.println(p.percolates());
        p.open(1, 1);
        System.out.println(p.percolates());
        System.out.println(p.numberOfOpenSites());

    }
}
