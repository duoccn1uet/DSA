import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private enum SiteStatus {
        BLOCK,
        OPEN,
        FULL
    }

    private final byte[] moveRow = {-1, 0, 1, 0};
    private final byte[] moveCol = {0, 1, 0, -1};
    private int n;
    private int top;
    private int bottom;
    private int countOpenSites;
    private SiteStatus[] siteStatus;
    private WeightedQuickUnionUF ufGrid;
    private WeightedQuickUnionUF ufFull;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        countOpenSites = 0;
        top = n * n + 1;
        bottom = n * n + 2;
        siteStatus = new SiteStatus[n * n + 3];
        for (int i = 1; i <= n * n + 2; ++i)
            siteStatus[i] = SiteStatus.BLOCK;
        ufGrid = new WeightedQuickUnionUF(n * n + 3);
        ufFull = new WeightedQuickUnionUF(n * n + 3);
    }

    private int convert2dTo1d(int row, int col) {
        return n * (row - 1) + col;
    }

    private boolean isInBounds(int row, int col) {
        return (1 <= row && row <= n && 1 <= col && col <= n);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isInBounds(row, col))
            throw new IllegalArgumentException();
        if (isOpen(row, col))
            return;
        ++countOpenSites;
        int index = convert2dTo1d(row, col);
        siteStatus[index] = SiteStatus.OPEN;
        if (row == 1) {
            ufGrid.union(index, top);
            ufFull.union(index, top);
        }
        if (row == n)
            ufGrid.union(index, bottom);
        for (int i = 0; i < 4; ++i) {
            final int addRow = moveRow[i];
            final int addCol = moveCol[i];
            int neighborRow = row + addRow;
            int neighborCol = col + addCol;
            if (isInBounds(neighborRow, neighborCol)) {
                int neighborIndex = convert2dTo1d(neighborRow, neighborCol);
                if (siteStatus[neighborIndex] != SiteStatus.BLOCK) {
                    ufGrid.union(index, neighborIndex);
                    ufFull.union(index, neighborIndex);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isInBounds(row, col))
            throw new IllegalArgumentException();
        int index = convert2dTo1d(row, col);
        return siteStatus[index] != SiteStatus.BLOCK;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isInBounds(row, col))
            throw new IllegalArgumentException();
        int index = convert2dTo1d(row, col);
        if (siteStatus[index] == SiteStatus.FULL)
            return true;
        if (ufFull.find(index) == ufFull.find(top)) {
            siteStatus[index] = SiteStatus.FULL;
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return ufGrid.find(top) == ufGrid.find(bottom);
    }
    // test client (optional)
}
