/******************************************************************************
 *  Name:    Tim Ruszala
 *  NetID:   truszala
 *  Precept: P04A
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Data type Percolation Stats which takes as its arguments
 *                square grid dimensions (n) and number of trials (t).
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {  
    
    // constant used to calculate 95% confidence
    private static final double CONF_95 = 1.96; 
    // # of trials
    private final int t;                 
    // records each trial's percolation threshold
    private final double[] percThresh;        
    
    
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n <= 0) throw new java.lang.IllegalArgumentException();
        if (trials <= 0) throw new java.lang.IllegalArgumentException();
        
        t = trials;
        percThresh = new double[t];
        
        for (int i = 0; i < t; i++)
        {
            Percolation p = new Percolation(n);
            while (!p.percolates())
            {
                p.open(StdRandom.uniform(0, n), StdRandom.uniform(0, n));
            }
            percThresh[i] = ((double) p.numberOfOpenSites() / (n * n));
        }
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(percThresh);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(percThresh);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow()
    {
        return (this.mean() - (CONF_95 * this.stddev() / Math.sqrt(t)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh()
    {
        return (this.mean() + (CONF_95 * this.stddev() / Math.sqrt(t)));
    }

   // test client (see below)
   public static void main(String[] args)
   {
       int n = Integer.parseInt(args[0]);
       int t = Integer.parseInt(args[1]);
       
       Stopwatch time = new Stopwatch();
       PercolationStats test = new PercolationStats(n, t);  
       
       StdOut.println("mean() = " + test.mean());
       StdOut.println("stddev() = " + test.stddev());
       StdOut.println("confidenceLow() = " + test.confidenceLow());
       StdOut.println("confidenceHigh() = " + test.confidenceHigh());
       StdOut.println("elapsed time = " + time.elapsedTime());
   }
}