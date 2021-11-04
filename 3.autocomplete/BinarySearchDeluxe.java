/******************************************************************************
 *  Name:     Tim Ruszala
 *  NetID:    truszala
 *  Precept:  P04A
 *
 *  Partner Name:       N/A
 *  Partner NetID:      N/A
 *  Partner Precept:    N/A
 *
 *  Description: Altered binary search which allows clients to find the first
 *               or last of a set of identical keys in an array. Requires that
 *               the array be sorted.
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;

public class BinarySearchDeluxe {
    
    // Returns the index of the first key in a[] that equals the search key,
    // or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key,
                                         Comparator<Key> comparator)
    {
        if (a == null) throw new java.lang.IllegalArgumentException();
        if (key == null) throw new java.lang.IllegalArgumentException();
        if (comparator == null) throw new java.lang.IllegalArgumentException();
        
        if (a.length == 0) return -1; // for an empty array
        
        // altered BinarySearch.java code from algs4 booksite. Finds any
        // instance of the key.
        int lo = 0;
        int hi = a.length - 1;
        int mid = 0; // arbitrary initialization of mid to please java
        int comp = 0;
        
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            mid = lo + (hi - lo) / 2;
            comp = comparator.compare(key, a[mid]);
            if (comp < 0) 
            {
                hi = mid - 1;               
            }          
            else if (comp > 0)
            {
                lo = mid + 1;               
            }
            else break; // here either mid == key or key is not in array
        }
        
        // if key not in array, return -1       
        if (comp != 0)
        {
            return -1;            
        }
        
        // sets up next round
        hi = mid - 1;      
        while (lo < hi) {
            // checks if there is an earlier instance of the key
            mid = lo + (hi - lo) / 2;
            comp = comparator.compare(key, a[mid]);
            if (comp == 0)
            {
                hi = mid - 1;               
            }
            else lo = mid + 1;           
        }
        // when we break from above loop mid is not updated
        
        if (lo > hi) return hi + 1; // special case
        else if (comparator.compare(key, a[hi]) == 0) return hi;
        else return hi + 1;        
    }
    
    // Returns the index of the last key in a[] that equals the search key,
    // or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key,
                                        Comparator<Key> comparator)
    {   
        if (a == null) throw new java.lang.IllegalArgumentException();
        if (key == null) throw new java.lang.IllegalArgumentException();
        if (comparator == null) throw new java.lang.IllegalArgumentException();
        
        if (a.length == 0) return -1; // for an empty array
        
        // altered BinarySearch.java code from algs4 booksite. Finds an
        // instance of the key.
        int lo = 0;
        int hi = a.length - 1;
        int mid = 0; // arbitrary initialization of mid to please java
        int comp = 0;
        
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            mid = lo + (hi - lo) / 2;
            comp = comparator.compare(key, a[mid]);
            if (comp < 0) hi = mid - 1;
            else if (comp > 0) lo = mid + 1;
            else break; // here either mid == key or key is not in array
        }
        
        // if key not in array, return -1
        if (comp != 0) return -1;
        
        // sets up next round
        lo = mid + 1;         
        while (lo < hi) {
            // checks if there is a later instance of the key
            mid = lo + (hi - lo) / 2;
            comp = comparator.compare(key, a[mid]);
            if (comp == 0) lo = mid + 1;
            else hi = mid - 1;           
        }
        // when we break from above loop mid is not updated
        
        // Below, lo == hi or lo - 1 == hi
        assert (lo >= hi);
        if (lo > hi) return lo - 1; // special case
        else if (comparator.compare(key, a[lo]) == 0) return lo; 
        else return lo - 1;         
    }
    
    // unit testing (required)
    public static void main(String[] args)   
    {
        Term test1 = new Term("bleghs", 37);
        Term test2 = new Term("gruszka", 234);
        Term test3 = new Term("xx", 3245345);
        Term test4 = new Term("WhereIsMySuperSuit?", 3);
        Term test5 = new Term("bleghs", 786923);
        Term test6 = new Term("bleghs", 34576);
        Term test7 = new Term("bleghs", 343453495);
        Term test8 = new Term("blegh", 45384);
        Term test9 = new Term("blegh", 45384);
        
        Term[] a = {test1, test2, test3, test4, test5, test6, test7, test8, 
            test9};     
        
        
        StdOut.println("By 2-char prefix");
        StdOut.println("----------");
        Arrays.sort(a, Term.byPrefixOrder(2));
        for (int i = 0; i < a.length; i++)
            StdOut.println(a[i]);
        StdOut.println();
        
        
        StdOut.println(firstIndexOf(a, test7, Term.byPrefixOrder(2)));
        StdOut.println(lastIndexOf(a, test7, Term.byPrefixOrder(2)));
        
        int n = 10000;
        Term[] b = new Term[n];
        
        for (int i = 0; i < n; i++)
            b[i] = new Term("egg", 123);
        
        StdOut.println(firstIndexOf(b, b[4456], Term.byPrefixOrder(3)));
        
    }
    
}