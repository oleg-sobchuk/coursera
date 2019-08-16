import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double COEFFICIENT = 1.96;

    private final int size;
    private final double[] thresholds;
    private final int experimentsCount;
    private final double mean;
    private final double stddev;

    // perform independent computational experiments on an N-by-N grid
    public PercolationStats(int n, int experimentsCount) {
        if (n <= 0) throw new IllegalArgumentException("N is out of bounds");
        if (experimentsCount <= 0) throw new IllegalArgumentException("T is out of bounds");
        size = n;
        this.experimentsCount = experimentsCount;
        thresholds = new double[experimentsCount];
        for (int i = 0; i < experimentsCount; i++) {
            thresholds[i] = findPercolationThreshold();
        }
        mean = StdStats.mean(thresholds);
        stddev = experimentsCount == 1 ? Double.NaN : StdStats.stddev(thresholds);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean - COEFFICIENT * stddev / Math.sqrt(experimentsCount);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean + COEFFICIENT * stddev / Math.sqrt(experimentsCount);
    }

    private double findPercolationThreshold() {
        Percolation percolation = new Percolation(size);
        int i, j;
        int count = 0;
        while (!percolation.percolates()) {
            do {
                i = StdRandom.uniform(size) + 1;
                j = StdRandom.uniform(size) + 1;
            } while (percolation.isOpen(i, j));

            percolation.open(i, j);
            count++;
        }
        return count / (Math.pow(size, 2));
    }

    // test client, described below
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int experimentCount = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, experimentCount);
        StdOut.printf("mean = %f\n", stats.mean());
        StdOut.printf("stddev = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", stats.confidenceLo(), stats.confidenceHi());
    }
}