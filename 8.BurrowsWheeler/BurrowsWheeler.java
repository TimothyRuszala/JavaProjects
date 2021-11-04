/******************************************************************************
 *  Name:     Tim Ruszala
 *  NetID:    truszala
 *  Precept:  P04A
 *
 *  Partner Name:       N/A
 *  Partner NetID:      N/A
 *  Partner Precept:    N/A
 *
 *  Description: Does the Burrows-Wheeler transformation on a given input
 *  string. An intermediary step for data compression.
 *
 ******************************************************************************/


import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler 
{
    private static final int R = 256; // ASCII size
    
    // apply Burrows-Wheeler transform, 
    // reading from standard input and writing to standard output
    public static void transform()
    {
        String s = BinaryStdIn.readString();
        int n = s.length();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        
        for (int i = 0; i < n; i++)
        {
            if (csa.index(i) == 0)
            {
                int first = i;
                BinaryStdOut.write(first);
                break;
            }
        }
        
        for (int i = 0; i < n; i++)
        {
            int index = csa.index(i);
            BinaryStdOut.write(s.charAt((index + n - 1) % n));
        }      
        BinaryStdOut.flush();
    }
    
    // apply Burrows-Wheeler inverse transform, 
    // reading from standard input and writing to standard output
    public static void inverseTransform()
    {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        char[] t = s.toCharArray();
        int n = t.length;
        
        char[] sortedT = new char[n];
        int[] next = sortT(n, t, sortedT);
        
        int current = first;
        for (int i = 0; i < n; i++)
        {
            BinaryStdOut.write(sortedT[current]);
            current = next[current];
        }
        
        BinaryStdOut.flush();
    }
    
    // simultaneously creates the sortedT array and initializes index[].
    private static int[] sortT(int n, char[] t, char[] sortedT)
    {
        // compute frequency counts
        int[] next = new int[n];
        int[] count = new int[R+1];
        for (int i = 0; i < n; i++)
            count[t[i] + 1]++;
        
        // compute cumulates
        for (int r = 0; r < R; r++)
            count[r+1] += count[r];
        
        // move data
        for (int i = 0; i < n; i++)
        {
            next[count[t[i]]] = i;
            sortedT[count[t[i]]++] = t[i];
        }
        
        return next;
    }
    
    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args)
    {
        char c = args[0].charAt(0);
        if (c == '+')
        {
            inverseTransform();
        }
        
        else if (c == '-')
        {
            transform();
        }
    }
}