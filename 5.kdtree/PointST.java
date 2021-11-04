/******************************************************************************
  *  Name:     Tim Ruszala
  *  NetID:    truszala
  *  Precept:  P04A
  *
  *  Partner Name:       N/A
  *  Partner NetID:      N/A
  *  Partner Precept:    N/A
  *
  *  Description: A symbol table whose keys are two-dimensional points.
  *  Implements brute-force range search and nearest-neighbor search.
  *
  ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class PointST<Value>
{
    // holds size of tree
    private int n;
    // holds Point2Ds in tree
    private final RedBlackBST<Point2D, Value> tree;
    
    // construct an empty symbol table of points
    public PointST()         
    {
        tree = new RedBlackBST<Point2D, Value>();
        n = 0;
    }
    
    // is the symbol table empty?
    public boolean isEmpty()  
    {
        return (n <= 0);
    }
    
    // number of points 
    public int size() 
    {
        return n;
    }
    
    // associate the value val with point p
    public void put(Point2D p, Value val)   
    {
        if (p == null) throw new java.lang.IllegalArgumentException();
        if (val == null) throw new java.lang.IllegalArgumentException();
        if (!tree.contains(p)) n++;
        tree.put(p, val);
    }
    
    // value associated with point p 
    public Value get(Point2D p)        
    {
        if (p == null) throw new java.lang.IllegalArgumentException();
        return tree.get(p);
    }
    
    // does the symbol table contain point p? 
    public boolean contains(Point2D p)  
    {       
        if (p == null) throw new java.lang.IllegalArgumentException();
        return tree.contains(p);
    }
    // all points in the symbol table 
    public Iterable<Point2D> points()    
    {
        return tree.keys();
    }
    
    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect)        
    {
        if (rect == null) throw new java.lang.IllegalArgumentException();
        Stack<Point2D> stack = new Stack<Point2D>();       
        for (Point2D p : tree.keys())
        {
            if (rect.contains(p)) stack.push(p);
        }       
        return stack;
    }
    
    // a nearest neighbor of point p; null if the symbol table is empty 
    public Point2D nearest(Point2D p)    
    {
        if (p == null) throw new java.lang.IllegalArgumentException();
        if (tree.isEmpty()) return null;
        double near = Double.POSITIVE_INFINITY;
        Point2D currentNearest = null;
        for (Point2D q : tree.keys())
        {
            if (q != p)
            {
                double distance = p.distanceSquaredTo(q);
                if (distance < near) 
                {
                    near = distance;
                    currentNearest = q;
                }
            }
        }
        return currentNearest;
    }
    
    // unit testing (required)
    public static void main(String[] args)  
    {
        PointST<String> test = new PointST<String>();
        
        StdOut.println("ST empty.");
        StdOut.println("isEmpty() = " + test.isEmpty());
        StdOut.println("size() = " + test.size());
        
        Point2D a = new Point2D(0, 0);
        Point2D b = new Point2D(1, 1);
        Point2D c = new Point2D(-2, 2);
        Point2D d = new Point2D(0, -3);
        Point2D e = new Point2D(0.5, -10);
        
        test.put(a, "ay");
        test.put(b, "bee");
        test.put(c, "cee");
        test.put(d, "dee");
        test.put(e, "ee");
        
        StdOut.println("ST has 5 elements.");
        StdOut.println("isEmpty() = " + test.isEmpty());
        StdOut.println("size() = " + test.size());
        
        for (Point2D p : test.points())
        {
            StdOut.println(p);
        }
        
        StdOut.println("get(a) = " + test.get(a));
        StdOut.println("get(b) = " + test.get(b));
        StdOut.println("get(c) = " + test.get(c));
        StdOut.println("get(d) = " + test.get(d));
        StdOut.println("get(e) = " + test.get(e));
        StdOut.println("contains(d) = " + test.contains(d));
        
        RectHV rect = new RectHV(-2, -1, 10, 10);
        StdOut.println("RANGE!!!");
        for (Point2D p : test.range(rect))
            StdOut.println(p);
        
        Point2D testPt = new Point2D(0, -10);
        StdOut.println("nearest(testPt) = " + test.nearest(testPt));
    }
}