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
  *  Implements efficient range search and nearest-neighbor search.
  *
  ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class KdTreeST<Value>
{
    // holds size of tree
    private int n;
    // root of tree
    private Node root; 
    
    // Node which holds the Point2D key, its value, its governing rectangle,
    // and its children.
    private class Node 
    {   
        // the point
        private Point2D p;   
        
        // the symbol table maps the point to this value
        private Value value;    
        
        // the axis-aligned rectangle corresponding to this node
        private final RectHV rect;  
        
        // the left/bottom subtree
        private Node lb;    
        
        // the right/top subtree
        private Node rt;        
        
        // constructs a new node
        public Node(Point2D key, Value val, RectHV rectangle)
        {
            p = key; 
            value = val; 
            rect = rectangle;
        }
    }
    
    // construct an empty symbol table of points
    public KdTreeST()         
    {
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
    public void put(Point2D key, Value val)   
    {
        if (key == null || val == null) throw new IllegalArgumentException();
        if (!contains(key)) n++;
        root = put(root, key, val, false); 
    }
    
    // private helper method to put (above); "false" orientation means vertical
    // splitting line.
    private Node put(Node x, Point2D key, Value val, boolean orientation)
    {
        // Creating the root
        if (x == null)
        {               
            RectHV rect = new RectHV(Double.NEGATIVE_INFINITY,
                                     Double.NEGATIVE_INFINITY,
                                     Double.POSITIVE_INFINITY,
                                     Double.POSITIVE_INFINITY);
            return new Node(key, val, rect);             
        }
        
        if (!orientation)
        {
            int cmp = Double.compare(key.x(), x.p.x());
            if (cmp < 0)
            {
                if (x.lb != null) x.lb = put(x.lb, key, val, true);
                else x.lb = create(x, key, val, orientation);
            }          
            else
            {
                if (key.equals(x.p))
                {
                    x.p = key;
                    x.value = val;
                }
                else if (x.rt != null) x.rt = put(x.rt, key, val, true);
                else x.rt = create(x, key, val, orientation);
            }    
        }
        else
        {
            int cmp = Double.compare(key.y(), x.p.y());
            if (cmp < 0)
            {
                if (x.lb != null) x.lb = put(x.lb, key, val, false);              
                else x.lb = create(x, key, val, orientation);
            }
            else
            {
                if (key.equals(x.p))
                {
                    x.p = key;
                    x.value = val;
                }    
                else if (x.rt != null) x.rt = put(x.rt, key, val, false);
                else x.rt = create(x, key, val, orientation);
            }           
        }
        return x;
    }
    
    // private helper method for put
    private Node create(Node x, Point2D key, Value val, boolean orientation)
    {
        RectHV rect;
        if (!orientation)
        {
            int cmp = Double.compare(key.x(), x.p.x());
            if (cmp < 0)
                rect = new RectHV(x.rect.xmin(), x.rect.ymin(),
                                  x.p.x(), x.rect.ymax());
            else rect = new RectHV(x.p.x(), x.rect.ymin(),
                                   x.rect.xmax(), x.rect.ymax());
        }
        else
        {
            int cmp = Double.compare(key.y(), x.p.y());
            if (cmp < 0)
                rect = new RectHV(x.rect.xmin(), x.rect.ymin(),
                                  x.rect.xmax(), x.p.y());
            else rect = new RectHV(x.rect.xmin(), x.p.y(),
                                   x.rect.xmax(), x.rect.ymax());
        }           
        return new Node(key, val, rect);
    }
    
    // value associated with point p 
    public Value get(Point2D p)        
    {
        return get(root, p, false);
    }
    
    // private helper method for get (above); "false" orientation means vertical
    // splitting line.
    private Value get(Node x, Point2D p, boolean orientation)
    {
        if (p == null) throw new IllegalArgumentException();
        if (x == null) return null;
        if (!orientation)
        {
            int cmp = Double.compare(p.x(), x.p.x());
            if (cmp < 0) return get(x.lb, p, true);
            else
            {
                if (p.equals(x.p)) return x.value;
                return get(x.rt, p, true);
            }
        }       
        else
        {
            int cmp = Double.compare(p.y(), x.p.y());
            if (cmp < 0) return get(x.lb, p, false);
            else
            {
                if (p.equals(x.p)) return x.value;
                return get(x.rt, p, false);
            }
        }
    }
    
    // does the symbol table contain point p? 
    public boolean contains(Point2D p)  
    {       
        if (p == null) throw new IllegalArgumentException();
        return get(p) != null;
    }
    
    // all points in the symbol table 
    public Iterable<Point2D> points() 
    {
        Queue<Point2D> queue = new Queue<Point2D>();
        Queue<Node> helperQ = new Queue<Node>();
        if (isEmpty()) return queue;
        Node current = root;
        while (queue.size() < size())
        {
            queue.enqueue(current.p);
            if (current.lb != null) helperQ.enqueue(current.lb);
            if (current.rt != null) helperQ.enqueue(current.rt);
            if (!helperQ.isEmpty()) current = helperQ.dequeue();
        }
        return queue;
    }
    
    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect)        
    {
        if (rect == null) throw new IllegalArgumentException();
        Stack<Point2D> stack = new Stack<Point2D>();
        if (!isEmpty()) range(rect, root, stack);                
        return stack;
    }
    
    // helper method to range (above)
    private void range(RectHV r, Node x, Stack<Point2D> stack)
    {
        if (r.contains(x.p)) stack.push(x.p);
        
        if (x.lb != null && r.intersects(x.lb.rect))
            range(r, x.lb, stack);
        if (x.rt != null && r.intersects(x.rt.rect))
            range(r, x.rt, stack);           
    }   
    
    // a nearest neighbor of point p; null if the symbol table is empty 
    public Point2D nearest(Point2D p)    
    {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        return nearest(p, root.p, root, false);
    }
    
    // helper method to nearest (above)
    private Point2D nearest(Point2D p, Point2D currentNearest, 
                            Node x, boolean orientation)
    {
        
        if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(currentNearest))
            currentNearest = x.p;
        
        if (isLB(p, x, orientation))
        {
            if (x.lb != null)
                currentNearest = nearest(p, currentNearest, x.lb, !orientation);
            if (x.rt != null &&
                x.rt.rect.distanceSquaredTo(p) < 
                p.distanceSquaredTo(currentNearest))
                currentNearest = nearest(p, currentNearest, x.rt, !orientation);
        }
        else
        {
            if (x.rt != null)
                currentNearest = nearest(p, currentNearest, x.rt, !orientation);
            if (x.lb != null &&
                x.lb.rect.distanceSquaredTo(p) < 
                p.distanceSquaredTo(currentNearest))
                currentNearest = nearest(p, currentNearest, x.lb, !orientation);
        }
        return currentNearest;
    }
    
    // private helper method. Tells which side of the dividing line point
    // p lies on given point p, the current node x, and the current
    // orientation, where "false" indicates a vertical splitting line.
    private boolean isLB(Point2D p, Node x, boolean orientation)
    {
        if (!orientation)
            return Double.compare(x.p.x(), p.x()) > 0;
        else
            return Double.compare(x.p.y(), p.y()) > 0;
    }
    
    // unit testing (required)
    public static void main(String[] args)
    {
        KdTreeST<String> test = new KdTreeST<String>();
        StdOut.println("Empty ST is empty? " + test.isEmpty());
        StdOut.println("Empty ST size = " + test.size());
        
//        Point2D a = new Point2D(0.7, 0.2);
//        Point2D b = new Point2D(0.5, 0.4);
//        Point2D c = new Point2D(0.2, 0.3);
//        Point2D d = new Point2D(0.4, 0.7);
//        Point2D e = new Point2D(0.9, 0.6);
        
        Point2D a = new Point2D(0.372, 0.497);
        Point2D b = new Point2D(0.564, 0.413);
        Point2D c = new Point2D(0.226, 0.577);
        Point2D d = new Point2D(0.144, 0.179);
        Point2D e = new Point2D(0.083, 0.510);
        Point2D f = new Point2D(0.320, 0.708);
        Point2D g = new Point2D(0.417, 0.362);
        Point2D h = new Point2D(0.862, 0.825);
        Point2D i = new Point2D(0.785, 0.725);
        Point2D j = new Point2D(0.499, 0.208);
        
        test.put(a, "ay");
        test.put(b, "bee");
        test.put(c, "cee");
        test.put(d, "dee");
        test.put(e, "ee");
        test.put(f, "fee");
        test.put(g, "gee");
        test.put(h, "hee");
        test.put(i, "iy");
        test.put(j, "jee");
        
        StdOut.println("fuller ST size = " + test.size());
        
        StdOut.println("get(a) = " + test.get(a));
        StdOut.println("get(b) = " + test.get(b));
        StdOut.println("get(c) = " + test.get(c));
        StdOut.println("get(d) = " + test.get(d));
        StdOut.println("get(e) = " + test.get(e));
        StdOut.println("contains(d) = " + test.contains(d));
        
        RectHV rect = new RectHV(-2, -11, 0.7, 0.7);
        StdOut.println("RANGE!!!");
        for (Point2D p : test.range(rect))
            StdOut.println(p);
        
        StdOut.println(test.size());
        for (Point2D p : test.points())
        {
            StdOut.println(p);
        }
        
        Point2D testPt = new Point2D(0.12, 0.13);
        StdOut.println("nearest(testPt) = " + test.nearest(testPt));
    }
}