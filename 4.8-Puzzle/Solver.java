/******************************************************************************
  *  Name:     Tim Ruszala
  *  NetID:    truszala
  *  Precept:  P04A
  *
  *  Partner Name:       N/A
  *  Partner NetID:      N/A
  *  Partner Precept:    N/A
  *
  *  Description: Uses the board data type
  *
  *****************************************************************************/

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Solver {
    
    // holds the final (goal) SearchNode- used to trace the solution steps
    private final SearchNode end;    
    
    
    private static class SearchNode implements Comparable<SearchNode>
    {
        // the Board in question
        private final Board b;
        
        // which Board did this Board come from?
        private final SearchNode parent;
        
        // how many moves did it take to get to this SearchNode?
        private final int moves;
        
        // holds the priority of the Board (manhattan() + moves)
        private final int priority;
        
        // Creates a wrapper for Board which allows for the implementation
        // of a compareTo() function, as well as for keeping track of the
        // Board's parent and its priority.
        public SearchNode(Board b, SearchNode parent)
        {
            this.b = b;
            this.parent = parent;
            if (this.parent == null) moves = 0;
            else moves = parent.moves + 1;
            priority = b.manhattan() + moves;
        }
        
        // compares two SearchNodes on the basis of priority.
        public int compareTo(SearchNode s)
        {
            if (priority > s.priority) return 1;
            else if (priority < s.priority) return -1;
            else return 0;
        }
        
        // returns the SearchNode as a string by returning the string
        // representation of its board.
        public String toString()
        {
            return b.toString();
        }
    }
    
    
    
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)   
    {
        if (initial == null) 
            throw new java.lang.IllegalArgumentException();
        if (!initial.isSolvable()) 
            throw new java.lang.IllegalArgumentException();
        SearchNode current = new SearchNode(initial, null);
        
        // stores SearchNodes for use in the A* Search algorithm. The minimum
        // value is defined as the search node with the lowest priority.
        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        
        while (!current.b.isGoal())
        {     
            for (Board b : current.b.neighbors())
            {
                SearchNode s = new SearchNode(b, current);  
                // critical optimization
                if (current.parent == null || !s.b.equals(current.parent.b))
                {
                    minPQ.insert(s);    
                }                
            }
            current = minPQ.delMin();
        }
        end = current;
    }
    
    
    
    
    
    
    // min number of moves to solve initial board
    public int moves()
    {     
        return end.moves;
    }
    
    // sequence of boards in a shortest solution
    public Iterable<Board> solution()  
    {       
        Stack<Board> stack = new Stack<Board>();
        SearchNode check = end;
        
        while (check != null)
        {
            stack.push(check.b);
            check = check.parent;
        }
        
        return stack;
    }  
    
    // test client
    public static void main(String[] args)   
    {           
        String file = args[0];
        In in = new In(file);
        int n = in.readInt();
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                a[i][j] = in.readInt();
            }
        }
        
        Board initial = new Board(a);
        if (!initial.isSolvable()) StdOut.println("Unsolvable puzzle");
        else
        {
            Solver solver = new Solver(initial);
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board b : solver.solution())
            {
                StdOut.println(b.toString());
            }    
        }
    }
}