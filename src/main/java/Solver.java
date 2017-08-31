import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

/**
 * @author Tetiana_Prynda
 *         Created on 8/30/2017.
 */
public class Solver {

    private boolean solvable;
    private Iterable<Board> solution;
    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException(" Can not process Null board");
        if (initial.isGoal()) {
            solution = Collections.emptyList();
            solvable = true;
            moves = 0;
            return;
        }
        Board twin = initial.twin();
        if (twin.isGoal()) {
            solvable = false;
            moves = -1;
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
//        solution = new LinkedList<Board>();
//        solution.add(minNode);

        MinPQ<Board> twinQueue = new MinPQ<Board>(new Comparator<Board>() {
            public int compare(Board b1, Board b2) {
                return Integer.compare(b1.manhattan(), b2.manhattan());
            }
        });
        twinQueue.insert(twin);
        Board twinPrevious = twinQueue.min();
        Board twinSearchNode = twinQueue.delMin();
        for (Board board : twinSearchNode.neighbors()) {
            twinQueue.insert(board);
        }
        Board twinMinNode = twinQueue.delMin();

//        int activeMove = 2;
        while (!minNode.isGoal() || queue.isEmpty()) {
//            System.out.println("Move "+activeMove++);
            if (twinMinNode.isGoal()) {
                solvable = false;
                moves = -1;
                break;
            }
            searchNode = minNode;
            for (Board board : searchNode.neighbors()) {
                if (previous.equals(board)) continue;
                queue.insert(board);
            }
            minNode = queue.delMin();
            previous = minNode.getPrevious();
//            solution.add(minNode);

            twinSearchNode = twinMinNode;
            for (Board board : twinSearchNode.neighbors()) {
                if (twinPrevious.equals(board)) continue;
                twinQueue.insert(board);
            }
            twinMinNode = twinQueue.delMin();
            twinPrevious = twinMinNode.getPrevious();
        }
        if (minNode.isGoal()) {
            solvable = true;
            solution = buildSolution(minNode);
        }
    }

    private Iterable<Board> buildSolution(Board minNode) {
        Stack<Board> solution = new Stack<Board>();
        Board current = minNode;
        while (current.getPrevious() != null) {
            solution.push(current);
            moves++;
            current = current.getPrevious();

        }
        return solution;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
    }
}
