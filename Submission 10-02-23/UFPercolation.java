import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using the UF data structure.
public class UFPercolation implements Percolation {
    private int n; // Percolation system size.
    private boolean[][] open; // Percolation system.
    private int openSites; // Number of open sites.
    private WeightedQuickUnionUF uf; // Union-find rep of percolation.


    // Constructs an n x n percolation system, with all sites blocked.
    public UFPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        this.n = n;
        this.open = new boolean[n][n];
        this.openSites = 0;
        // We add +2 for the virtual sources.
        this.uf = new WeightedQuickUnionUF(n*n+2);

    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        if (!isOpen(i, j)) {
            open[i][j] = true;
            openSites++;

            int nsite = encode(i, j);
            if (nsite == 0) {
                uf.union(nsite, 0);
            }
            if (nsite == n - 1) {
                uf.union(nsite, n * n + 1);
            }
            if ((i - 1) >= 0 && open[i - 1][j]) {
                uf.union(encode(i, j), encode(i - 1, j));
            }
            if ((j + 1) < n && open[i][j + 1]) {
                uf.union(encode(i, j), encode(i, j + 1));
            }
            if ((j - 1) >= 0 && open[i][j - 1]) {
                uf.union(encode(i, j), encode(i, j - 1));
            }
            if ((i + 1) < n && open[i + 1][j]) {
                uf.union(encode(i, j), encode(i + 1, j));
            }
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return open[i][j];
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return isOpen(i, j) && uf.connected(encode(i, j), 0);
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        return uf.connected(0, n * n + 1);
    }

    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        return i * n + j + 1;
    }



    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        UFPercolation perc = new UFPercolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}