/******************************************************************************
 *  Name:     Tim Ruszala
 *  NetID:    truszala
 *  Precept:  P04A
 *
 *  Partner Name:       N/A
 *  Partner NetID:      N/A
 *  Partner Precept:    N/A
 *
 *  Description: Provides autocomplete functionality for a given set of string 
 *               and weights, using Term and BinarySearchDeluxe.
 *
 ******************************************************************************/

import java.util.Arrays;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Autocomplete {
    
    private final Term[] a;  // holds each Term
    
    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms)
    {
        if (terms == null) throw new IllegalArgumentException();
        a = new Term[terms.length];
        for (int i = 0; i < a.length; i++)
        {
            if (terms[i] == null) throw new IllegalArgumentException();
            a[i] = terms[i];
        }       
        Arrays.sort(a);       
    }
    
    
    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix)
    {
        if (prefix == null) throw new IllegalArgumentException();
        Term pre = new Term(prefix, 0); // arbitrary weight
        int start = 
            BinarySearchDeluxe
            .firstIndexOf(a, pre, Term.byPrefixOrder(prefix.length()));
        int fin = 
            BinarySearchDeluxe
            .lastIndexOf(a, pre, Term.byPrefixOrder(prefix.length()));
        
        if (start == -1) return new Term[0];
        Term[] matches = Arrays.copyOfRange(a, start, fin + 1);
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }
    
    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix)
    {
        if (prefix == null) throw new IllegalArgumentException();
        Term pre = new Term(prefix, 0); // arbitrary weight
        int start = 
            BinarySearchDeluxe
            .firstIndexOf(a, pre, Term.byPrefixOrder(prefix.length()));
        int fin = 
            BinarySearchDeluxe
            .lastIndexOf(a, pre, Term.byPrefixOrder(prefix.length()));
        
        if (start == -1) return 0;
        else return fin + 1 - start;
    }
    
    // unit testing (required)
    public static void main(String[] args) {
        
        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }
        
        // read in queries from standard input and print out the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }
}