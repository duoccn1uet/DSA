import java.util.ArrayList;

public class Board {

    private int[] board;
    private int n;
    private final int[] dx = {-1, 0, 1, 0};
    private final int[] dy = {0, 1, 0, -1};

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null)
            throw new IllegalArgumentException("null param provided in Board's constructor");
        if (tiles.length != tiles[0].length)
            throw new java.lang.IllegalArgumentException();
        n = tiles.length;
        board = new int[n * n];
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                board[to1D(i, j)] = tiles[i][j];
    }

    private int to1D(int x, int y) {
        return x * n + y;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int res = 0;
        for (int i = 0; i < board.length; ++i)
            if (board[i] != 0 && board[i] != i + 1)
                ++res;
        return res;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int res = 0;
        for (int i = 0, id = 0; i < n; ++i)
            for (int j = 0; j < n; ++j) {
                if (board[id] != 0 && board[id] != id + 1) {
                    int x = (board[id] - 1) / n;
                    int y = (board[id] - 1) % n;
                    res += Math.abs(x - i) + Math.abs(y - j);
                }
                ++id;
            }
        return res;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < board.length - 1; i++)
            if (board[i] != i + 1)
                return false;
        return true;

    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        if (that.dimension() != n)
            return false;
        for (int i = 0; i < board.length; i++) {
            if (this.board[i] != that.board[i])
                return false;
        }
        return true;
    }

    private boolean inBound(int i, int j) {
        return 0 <= i && i < n && 0 <= j && j < n;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                if (board[to1D(i, j)] == 0) {
                    for (int k = 0; k < dx.length; ++k) {
                        int x = i + dx[k];
                        int y = j + dy[k];
                        if (inBound(x, y)) {
                            int[][] temp = new int[n][n];
                            for (int u = 0; u < n; ++u)
                                for (int v = 0; v < n; ++v)
                                    temp[u][v] = board[to1D(u, v)];
                            int t = temp[i][j];
                            temp[i][j] = temp[x][y];
                            temp[x][y] = t;
                            neighbors.add(new Board(temp));
                        }
                    }
                    break;
                }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] temp = new int[n][n];
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                temp[i][j] = board[to1D(i, j)];
        for (int t = 0; t < board.length; ++t)
            if (board[t] != 0) {
                int i = t / n;
                int j = t % n;
                for (int k = 0; k < dx.length; ++k) {
                    int x = i + dx[k];
                    int y = j + dy[k];
                    if (inBound(x, y) && temp[x][y] != 0) {
                        int tmp = temp[i][j];
                        temp[i][j] = temp[x][y];
                        temp[x][y] = tmp;
                        return new Board(temp);
                    }
                }
            }
        return new Board(temp);
    }

    public String toString() {
        StringBuilder res = new StringBuilder(n + "\n");
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j)
                res.append(board[to1D(i, j)]).append(" ");
            res.append("\n");
        }
        return res.toString();
    }
}