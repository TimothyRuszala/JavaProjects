/******************************************************************************
 *  Name:             Timothy Ruszala
 *  NetID:            truszala
 *  Precept:          P04A
 *
 *  Partner Name:     N/A
 *  Partner NetID:    N/A 
 *  Partner Precept:  N/A 
 * 
 * Description: Client for RandomizedQueue. Stores all strings from StdIn in a
 * RandomizedQueue and prints k random strings.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation
{   
    // See Description above
    public static void main(String[] args)
    {        
      int k = Integer.parseInt(args[0]);
      RandomizedQueue<String> perm = new RandomizedQueue<String>();
      while (!StdIn.isEmpty())
          perm.enqueue(StdIn.readString());
      for (int i = 0; i < k; i++)
          StdOut.println(perm.dequeue() + " ");
    }
}