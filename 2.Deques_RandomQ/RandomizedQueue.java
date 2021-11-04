/******************************************************************************
 *  Name:             Timothy Ruszala
 *  NetID:            truszala
 *  Precept:          P04A
 *
 *  Partner Name:     N/A
 *  Partner NetID:    N/A 
 *  Partner Precept:  N/A 
 * 
 * Description: Creates the Randomized Queue, a data type in which the item
 * dequeued is chosen at random from the queue's contents.
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> 
{
    
    private int n;     // number of elements in Randomized Queue
    private Item[] a;  // holds elements of the Randomized Queue
    
    // construct an empty randomized queue
    public RandomizedQueue()   
    {
        a = (Item[]) new Object[2];
        n = 0;
    }
    
    // is the randomized queue empty?
    public boolean isEmpty()             
    {
        return (n == 0);
    }
    
    // return the number of items on the randomized queue
    public int size()     
    {
        return n;
    }
    
    // add the item
    public void enqueue(Item item)  
    {
        if (item == null) throw new IllegalArgumentException();
        if (n == a.length) resize(a.length * 2);
        a[n] = item;
        n++;   
    }
    // remove and return a random item
    public Item dequeue()      
    {
        if (isEmpty()) throw new NoSuchElementException();
        int r = StdRandom.uniform(n);
        Item item = a[r];
        n--;
        a[r] = a[n];
        a[n] = null;
        return item;
    }
    // return a random item (but do not remove it)
    public Item sample()
    {
        if (isEmpty()) throw new NoSuchElementException();
        int r = StdRandom.uniform(n);
        Item item = a[r];
        return item;
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator()   
    {
        return new ArrayIterator();
    }     
    
    // independent iterator which iterates through the elements in random order
    private class ArrayIterator implements Iterator<Item>
    {
        // holds consolidated and shuffled array a
        private final Item[] it;        
        private int current;            // current array index
        
        // consolidates and shuffles array a into array it for random iteration
        public ArrayIterator()
        {
            int itIndex = 0;
            current = 0;
            it = (Item[]) new Object[n];
            for (int i = 0; i < n; i++)
            {
                if (a[i] != null)
                {
                    it[itIndex] = a[i];
                    itIndex++;
                }
            } 
            StdRandom.shuffle(it);
        }
        
        public boolean hasNext()
        {
            return (current < n);
        }
        
        public void remove() { throw new UnsupportedOperationException(); }
        
        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException();
            return it[current++];         
        }
    }
    
    // resizes the array
    private void resize(int capacity) {
        if (n > capacity) throw new IllegalArgumentException();
        
        Item[] temp = (Item[]) new Object[capacity];
        int tempIndex = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != null)
            {
                temp[tempIndex] = a[i];
                tempIndex++;
            }           
        }
        a = temp;
    }
    
    // unit testing (required)
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        StdOut.println("initial isEmpty() = " + test.isEmpty());
        StdOut.println("initial size() = " + test.size());
        for (int i = 0; i < 33; i++)
        {
            test.enqueue(i);
        }
        StdOut.println("filled isEmpty() = " + test.isEmpty());
        StdOut.println("filled size() = " + test.size());
        StdOut.println("sample() = " + test.sample());
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
        StdOut.println();
        StdOut.println("final size() = " + test.size());
        
        for (int i : test)
            if ((i % 5) >= 4) StdOut.print(i + " ");
        StdOut.println();
        
        
        
        
    }
}