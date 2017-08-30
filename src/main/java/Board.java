import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Tetiana_Prynda
 * Created on 8/30/2017.
 */
public class Board {

    private final int[][] blocks;
    private final int[][] goal;
    private final int length;
    private final int hamming;
    private final int manhattan;
    private boolean solved;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.length = blocks.length;
        this.blocks = new int[length][length];
        this.goal = new int[length][length];
        this.solved = true;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                final int actualItem = blocks[i][j];
                final int expectedItem = (i * length + j + 1) % (length * length);
                this.blocks[i][j] = actualItem;
                this.goal[i][j] = expectedItem;
                if (actualItem != expectedItem) solved = false;
            }
        }
        this.hamming = countHamming();
        this.manhattan = countManhattan();
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
                if (blocks[i][j] != goal[i][j] && goal[i][j] != 0) val++;
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
        final int i = StdRandom.uniform(length);
        final int j = StdRandom.uniform(length);
        final int direction = StdRandom.uniform(4);

        return getTwin(i, j, direction);
    }

    private Board getTwin(int i, int j, int direction) {
        //direction considered as 0 - up, 1 - right, 2 - down, 3 - left. if no option - switch up-down, left-right
        int secondI;
        int secondJ;
        switch (direction) {
            case 0:
                secondI = i == 0 ? i + 1 : i - 1;
                secondJ = j;
                break;
            case 2:
                secondI = i == length - 1 ? i - 1 : i + 1;
                secondJ = j;
                break;
            case 1:
                secondI = i;
                secondJ = j == length - 1 ? j - 1 : j + 1;
                break;
            case 3:
                secondI = i;
                secondJ = j == 0 ? j + 1 : j - 1;
                break;
            default:
                secondI = i;
                secondJ = j;
        }
        final int original = this.blocks[i][j];
        final int neighbor = this.blocks[secondI][secondJ];
        this.blocks[i][j] = neighbor;
        this.blocks[secondI][secondJ] = original;
        final Board board = new Board(this.blocks);
        this.blocks[i][j] = original;
        this.blocks[secondI][secondJ] = neighbor;
        return board;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board board = (Board) y;
        return Arrays.equals(blocks, board.blocks);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return Collections.emptyList();
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder builder = new StringBuilder(this.blocks.length * this.blocks.length * 2);
        for (int[] block : blocks) {
            for (int j = 0; j < blocks.length; j++) {
                builder.append(block[j]).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
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
