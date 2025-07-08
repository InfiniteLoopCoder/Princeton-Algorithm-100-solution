import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Board {
    private final int[] data;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles[0].length;
        data = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                data[n * i + j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        String newLine = System.lineSeparator();
        StringBuilder result = new StringBuilder();
        result.append(n).append(newLine);
        for (int i = 0; i < n; i++) {
            result.append(" ");
            for (int j = 0; j < n; j++) {
                result.append(data[i * n + j]).append(" ");
            }
            if (i != n - 1) {
                result.append(newLine);
            }

        }
        return result.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int result = 0;
        for (int i = 0; i < n * n - 1; i++) {
            if ((data[i] != i + 1) && (data[i] != 0)) {
                result++;
            }
        }
        if (data[n * n - 1] != 0) {
            result++;
        }
        return result;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int num = data[i * n + j];
                if (num != 0) {
                    int goalRow = (num - 1) / n;
                    int goalCol = (num - 1) % n;
                    result += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y instanceof Board) {
            Board b = (Board) y;
            if (dimension() != b.dimension()) {
                return false;
            }
            for (int i = 0; i < n * n; i++) {
                if (this.data[i] != b.data[i]) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // find the 0 position
        int rowOfBlank = 0, colOfBlank = 0;
        for (int i = 0; i < n * n; i++) {
            if (data[i] == 0) {
                rowOfBlank = i / n;
                colOfBlank = i % n;
            }
        }
        // construct an Iterable<Board>
        LinkedList<Board> result = new LinkedList<Board>();
        // add neighbors by position
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n * n; i++) {
            tiles[i / n][i % n] = data[i];
        }
        if (rowOfBlank != 0) {
            tiles[rowOfBlank][colOfBlank] = tiles[rowOfBlank - 1][colOfBlank];
            tiles[rowOfBlank - 1][colOfBlank] = 0;
            Board b = new Board(tiles);
            tiles[rowOfBlank - 1][colOfBlank] = tiles[rowOfBlank][colOfBlank];
            tiles[rowOfBlank][colOfBlank] = 0;
            result.addLast(b);
        }
        if (rowOfBlank != n - 1) {
            tiles[rowOfBlank][colOfBlank] = tiles[rowOfBlank + 1][colOfBlank];
            tiles[rowOfBlank + 1][colOfBlank] = 0;
            Board b = new Board(tiles);
            tiles[rowOfBlank + 1][colOfBlank] = tiles[rowOfBlank][colOfBlank];
            tiles[rowOfBlank][colOfBlank] = 0;
            result.addLast(b);
        }
        if (colOfBlank != 0) {
            tiles[rowOfBlank][colOfBlank] = tiles[rowOfBlank][colOfBlank - 1];
            tiles[rowOfBlank][colOfBlank - 1] = 0;
            Board b = new Board(tiles);
            tiles[rowOfBlank][colOfBlank - 1] = tiles[rowOfBlank][colOfBlank];
            tiles[rowOfBlank][colOfBlank] = 0;
            result.addLast(b);
        }
        if (colOfBlank != n - 1) {
            tiles[rowOfBlank][colOfBlank] = tiles[rowOfBlank][colOfBlank + 1];
            tiles[rowOfBlank][colOfBlank + 1] = 0;
            Board b = new Board(tiles);
            tiles[rowOfBlank][colOfBlank + 1] = tiles[rowOfBlank][colOfBlank];
            tiles[rowOfBlank][colOfBlank] = 0;
            result.addLast(b);
        }
        return result;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n * n; i++) {
            tiles[i / n][i % n] = data[i];
        }
        int nonBlankCount = 0;
        int targetOne = 0;
        int targetTwo = 0;
        for (int i = 0; i < n * n; i++) {
            if (data[i] != 0) {
                nonBlankCount++;
                if (nonBlankCount == 1) {
                    targetOne = i;
                }
                if (nonBlankCount == 2) {
                    targetTwo = i;
                    break;
                }
            }
        }
        int temp = tiles[targetOne / n][targetOne % n];
        tiles[targetOne / n][targetOne % n] = tiles[targetTwo / n][targetTwo % n];
        tiles[targetTwo / n][targetTwo % n] = temp;
        return new Board(tiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // 创建一个初始的3x3拼图
        int[][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        // 创建Board对象
        Board board = new Board(tiles);
        // 测试toString方法
        StdOut.println("Board:");
        StdOut.println(board);
        // 测试dimension方法
        StdOut.println("Dimension: " + board.dimension());
        // 测试hamming方法
        StdOut.println("Hamming: " + board.hamming());
        // 测试manhattan方法
        StdOut.println("Manhattan: " + board.manhattan());
        // 测试isGoal方法
        StdOut.println("Is goal: " + board.isGoal());
        // 测试twin方法
        StdOut.println("Twin: ");
        StdOut.println(board.twin());
        // 测试neighbors方法
        StdOut.println("Neighbors:");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
        }

        // 测试equals方法
        int[][] tiles2 = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board2 = new Board(tiles2);
        StdOut.println("Board equals board2: " + board.equals(board2));

        int[][] tiles3 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board3 = new Board(tiles3);
        StdOut.println("Board3:");
        StdOut.println(board3);
        StdOut.println("Board equals board3: " + board.equals(board3));
        // 测试hamming方法
        StdOut.println("Hamming: " + board3.hamming());
        // 测试manhattan方法
        StdOut.println("Manhattan: " + board3.manhattan());
        // 测试isGoal方法
        StdOut.println("Is goal: " + board3.isGoal());
    }
}
