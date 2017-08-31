import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Tetiana_Prynda
 *         Created on 8/30/2017.
 */
public class Board {

    private final int[][] blocks;
    private final int length;
    private final int hamming;
    private final int manhattan;
    private boolean solved;
    private int zeroI;
    private int zeroJ;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.length = blocks.length;
        this.blocks = new int[length][length];
        this.solved = true;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                final int actualItem = blocks[i][j];
                final int expectedItem = getExpected(i, j);
                this.blocks[i][j] = actualItem;
                if (actualItem != expectedItem) solved = false;
                if (actualItem == 0) {
                    zeroI = i;
                    zeroJ = j;
                }
            }
        }
        this.hamming = countHamming();
        this.manhattan = countManhattan();
    }

    private int getExpected(int i, int j) {
        return (i * length + j + 1) % (length * length);
    }

    // board dimension n
    public int dimension() {
        return length;
    }

    // number of blocks out of place
    public int hamming() {
        return hamming;
    }

    private int countHamming() {
        int val = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                int expectedItem = getExpected(i, j);
                if (blocks[i][j] != expectedItem && expectedItem != 0) val++;
            }
        }
        return val;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattan;
    }

    private int countManhattan() {
        int val = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                final int actual = blocks[i][j] - 1;
                if (actual == -1) continue;
                int expectedI = actual / length;
                int expectedJ = actual % length;
                final int moves = Math.abs(expectedI - i) + Math.abs(expectedJ - j);
                val += moves;
            }
        }
        return val;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return solved;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int i = StdRandom.uniform(length);
        int j = StdRandom.uniform(length);
        int newI = StdRandom.uniform(length);
        int newJ = StdRandom.uniform(length);

        int originalValue = blocks[i][j];
        int swapValue = blocks[newI][newJ];
        while (originalValue == 0 || swapValue == 0 || originalValue == swapValue) {
            if (originalValue == 0) {
                i = StdRandom.uniform(length);
                j = StdRandom.uniform(length);
            } else {
                newI = StdRandom.uniform(length);
                newJ = StdRandom.uniform(length);
            }
            originalValue = blocks[i][j];
            swapValue = blocks[newI][newJ];
        }
        return moveItem(i, j, newI, newJ);
    }

    private Board moveItem(int i, int j, int aimI, int aimJ) {
        final int original = this.blocks[i][j];
        final int neighbor = this.blocks[aimI][aimJ];
        this.blocks[i][j] = neighbor;
        this.blocks[aimI][aimJ] = original;
        final Board board = new Board(this.blocks);
        this.blocks[i][j] = original;
        this.blocks[aimI][aimJ] = neighbor;
        return board;
    }

    private boolean canBeMovedUp(int i, int j) {
        return i > 0;
    }

    private boolean canBeMovedDown(int i, int j) {
        return i < length - 1;
    }

    private boolean canBeMovedLeft(int i, int j) {
        return j > 0;
    }

    private boolean canBeMovedRight(int i, int j) {
        return j < length - 1;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board board = (Board) y;
        return Arrays.deepEquals(blocks, board.blocks);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Collection<Board> neighbors = new ArrayList<Board>();
        if (canBeMovedDown(zeroI, zeroJ)) {
            neighbors.add(moveItem(zeroI, zeroJ, zeroI + 1, zeroJ));
        }
        if (canBeMovedUp(zeroI, zeroJ)) {
            neighbors.add(moveItem(zeroI, zeroJ, zeroI - 1, zeroJ));
        }
        if (canBeMovedLeft(zeroI, zeroJ)) {
            neighbors.add(moveItem(zeroI, zeroJ, zeroI, zeroJ - 1));
        }
        if (canBeMovedRight(zeroI, zeroJ)) {
            neighbors.add(moveItem(zeroI, zeroJ, zeroI, zeroJ + 1));
        }
        return neighbors;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(length).append("\n");
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        final int length = 3;
        final int[][] blocks = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                final int item = (i * length + j + 1) % (length * length);
                blocks[i][j] = item;
            }
        }
        final Board board = new Board(blocks);
        System.out.println(board);
        System.out.println(board.twin());
    }
}
