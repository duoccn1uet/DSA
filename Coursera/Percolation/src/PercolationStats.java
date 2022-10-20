import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double CONFIDENCE_95 = 1.96;
    private int n;
    private int trials;
    private double[] trialRatio;
    private double x;
    private double s;
    private double cLo;
    private double cHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        this.trials = trials;
        trialRatio = new double[trials];
        for (int i = 0; i < trials; ++i) {
            Percolation p = new Percolation(n);
            int row;
            int col;
            int d = 0;
            do {
                row = StdRandom.uniformInt(n) + 1;
                col = StdRandom.uniformInt(n) + 1;
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    ++d;
                }
            } while (!p.percolates());
            trialRatio[i] = ((double) d / (n * n));
        }
        x = StdStats.mean(trialRatio);
        s = StdStats.stddev(trialRatio);
        cLo = x - CONFIDENCE_95 * s / Math.sqrt(trials);
        cHi = x + CONFIDENCE_95 * s / Math.sqrt(trials);
    }

    // sample mean of percolation threshold
    public double mean() {
        return x;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return s;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return cLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return cHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        /**PercolationStats p = new PercolationStats(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));
        System.out.println("mean                    = " + p.mean() + "\n" +
                "stddev                   = " + p.stddev() + "\n" +
                "95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");*/
    }
}
