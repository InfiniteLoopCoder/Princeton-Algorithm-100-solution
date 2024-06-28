import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] data;
    private final int nums;
    private WeightedQuickUnionUF qu;
    // help to fix the backwash problem
    private WeightedQuickUnionUF quOnlyTop;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        nums = n;
        // data array keep the same scale as union set, to make index method same
        data = new boolean[(nums + 2) * nums];
        for (int i = 0; i < (nums + 2) * nums; i++) {
            data[i] = false;
        }
        // make virtual line "true" to make openHelper simpler
        for (int i = 0; i < nums; i++) {
            data[i] = true;
            data[nums * (nums + 1) + i] = true;
        }

        // at the top and the bottom, add two connected virtual rows
        qu = new WeightedQuickUnionUF((nums + 2) * nums);
        quOnlyTop = new WeightedQuickUnionUF((nums + 1) * nums);
        // connect the top and the bottom virtual line separately
        for (int i = 0; i < nums - 1; i++) {
            qu.union(i, i + 1);
            qu.union(nums * (nums + 1) + i, nums * (nums + 1) + i + 1);
            quOnlyTop.union(i, i + 1);
        }
        openSites = 0;
    }

    // help to decide which can be unioned
    private void openHelper(int row, int col) {
        // row and col here are different, refer to direct index of the union
        if (col < (nums - 1)) {
            if (data[row * nums + col + 1]) {
                qu.union(row * nums + col, row * nums + col + 1);
                quOnlyTop.union(row * nums + col, row * nums + col + 1);
            }
        }
        if (col > 0) {
            if (data[row * nums + col - 1]) {
                qu.union(row * nums + col, row * nums + col - 1);
                quOnlyTop.union(row * nums + col, row * nums + col - 1);
            }
        }

        if (data[(row + 1) * nums + col]) {
            qu.union(row * nums + col, (row + 1) * nums + col);
            if (row < nums) {
                // for quOnlyTop, last row can't connect to non-exist virtual row
                quOnlyTop.union(row * nums + col, (row + 1) * nums + col);
            }
        }
        if (data[(row - 1) * nums + col]) {
            qu.union(row * nums + col, (row - 1) * nums + col);
            quOnlyTop.union(row * nums + col, (row - 1) * nums + col);
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            col--;
            data[nums * row + col] = true;
            // When there is a new open site, consider new union
            openHelper(row, col);
            openSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (col < 1 || col > nums || row < 1 || row > nums) {
            throw new IllegalArgumentException("Wrong position");
        }
        col--;
        return data[nums * row + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (col < 1 || col > nums || row < 1 || row > nums) {
            throw new IllegalArgumentException("Wrong position");
        }
        col--;
        // already optimised: virtual rows
        return quOnlyTop.find(nums * row + col) == quOnlyTop.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return qu.find(0) == qu.find(nums * (nums + 1));
    }

    // test client (optional)
    public static void main(String[] args) {
    }

}
