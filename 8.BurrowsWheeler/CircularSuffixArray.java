/******************************************************************************
 *  Name:     Tim Ruszala
 *  NetID:    truszala
 *  Precept:  P04A
 *
 *  Partner Name:       N/A
 *  Partner NetID:      N/A
 *  Partner Precept:    N/A
 *
 *  Description: A data type which forms an array of rotated suffixes, for use
 *  with BurrowsWheeler.java
 *
 ******************************************************************************/



import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray 
{
    private final int n; // string length
    private final int[] index; // holds the sorted suffixes' original indices
    
    // circular suffix array of s
    public CircularSuffixArray(String s)    
    {
        if (s == null) throw new IllegalArgumentException();
        
        n = s.length();
        CircularSuffix[] a = new CircularSuffix[n];
        index = new int[n];
        for (int i = 0; i < n; i++)
        {
            a[i] = new CircularSuffix(i, s);
        }
        
        Merge.sort(a);
        
        for (int i = 0; i < n; i++)
        {
            index[i] = a[i].index;
        }
    }
    
    private class CircularSuffix implements Comparable<CircularSuffix>
    {
        public final String ref; // reference to the original string
        public final int index; // the index for this suffix
        
        // creates a CircularSuffix object
        public CircularSuffix(int index, String s)
        {
            ref = s; // this is a reference, not a new string, right?
            this.index = index;
        }
        
        // compareTo() method
        public int compareTo(CircularSuffix that)
        {
            for (int i = 0; i < n; i++)
            {
                if (ref.charAt((index + i) % n) > 
                    that.ref.charAt((that.index + i) % n)) return 1;
                else if (ref.charAt((index + i) % n) <
                         that.ref.charAt((that.index + i) % n)) return -1;
            }
            return 0;
        }
    }
    
    // length of s
    public int length()                     
    {
        return n;
    }
    
    // returns index of ith sorted suffix
    public int index(int i)                 
    {
        if (i < 0 || i >= n) throw new IllegalArgumentException();
        return index[i];
    }
    
    // unit testing (required)
    public static void main(String[] args)  
    {
        String s = "ABRACADABRA!";
        CircularSuffixArray test = new CircularSuffixArray(s);
        StdOut.println(test.length());
        StdOut.println(test.index(3));
    }
}