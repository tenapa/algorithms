import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * @author Tetiana_Prynda
 *         Created on 8/30/2017.
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
    public void board_neighbors() throws Exception {
        final Board board = new Board(new int[][]{new int[]{0, 1, 3}, new int[]{4, 2, 5}, new int[]{7, 8, 6}});
        Collection<Board> expectedNeighbors = new ArrayList<Board>(2);
        expectedNeighbors.add(new Board(new int[][]{new int[]{4, 1, 3}, new int[]{0, 2, 5}, new int[]{7, 8, 6}}));
        expectedNeighbors.add(new Board(new int[][]{new int[]{1, 0, 3}, new int[]{4, 2, 5}, new int[]{7, 8, 6}}));
        Iterable<Board> actualNeighbors = board.neighbors();

        assertEquals(expectedNeighbors, actualNeighbors);
    }

    @Test
    public void Board_puzzle04() throws Exception {
        Solver solution = runTest("8puzzle/puzzle04.txt");
        assertEquals(true, solution.isSolvable());
        assertEquals(4, solution.moves());
        assertEquals(5, getSolutionSize(solution));
//        System.out.println(printSolution(solution.solution()));
    }

    private int getSolutionSize(Solver solution) {
        Iterable<Board> pathToSolution = solution.solution();
        int count = 0;
        for (Board board : pathToSolution) {
            count++;
        }
        return count;
    }

    @Test
    public void Board_puzzle2x2_00() throws Exception {
        Solver solution = runTest("8puzzle/puzzle2x2-00.txt");
        assertEquals(true, solution.isSolvable());
        assertEquals(0, solution.moves());
        assertEquals(0, getSolutionSize(solution));

//        System.out.println(printSolution(solution.solution()));
    }

    @Test
    public void Board_puzzle2x2_01() throws Exception {
        Solver solution = runTest("8puzzle/puzzle2x2-01.txt");
        assertEquals(true, solution.isSolvable());
        assertEquals(1, solution.moves());
        assertEquals(2, getSolutionSize(solution));

//        System.out.println(printSolution(solution.solution()));
    }


    @Test
    public void Board_puzzle2x2_02() throws Exception {
        Solver solution = runTest("8puzzle/puzzle2x2-02.txt");
        assertEquals(true, solution.isSolvable());
        assertEquals(2, solution.moves());
        assertEquals(3, getSolutionSize(solution));

//        System.out.println(printSolution(solution.solution()));
    }

    @Test
    public void Board_puzzle2x2_03() throws Exception {
        Solver solution = runTest("8puzzle/puzzle2x2-03.txt");
        assertEquals(true, solution.isSolvable());
        assertEquals(3, solution.moves());
        assertEquals(4, getSolutionSize(solution));

//        System.out.println(printSolution(solution.solution()));
    }

    @Test
    public void Board_puzzle2x2_04() throws Exception {
        Solver solution = runTest("8puzzle/puzzle2x2-04.txt");
        assertEquals(true, solution.isSolvable());
        assertEquals(4, solution.moves());
//        System.out.println(printSolution(solution.solution()));
    }

    @Test
    public void Board_puzzle2x2_05() throws Exception {
        Solver solution = runTest("8puzzle/puzzle2x2-05.txt");
        assertEquals(true, solution.isSolvable());
        assertEquals(5, solution.moves());
        assertEquals(6, getSolutionSize(solution));

//        System.out.println(printSolution(solution.solution()));
    }

    @Test
    public void Board_puzzle2x2_06() throws Exception {
        Solver solution = runTest("8puzzle/puzzle2x2-06.txt");
//        System.out.println(printSolution(solution.solution()));
        assertEquals(true, solution.isSolvable());
        assertEquals(6, solution.moves());
        assertEquals(7, getSolutionSize(solution));

    }

    @Test
    public void Board_puzzle3x3_31() throws Exception {
        Solver solution = runTest("8puzzle/puzzle3x3-31.txt");
//        System.out.println(printSolution(solution.solution()));
        assertEquals(true, solution.isSolvable());
        assertEquals(31, solution.moves());
        assertEquals(32, getSolutionSize(solution));

    }

//    @Test
    public void Board_puzzle4x4_80() throws Exception {
        Solver solution = runTest("8puzzle/puzzle4x4-80.txt");
//        System.out.println(printSolution(solution.solution()));
        assertEquals(true, solution.isSolvable());
        assertEquals(80, solution.moves());
    }

    private String printSolution(Iterable<Board> solution) {
        StringBuilder builder = new StringBuilder();
        int i = 1;
        for (Board board : solution) {
            builder.append(i++).append("\n");
            builder.append(board.toString()).append("\n");
        }
        return builder.toString();
    }

    @Test
    public void Board_puzzle2x2_unsolvable1() throws Exception {
        Solver solution = runTest("8puzzle/puzzle2x2-unsolvable1.txt");
        assertEquals(false, solution.isSolvable());
        assertEquals(-1, solution.moves());
    }

    @Test
    public void Board_puzzle3x3_unsolvable() throws Exception {
        Solver solution = runTest("8puzzle/puzzle3x3-unsolvable.txt");
        assertEquals(false, solution.isSolvable());
        assertEquals(-1, solution.moves());
    }

//    @Test
    public void Board_puzzle4x4_unsolvable() throws Exception {
        Solver solution = runTest("8puzzle/puzzle4x4-unsolvable.txt");
        assertEquals(false, solution.isSolvable());
        assertEquals(-1, solution.moves());
    }

    private Solver runTest(String fileName) {
        In in = new In(fileName);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        System.out.println(initial);
        // solve the puzzle
        return new Solver(initial);
    }

}