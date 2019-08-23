import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int size;
    private final int width;
    private boolean[] open;
    private int openCount;
    private final WeightedQuickUnionUF uf; //have size+2 for top and bottom virtual connect to determine percolation
    private final WeightedQuickUnionUF ufCorrectIsFull; //have size+1 for only top virtual connect to determine correct isFull

    // create N-by-N grid, with all sites blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Invalid width number.");
        }
        size = n * n;
        width = n;
        open = new boolean[size];
        openCount = 0;
        uf = new WeightedQuickUnionUF(size + 2);
        ufCorrectIsFull = new WeightedQuickUnionUF(size + 1);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if (!isOpen(i, j)) {
            int num = convertToSingleIndex(i, j);
            open[num] = true;
            openCount++;
            unionToOpenNeighbors(i, j);
        }

    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        checkBounds(i, j);
        int num = convertToSingleIndex(i, j);
        return (open[num]);
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        checkBounds(i, j);
        return ufCorrectIsFull.connected(size, convertToSingleIndex(i, j));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(size, size + 1);
    }

    private void unionToOpenNeighbors(int i, int j) {
        int index = convertToSingleIndex(i, j);

        if (j < width && isOpen(i, j + 1)) {
            uf.union(index + 1, index);
            ufCorrectIsFull.union(index + 1, index);
        }

        if (j > 1 && isOpen(i, j - 1)) {
            uf.union(index - 1, index);
            ufCorrectIsFull.union(index - 1, index);
        }

        if (i < width && isOpen(i + 1, j)) {
            uf.union(index + width, index);
            ufCorrectIsFull.union(index + width, index);
        } else {
            if (i == width) {
                uf.union(size + 1, index);
            }
        }

        if (i > 1 && isOpen(i - 1, j)) {
            uf.union(index - width, index);
            ufCorrectIsFull.union(index - width, index);
        } else {
            if (i == 1) {
                uf.union(size, index);
                ufCorrectIsFull.union(size, index);
            }
        }
    }

    private int convertToSingleIndex(int i, int j) {
        return ((i * width - width) + j) - 1;
    }

    private void checkBounds(int i, int j) {
        if (i <= 0 || i > width) {
            throw new IllegalArgumentException("row index i is out of bounds");
        }
        if (j <= 0 || j > width) {
            throw new IllegalArgumentException("column index j is out of bounds");
        }
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);

        percolation.open(1, 3);
        percolation.open(2, 3);
        percolation.open(3, 3);
        percolation.open(3, 1);
        boolean isFull = percolation.isFull(3, 1);
        StdOut.println(isFull);
        percolation.open(2, 1);
        percolation.open(1, 1);


        boolean isPercolates = percolation.percolates();

        StdOut.println(isPercolates);

    }

}
