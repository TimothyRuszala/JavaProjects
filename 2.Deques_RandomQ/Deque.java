/******************************************************************************
  *  Name:             Timothy Ruszala
  *  NetID:            truszala
  *  Precept:          P04A
  *
  *  Partner Name:     N/A
  *  Partner NetID:    N/A 
  *  Partner Precept:  N/A 
  * 
  * Description: Creates the Deque, an abstract data type in which elements
  * may be either added or removed from both the front and back of the data
  * type.
  *
  ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    
    private Node first; // first node in the linked list
    private Node last;  // last node in the linked list
    private int count;  // number of elements in the Deque
    
    private class Node
    {
        Item item;      // the node's data element
        Node next;      // the next node in the sequence (towards "last")
        Node prev;      // the previous node in the sequence (towards "first")
    }
    
    // construct an empty deque
    public Deque() 
    {     
        count = 0;
    }
    
    // is the deque empty?
    public boolean isEmpty()   
    {
        return count == 0;
    }             
    
    // return the number of items on the deque
    public int size()        
    {
        return count;
    }    
    
    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null) throw new IllegalArgumentException();
        if (isEmpty()) 
        {
            first = new Node();
            last = first;
            first.item = item;
            count++;
        }
        else 
        {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            oldFirst.prev = first;
            count++;
        }
    }        
    
    // add the item to the end
    public void addLast(Item item)  
    {
        if (item == null) throw new IllegalArgumentException();
        if (isEmpty()) {
            last = new Node();
            first = last;
            last.item = item;
            count++;
        }
        else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.prev = oldLast; 
            oldLast.next = last;           
            count++;
        }       
    }       
    
    // remove and return the item from the front
    public Item removeFirst()      
    {
        if (isEmpty()) throw new NoSuchElementException();
        
        Item item = first.item;
        if (size() == 1)
        {
            first = null;
            last = null;
        }
        else
        {
            first = first.next;
            first.prev = null;
        }
        count--;
        return item;
    }     
    
    // remove and return the item from the end
    public Item removeLast()     
    {
        if (isEmpty()) throw new NoSuchElementException();
        
        Item item = last.item;
        if (size() == 1)
        {
            last = null;
            first = null;
        }
        else
        {
            last = last.prev;
            last.next = null;
        }
        count--;
        return item;
    }    
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator()    
    {
        return new ListIterator();
    }     
    
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first; // Which node we're looking at
        
        public boolean hasNext() { return current != null; }
        public void remove()     { throw new UnsupportedOperationException(); }
        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current   = current.next;
            return item;
        }       
    }
    
    // unit testing (required)
    public static void main(String[] args)   
    {
        Deque<Integer> deque = new Deque<Integer>();
        StdOut.println("isEmpty() = " + deque.isEmpty());
        StdOut.println("size() = " + deque.size());
        
        // "Check all submitted files" slightly altered failed case
        deque.addLast(0);
        deque.addLast(1);
        deque.addFirst(2);
        StdOut.println(deque.removeLast());  
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.isEmpty());        
        StdOut.println("isEmpty() = " + deque.isEmpty());
        StdOut.println("size() = " + deque.size());
        deque.addLast(4);
        deque.removeLast();
        
        
        // "Check all submitted files" failed case
        deque.addFirst(0);
        StdOut.println("size() = " + deque.size());            
        deque.addFirst(2);
        deque.removeFirst();    
        deque.removeLast();   
        StdOut.println("size() = " + deque.size());
        deque.addFirst(6);
        deque.addFirst(7);
        deque.addFirst(8);
        StdOut.println(deque.removeLast());
        
        // "Check all submitted files" failed case
        Deque<Integer> beque = new Deque<Integer>();
        beque.addLast(1);
        beque.removeFirst(); 
        beque.addLast(3);
        beque.removeFirst(); 
        beque.addFirst(5);
        beque.addLast(6);
        beque.removeLast();  
        beque.removeLast();   
        for (Integer i : beque)
            StdOut.print(i + " ");
        StdOut.println();
    }
}
