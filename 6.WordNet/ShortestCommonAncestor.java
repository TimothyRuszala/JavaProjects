/******************************************************************************
  *  Name:     Tim Ruszala
  *  NetID:    truszala
  *  Precept:  P04A
  *
  *  Partner Name:       N/A
  *  Partner NetID:      N/A
  *  Partner Precept:    N/A
  *
  *  Description: A program which determines the common ancestor of two sets
  *  of vertices for a given input digraph.
  *
  *****************************************************************************/

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class ShortestCommonAncestor
{
    
    // holds the input digraph
    private final Digraph dag;
    
    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G)
    {
        if (G == null) throw new IllegalArgumentException();     
        
        // DAG test
        Topological tester = new Topological(G);
        if (tester.order() == null) throw new IllegalArgumentException();
        
        // rooted test
        int rootCount = 0;
        for (int i = 0; i < G.V(); i++)
        {
            if (G.outdegree(i) == 0) rootCount++;
        }
        if (rootCount > 1) throw new IllegalArgumentException();          
        
        dag = new Digraph(G);
    }
    
    // length of shortest ancestral path between v and w
    public int length(int v, int w)
    {
        if (v < 0 || w < 0) 
            throw new IllegalArgumentException();
        if (v > dag.V() - 1 || w > dag.V() - 1) 
            throw new IllegalArgumentException();
        
        Queue<Integer> s1 = new Queue<Integer>();
        Queue<Integer> s2 = new Queue<Integer>();
        s1.enqueue(v);
        s2.enqueue(w);
        DoubleBFS bfs = new DoubleBFS(dag, s1, s2);
        return bfs.shortestPathLength();
    }
    
    
    
    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w)
    {
        if (v < 0 || w < 0) 
            throw new IllegalArgumentException();
        if (v > dag.V() - 1 || w > dag.V() - 1) 
            throw new IllegalArgumentException();
        Queue<Integer> s1 = new Queue<Integer>();
        Queue<Integer> s2 = new Queue<Integer>();
        s1.enqueue(v);
        s2.enqueue(w);
        DoubleBFS bfs = new DoubleBFS(dag, s1, s2);
        return bfs.shortestAncestor();
    }
    
    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB)
    {
        if (subsetA == null || subsetB == null) 
            throw new IllegalArgumentException();
        int count = 0;
        for (Integer i : subsetA)
        {
            count++;
            
            if (i == null || i < 0 || i > dag.V() - 1) 
                throw new IllegalArgumentException();          
        }
        if (count == 0) throw new IllegalArgumentException();
        count = 0;
        for (Integer i : subsetB)
        {
            count++;
            if (i == null || i < 0 || i > dag.V() - 1)
                throw new IllegalArgumentException();
        }
        if (count == 0) throw new IllegalArgumentException();

        DoubleBFS bfs = new DoubleBFS(dag, subsetA, subsetB);
        return bfs.shortestPathLength();      
    }
    
    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, 
                              Iterable<Integer> subsetB)
    {
        if (subsetA == null || subsetB == null) 
            throw new IllegalArgumentException();
        int count = 0;
        for (Integer i : subsetA)
        {
            count++;
            if (i == null || i < 0 || i > dag.V() - 1) 
                throw new IllegalArgumentException();
        }
        if (count == 0) throw new IllegalArgumentException();
        count = 0;
        for (Integer i : subsetB)
        {
            count++;
            if (i == null || i < 0 || i > dag.V() - 1)
                throw new IllegalArgumentException();
        }
        if (count == 0) throw new IllegalArgumentException();
        
        DoubleBFS bfs = new DoubleBFS(dag, subsetA, subsetB);
        return bfs.shortestAncestor();
    }
    
    
    // a class which performs bfs a number of times proportional to the number
    // of query source vertices.
    private class DoubleBFS
    {
        // has this vertex been seen?
        public boolean[][] marked;
        // shortest distance to this vertex from a source vertex
        public int[][] distTo;
        // records path to this vertex from source vertex
        public int[][] edgeTo;
        // shortest ancestral path length
        private final int sapl;
        // shortest common ancestor
        private final int sca;
        
        // constructor
        public DoubleBFS(Digraph G, Iterable<Integer> s1, Iterable<Integer> s2)
        {
            marked = new boolean[G.V()][2];
            edgeTo = new int[G.V()][2];
            distTo = new int[G.V()][2];
            for (int i = 0; i < 2; i++)
            {
                for (int v = 0; v < G.V(); v++)
                    distTo[v][i] = Integer.MAX_VALUE;
            }
            
            // fills in array[][0], corresponding to s1
            Queue<Integer> q = new Queue<Integer>();
            for (int i : s1)
            {          
                distTo[i][0] = 0;
                marked[i][0] = true;
                q.enqueue(i);
            }
            
            while (!q.isEmpty()) {
                int v = q.dequeue();
                for (int w : G.adj(v)) {
                    if (!marked[w][0]) {                      
                        edgeTo[w][0] = v;
                        distTo[w][0] = distTo[v][0] + 1;
                        marked[w][0] = true;
                        q.enqueue(w);
                    }
                }
            }        
            
            // fills in array[][1]
            for (int i : s2)
            {                     
                distTo[i][1] = 0;
                marked[i][1] = true;
                q.enqueue(i);
            }
            
            while (!q.isEmpty()) {
                int v = q.dequeue();
                for (int w : G.adj(v)) {
                    if (!marked[w][1]) {
                        
                        edgeTo[w][1] = v;
                        distTo[w][1] = distTo[v][1] + 1;
                        marked[w][1] = true;
                        q.enqueue(w);
                    }
                }
            }           
            
            // calculates the sca and sapl
            int minDist = Integer.MAX_VALUE;
            int ancestor = Integer.MAX_VALUE;
            for (int i = 0; i < marked.length; i++)
            {
                if (marked[i][0] && marked[i][1])
                {
                    int dist = distTo[i][0] + distTo[i][1];     
                    if (dist < minDist)
                    {                        
                        minDist = dist;
                        ancestor = i;
                    }
                }
            }
            sapl = minDist;
            sca = ancestor;
        }
        
        // returns the shortest common ancestor of two points
        public int shortestAncestor()
        {
            return sca;
        }
        
        // returns the shortest path length between two points
        public int shortestPathLength()
        {
            return sapl;
        }      
    }   
    
// unit testing (required)
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
//        int[] s1 = { 3, 8, 10, null };
//        int[] s2 = { 1, 6, 7, 9 };
//        Queue<Integer> v = new Queue<Integer>();
//        Queue<Integer> w = new Queue<Integer>();
//        for (Integer i = 0; i < s1.length; i++)
//            q1.enqueue(s1[i]);
//        for (Integer i = 0; i < s2.length; i++)
//            q2.enqueue(s2[i]);
//        StdOut.println(sca.ancestorSubset());
//        StdOut.println(sca.lengthSubset());
        
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            Queue<Integer> a = new Queue<Integer>();
            a.enqueue(v);
            a.enqueue(w);
            Queue<Integer> b = new Queue<Integer>();
            b.enqueue(x);
            b.enqueue(y);
            int length   = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.println("distance between v and w: ");
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
            length   = sca.lengthSubset(a, b);
            ancestor = sca.ancestorSubset(a, b);
            StdOut.println("distance between [v, w] and [x, y]: ");
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}