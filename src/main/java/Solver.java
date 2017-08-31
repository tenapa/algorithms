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
        MinPQ<BoardNode> queue = new MinPQ<BoardNode>(new Comparator<BoardNode>() {
            public int compare(BoardNode b1, BoardNode b2) {
                return Integer.compare(b1.getBoard().manhattan() + b1.getMoves(), b2.getBoard().manhattan() + b2.getMoves());
            }
        });
        queue.insert(new BoardNode(initial, 0, null));
        BoardNode previous = queue.min();
        BoardNode searchNode = queue.delMin();
        for (BoardNode board : searchNode.neighbors()) {
            queue.insert(board);
        }
        BoardNode minNode = queue.delMin();

        MinPQ<BoardNode> twinQueue = new MinPQ<BoardNode>(new Comparator<BoardNode>() {
            public int compare(BoardNode b1, BoardNode b2) {
                return Integer.compare(b1.getBoard().manhattan() + b1.getMoves(), b2.getBoard().manhattan() + b2.getMoves());
            }
        });
        twinQueue.insert(new BoardNode(twin, 0, null));
        BoardNode twinPrevious = twinQueue.min();
        BoardNode twinSearchNode = twinQueue.delMin();
        for (BoardNode board : twinSearchNode.neighbors()) {
            twinQueue.insert(board);
        }
        BoardNode twinMinNode = twinQueue.delMin();

        while (!minNode.getBoard().isGoal() || queue.isEmpty()) {
            if (twinMinNode.getBoard().isGoal()) {
                solvable = false;
                moves = -1;
                break;
            }
            searchNode = minNode;
            for (BoardNode board : searchNode.neighbors()) {
                if (previous.equals(board)) continue;
                queue.insert(board);
            }
            minNode = queue.delMin();
            previous = minNode.getPrevious();

            twinSearchNode = twinMinNode;
            for (BoardNode board : twinSearchNode.neighbors()) {
                if (twinPrevious.equals(board)) continue;
                twinQueue.insert(board);
            }
            twinMinNode = twinQueue.delMin();
            twinPrevious = twinMinNode.getPrevious();
        }
        if (minNode.getBoard().isGoal()) {
            solvable = true;
            solution = buildSolution(minNode);
        }
    }

    private Iterable<Board> buildSolution(BoardNode minNode) {
        Stack<Board> solution = new Stack<Board>();
        BoardNode current = minNode;
        while (current.getPrevious() != null) {
            solution.push(current.getBoard());
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

    private class BoardNode {
        Board board;
        int moves;
        BoardNode previous;

        BoardNode(Board board, int moves, BoardNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        Board getBoard() {
            return board;
        }

        int getMoves() {
            return moves;
        }

        BoardNode getPrevious() {
            return previous;
        }

        Iterable<BoardNode> neighbors() {
            Iterable<Board> neighbors = board.neighbors();
            Collection<BoardNode> nodeNeighbors = new ArrayList<BoardNode>();
            for (Board neighbor : neighbors) {
                nodeNeighbors.add(new BoardNode(neighbor, moves + 1, this));

            }
            return nodeNeighbors;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BoardNode boardNode = (BoardNode) o;

            return board != null ? board.equals(boardNode.board) : boardNode.board != null;

        }
    }
}
