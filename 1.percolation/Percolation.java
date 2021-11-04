/******************************************************************************
 *  Name:    Tim Ruszala
 *  NetID:   truszala
 *  Precept: P04A
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Modeling Percolation using an N-by-N grid and Union-Find data 
 *                structures to determine the threshold. woot.
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


// false == blocked
public class Percolation {
    
    private final int n;                     // problem size
    private final boolean[][] grid;          // shows whether open or closed
    private final WeightedQuickUnionUF uf;   // shows site connections
    
    private final int topKey;         // virtual top component
    private final int bottomKey;      // virtual bottom component
    
    private int count;                       // counts # of open sites
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int size)
    {
        if (size <= 0) throw new java.lang.IllegalArgumentException();
        
        n = size;
        uf = new WeightedQuickUnionUF(n*n + 2); // +2 for the virtual components
        grid = new boolean[n][n];
        topKey = n*n;
        bottomKey = n*n + 1;
        count = 0;
        
        for (int i = 0; i < n; i++)    // connects top nodes to virtual top
        {
            uf.union(i, topKey);
        }
        
        for (int i = n*n - n; i < n*n; i++) // connects bottom nodes to virtual
                                            // bottom.
        {
            uf.union(i, bottomKey);
        }
    }
    
    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (row < 0 || row >= n) throw new java.lang.IllegalArgumentException();
        if (col < 0 || col >= n) throw new java.lang.IllegalArgumentException();
        
        if (!grid[row][col])                        // checks if site is
                                                           // already open
        {
            grid[row][col] = true;                         // opens site
            count++;
            
            // connects site with open neighbors
            if (row > 0 && grid[row-1][col])                  
                uf.union(val(row, col), val(row-1, col));
            if (col > 0 && grid[row][col-1])
                uf.union(val(row, col), val(row, col-1));
            if (row < (n-1) && grid[row+1][col])
                uf.union(val(row, col), val(row+1, col));
            if (col < (n-1) && grid[row][col+1])
                uf.union(val(row, col), val(row, col+1));
        }
    }
    
    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if (row < 0 || row >= n) throw new java.lang.IllegalArgumentException();
        if (col < 0 || col >= n) throw new java.lang.IllegalArgumentException();
        
        return grid[row][col];
    }
    
    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (row < 0 || row >= n) throw new java.lang.IllegalArgumentException();
        if (col < 0 || col >= n) throw new java.lang.IllegalArgumentException();
        
        if (!isOpen(row, col)) return false;
        int val = val(row, col);
        return uf.connected(val, topKey);
    }
    
    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return count;
    }
    
    
    // does the system percolate?
    public boolean percolates()
    {
        if (n == 1)
            return isOpen(0, 0);
        return uf.connected(topKey, bottomKey);
    }
    
    // converts boolean[][] coordinates to uf indices
    private int val(int row, int col)
    {
        int val = (n * row) + col;
        return val;
    }
    
    // unit testing (required)
    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]);
        Percolation test = new Percolation(n);
        
        StdOut.println("Before any sites are opened:");
        StdOut.println("percolates() = " + test.percolates());
        StdOut.println("numberOfOpenSites() = " + test.numberOfOpenSites());
        StdOut.println("(0,0) is full? " + test.isFull(0, 0));
        StdOut.println("(0,0) is open? " + test.isOpen(0, 0));
        StdOut.println();
        
        StdOut.println("After Opening (0,0):");
        test.open(0, 0);
        StdOut.println("(0,0) is full? " + test.isFull(0, 0));
        StdOut.println("(0,0) is open? " + test.isOpen(0, 0));
        StdOut.println("percolates() = " + test.percolates());
        StdOut.println("numberOfOpenSites() = " + test.numberOfOpenSites());
        StdOut.println();
        
        StdOut.println("After Opening (n-1, n-1):");
        StdOut.println("(n-1, n-1) is full? " + test.isFull(n-1, n-1));
        StdOut.println();
        
        
        StdOut.println("Straight pass through Column 0 opened" + 
                       "from top to bottom:");
        for (int i = 0; i < n; i++)
            test.open(i, 0);
        StdOut.println("percolates() = " + test.percolates());
        StdOut.println("numberOfOpenSites() = " + test.numberOfOpenSites());
    }
}