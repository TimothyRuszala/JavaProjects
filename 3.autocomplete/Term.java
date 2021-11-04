/******************************************************************************
  *  Name:     Tim Ruszala
  *  NetID:    truszala
  *  Precept:  P04A
  *
  *  Partner Name:       N/A
  *  Partner NetID:      N/A
  *  Partner Precept:    N/A
  *
  *  Description: Term data type which consists of a String query and a Long
  *               weight associated with that query. Implements Comparable and
  *               Comparators, and can be sorted lexicographically, by 
  *               descending weight, or lexicographically by the first r
  *               characters.
  *
  ******************************************************************************/

import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;

public class Term implements Comparable<Term> {
    
// word
    private final String query;   
    // weight value
    private final long weight;  
    
    
    
    
    // Initializes a term with the given query string and weight.
    public Term(String query, long weight)
    {
        if (query == null) throw new java.lang.IllegalArgumentException();
        if (weight < 0) throw new java.lang.IllegalArgumentException();
        this.query = query;
        this.weight = weight;
    }
    
    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder()
    {
        return new ByReverseWeightOrder(); // Note: +1 means term is smaller
    }
    
    // Compares the two terms in lexicographic order but
    // using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r)
    {
        if (r < 0) throw new java.lang.IllegalArgumentException();
        return new ByPrefixOrder(r);
    }
    
    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that)
    {
        return query.compareTo(that.query);
    }
    
    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString()
    {
        return "" + weight + '\t' + query;
    }
    
    private static class ByReverseWeightOrder implements Comparator<Term>
    {
        public int compare(Term v, Term w)
        {
            if (v.weight < w.weight) return 1;
            else if (v.weight > w.weight) return -1;
            else return 0;
        }
    }
    
    private static class ByPrefixOrder implements Comparator<Term>
    {
        // prefix length
        private final int r;
        // Constructor needed to pass int r from byPrefixOrder(int r)
        private ByPrefixOrder(int pre)
        {
            r = pre;
        }
        
        public int compare(Term v, Term w)
        {          
            if (r == 0) return 0;
            
            
            int n = Math.min(r, Math.min(v.query.length(), w.query.length()));
            int i = 0;
            while (i < n)
            {             
                if (v.query.charAt(i) > w.query.charAt(i)) return 1;
                else if (v.query.charAt(i) < w.query.charAt(i)) return -1;
                else i++;
            }
            if (n == r) return 0;
            else if (v.query.length() == w.query.length()) return 0;
            else if (n == v.query.length()) return -1;
            else return 1;
        }
    }
    
    // unit testing (required)
    public static void main(String[] args)  
    {
        Term test1 = new Term("bleghs", 37);
        Term test2 = new Term("gruszka", 234);
        Term test3 = new Term("xxi", 3245345);
        Term test4 = new Term("WhereIsMySuperSuit?", 3);
        Term test5 = new Term("xx", 786923);
        Term test6 = new Term("groszy", 34576);
        Term test7 = new Term("bleghs", 343453495);
        Term test8 = new Term("ble", 45384);
        
        Term[] a = {test1, test2, test3, test4, test5, test6, test7, test8};
               
        StdOut.println("toString() test: " + test1.toString());
        StdOut.println("compareTo() test: " 
                           + test1.compareTo(test2));
        
        // sort by reverse weight order and print results
        StdOut.println("By descending weight");
        StdOut.println("----------");
        Arrays.sort(a, Term.byReverseWeightOrder());
        for (int i = 0; i < a.length; i++)
            StdOut.println(a[i]);
        StdOut.println();      
        
        // now, sort by prefix order and print results
        StdOut.println("By 4-char prefix");
        StdOut.println("----------");
        Arrays.sort(a, Term.byPrefixOrder(4));
        for (int i = 0; i < a.length; i++)
            StdOut.println(a[i]);
        StdOut.println();
        
        Term p = new Term("TCC", 3);
        Term q = new Term("TCC", 7);
        Comparator<Term> comparator = byPrefixOrder(10);
        StdOut.println("compare() = " + comparator.compare(p, q));
            
    }
}

