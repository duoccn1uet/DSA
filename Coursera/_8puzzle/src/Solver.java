import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {
    private boolean solvable;
    private ArrayList<Board> traceList = new ArrayList<>();

    private class Node implements Comparable<Node> {

        private Board board;
        private Node prev;
        private int countMoves = 0;
        private int totalCost = 0;

        public Node(Board board, Node prev, int countMoves, int totalCost) {
            this.board = board;
            this.prev = prev;
            this.countMoves = countMoves;
            this.totalCost = totalCost;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(totalCost, other.totalCost);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("null param provided in Solver's constructor");
        traceList = solve(initial);
        solvable = (traceList != null);
    }
    private ArrayList<Board> trace(Node u) {
        ArrayList<Board> path = new ArrayList<>();
        while (u != null) {
            path.add(u.board);
            u = u.prev;
        }
        Collections.reverse(path);
        return path;
    }

    private ArrayList<Board> solve(Board src) {
        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(new Node(src, null, 0, src.manhattan()));
        MinPQ<Node> pqtwin = new MinPQ<>();
        Board stwin = src.twin();
        pqtwin.insert(new Node(stwin, null, 0, stwin.manhattan()));
        while (!pq.isEmpty() && !pqtwin.isEmpty()) {
            Node u = pq.delMin();
            if (u.board.isGoal()) {
                return trace(u);
            }
            int countMoves = u.countMoves;
            int totalCost = u.totalCost;
            Node prev = u.prev;
            for (Board v : u.board.neighbors()) {
                if (prev == null || !prev.board.equals(v)) {
                    pq.insert(new Node(v, u, countMoves+1, countMoves+1 + v.manhattan()));
                }
            }

            u = pqtwin.delMin();
            if (u.board.isGoal()) {
                return null;
            }
            countMoves = u.countMoves;
            totalCost = u.totalCost;
            prev = u.prev;
            for (Board v : u.board.neighbors()) {
                if (prev == null || !prev.board.equals(v)) {
                    pqtwin.insert(new Node(v, u, countMoves+1, countMoves+1 + v.manhattan()));
                }
            }
        }
        return null;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solvable ? traceList.size()-1 : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return traceList;
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
