import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Solver {

    private class BoardWrapper implements Comparable<BoardWrapper> {
        private final Board board;
        private final int moves;
        private final int priority;
        private final BoardWrapper previousBoardWrapper;

        BoardWrapper(Board b, int m, BoardWrapper previous) {
            board = b;
            moves = m;
            priority = board.manhattan() + moves;
            previousBoardWrapper = previous;
        }

        public int compareTo(BoardWrapper o) {
            return Integer.compare(this.priority, o.priority);
        }

    }

    private int steps = -1;
    private LinkedList<Board> solutions = new LinkedList<Board>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<BoardWrapper> pq = new MinPQ<BoardWrapper>();
        MinPQ<BoardWrapper> pqForTwin = new MinPQ<BoardWrapper>();

        pq.insert(new BoardWrapper(initial, 0, null));
        pqForTwin.insert(new BoardWrapper(initial.twin(), 0, null));

        while (true) {
            BoardWrapper m = pq.delMin();
            Board currentBoard = m.board;
            BoardWrapper previousBoardWrapper = m.previousBoardWrapper;

            BoardWrapper mForTwin = pqForTwin.delMin();
            Board currentBoardForTwin = mForTwin.board;
            BoardWrapper previousBoardWrapperForTwin = mForTwin.previousBoardWrapper;

            int currentMoves = m.moves;

            if (currentBoard.isGoal()) {
                steps = currentMoves;
                BoardWrapper c = m;
                while (c != null) {
                    solutions.addFirst(c.board);
                    c = c.previousBoardWrapper;
                }
                break;
            }
            if (currentBoardForTwin.isGoal()) {
                break;
            }


            Iterable<Board> neighbors = currentBoard.neighbors();
            Iterable<Board> neighborsForTwin = currentBoardForTwin.neighbors();

            for (Board b : neighbors) {
                if (currentMoves == 0) {
                    pq.insert(new BoardWrapper(b, currentMoves + 1, m));
                } else {
                    if (!b.equals(previousBoardWrapper.board)) {
                        pq.insert(new BoardWrapper(b, currentMoves + 1, m));
                    }
                }

            }
            for (Board b : neighborsForTwin) {
                if (currentMoves == 0) {
                    pqForTwin.insert(new BoardWrapper(b, currentMoves + 1, mForTwin));
                } else {
                    if (!b.equals(previousBoardWrapperForTwin.board)) {
                        pqForTwin.insert(new BoardWrapper(b, currentMoves + 1, mForTwin));
                    }
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return (steps != -1);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return steps;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return solutions;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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