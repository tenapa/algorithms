import edu.princeton.cs.algs4.MinPQ;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * @author Tetiana_Prynda
 *         Created on 8/30/2017.
 */
public class Solver {

    private boolean solvable;
    private Collection<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException(" Can not process Null board");
        if (initial.isGoal()) {
            solution = Collections.emptyList();
            solvable = true;
            return;
        }
        MinPQ<Board> queue = new MinPQ<Board>(new Comparator<Board>() {
            public int compare(Board b1, Board b2) {
                return Integer.compare(b1.manhattan(), b2.manhattan());
            }
        });
        queue.insert(initial);
        Board previous = queue.min();
        Board searchNode = queue.delMin();
        for (Board board : searchNode.neighbors()) {
            queue.insert(board);
        }
        Board minNode = queue.delMin();
        solution = new LinkedList<Board>();
        solution.add(minNode);
        while (!minNode.isGoal() || queue.isEmpty()) {
            searchNode = minNode;
            for (Board board : searchNode.neighbors()) {
                if (previous.equals(board)) continue;
                queue.insert(board);
            }
            minNode = queue.delMin();
            previous = minNode.getPrevious();
            solution.add(minNode);
        }
        solvable = true;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solution.size();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
    }
}
