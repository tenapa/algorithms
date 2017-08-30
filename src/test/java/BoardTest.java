import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Tetiana_Prynda
 * Created on 8/30/2017.
 */
public class BoardTest {

    @Test
    public void board_hamming() throws Exception {
        final Board board = new Board(new int[][]{new int[]{8, 1, 3}, new int[]{4, 0, 2}, new int[]{7, 6, 5}});
        assertEquals(5, board.hamming());
    }

    @Test
    public void board_manhattan() throws Exception {
        final Board board = new Board(new int[][]{new int[]{8, 1, 3}, new int[]{4, 0, 2}, new int[]{7, 6, 5}});
        assertEquals(10, board.manhattan());
    }

    @Test
    public void board_manhattan2() throws Exception {
        final Board board = new Board(new int[][]{new int[]{0, 1, 3}, new int[]{4, 2, 5}, new int[]{7, 8, 6}});
        assertEquals(4, board.manhattan());
    }

    @Test
    public void board_manhattan3() throws Exception {
        final Board board = new Board(new int[][]{new int[]{1, 2, 3}, new int[]{4, 0, 5}, new int[]{7, 8, 6}});
        assertEquals(2, board.manhattan());
    }

    @Test
    public void Board_constructor_puzzle04() throws Exception {
        runTest("8puzzle/puzzle04.txt");
    }

    private void runTest(String fileName){
        In in = new In(fileName);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        System.out.println(initial);
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        
    }

}