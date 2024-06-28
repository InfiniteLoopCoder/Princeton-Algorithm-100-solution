import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private double[] possiblities;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be positive");
        }
        possiblities = new double[trials];
        int times = 0;
        int r, c;
        while (times < trials) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                do {
                    r = StdRandom.uniformInt(1, n + 1);
                    c = StdRandom.uniformInt(1, n + 1);
                } while (p.isOpen(r, c));
                p.open(r, c);
            }
            possiblities[times] = (double) p.numberOfOpenSites() / (n * n);
            times++;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(possiblities);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(possiblities);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(possiblities.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(possiblities.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stat = new PercolationStats(n, t);
        StdOut.println("mean                    = " + stat.mean());
        StdOut.println("stddev                  = " + stat.stddev());
        StdOut.println("95% confidence interval = [" + stat.confidenceLo() + ", " + stat.confidenceHi() + "]");
    }

}
