import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class SuccessorWithDelete {
    private boolean[] delete;
    private WeightedQuickUnionUF qu;
    private static final int ERROR = -1;

    SuccessorWithDelete(int n) {
        delete = new boolean[n];
        qu = new WeightedQuickUnionUF(n);
        for (int i = 0; i < n; i++) {
            delete[i] = false;
        }

    }

    public void remove(int n) {
        if (n > delete.length - 1 || n <= 0) {
            throw new IllegalArgumentException("n is not in removable range");
        }
        qu.union(n - 1, n);
        delete[n - 1] = true;
    }


    public int find(int n) {
        return qu.find()
    }

    public static void main(String[] args) {

    }
}
