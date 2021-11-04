/******************************************************************************
  *  Name:     Tim Ruszala
  *  NetID:    truszala
  *  Precept:  P04A
  *
  *  Partner Name:       N/A
  *  Partner NetID:      N/A
  *  Partner Precept:    N/A
  *
  *  Description: Models the board of a number slider puzzle.
  *
  *****************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
public class Board {
    
    // board size
    private final int n;
    
    // row which contains the blank square
    private final int row0;
    
    // column which contains the blank square
    private final int col0;
    
    // holds the contents of the board
    private final int[][] board;
    
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)       
    {
        n = tiles.length;
        board = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                board[i][j] = tiles[i][j];
            }
        }
        
        // temp variables to determine row0 and col0
        int r0 = 0;
        int c0 = 0;
        // finds location of 0
        for (int i = 0; i < n; i++) 
        {
            for (int j = 0; j < n; j++) 
            {
                if (board[i][j] == 0) 
                {
                    r0 = i;
                    c0 = j;
                    break;
                }
            }
        }
        row0 = r0;
        col0 = c0;
    }
    // string representation of this board
    public String toString()              
    {
        StringBuilder string = new StringBuilder();
        string.append(n);
        string.append('\n');
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {               
                string.append(board[i][j] + " ");
                if (j == n - 1) string.append('\n');
            }
        }
        return string.toString();
    }
    
    
    
// tile at (row, col) or 0 if blank
    public int tileAt(int row, int col)
    {
        if (row >= n || row < 0)
            throw new java.lang.IllegalArgumentException();
        if (col >= n || col < 0)
            throw new java.lang.IllegalArgumentException();
        return board[row][col];
    }
    
// board size n
    public int size()            
    {
        return n;
    }
    
// number of tiles out of place
    public int hamming()           
    {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != shouldBeHere(i, j)) {
                    if (board[i][j] != 0)
                        count++;
                }
            }           
        }
        return count;
    }
    
    // returns what number should be in a given spot; used for hamming()
    private int shouldBeHere(int x, int y) { return (n * x) + y + 1; }
    
// sum of Manhattan distances between tiles and goal
    public int manhattan()      
    {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {   
                if (board[i][j] != 0)
                {
                    count += Math.abs(i - findRow(board[i][j]));  
                    count += Math.abs(j - findCol(board[i][j]));
                }
            }
        }
        return count;
    }
    
    // finds which row a given number should be in
    private int findRow(int x) { return (x - 1) / n; }  
    
    // finds which column a given number should be in
    private int findCol(int x) { return (x - 1) % n; }
    
// is this board the goal board?
    public boolean isGoal() { return (hamming() == 0); }
    
// does this board equal y?
    public boolean equals(Object y)    
    {
        if (y == this) return true;

        if (y == null) return false;

        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        
        if (this.size() != that.size()) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {               
                if (board[i][j] != that.board[i][j]) return false;
            }
        }
        return true;
    }
    
// all neighboring boards
    public Iterable<Board> neighbors()     
    {
        Stack<Board> stack = new Stack<Board>();       
        
        
        if (row0 != 0) 
            stack.push(new Board(slide(row0, col0, row0 - 1, col0)));
        if (row0 != n-1) 
            stack.push(new Board(slide(row0, col0, row0 + 1, col0)));
        if (col0 != 0) 
            stack.push(new Board(slide(row0, col0, row0, col0 - 1)));
        if (col0 != n-1) 
            stack.push(new Board(slide(row0, col0, row0, col0 + 1)));
        
        return stack;
    }
    
    
// is this board solvable?
    public boolean isSolvable()  
    {
        int inv = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0) {
                    for (int k = 0; k <= i; k++) {
                        if (k < i) {
                            for (int m = 0; m < n; m++) {
                                if (board[i][j] < board[k][m] 
                                        && board[k][m] != 0) inv++;
                            }
                        }
                        else if (k == i)
                        {
                            for (int m = 0; m < j; m++) {
                                if (board[i][j] < board[k][m] 
                                        && board[k][m] != 0) inv++;
                            }
                        }
                    }
                }   
            }  
        }
        if (n % 2 == 0) {
            return ((inv + row0) % 2 != 0);
        }        
        else if (inv % 2 == 0) return true;      
        return false;      
    }
    
    
    // creates a new int[][] (for use in a new Board) and switches two values,
    // one of which is assumed to be 0.
    private int[][] slide(int fromX, int fromY, int toX, int toY)
    {
        int[][] b = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                b[i][j] = board[i][j];
            }
        }
        
        int temp = b[toX][toY];
        b[toX][toY] = b[fromX][fromY];
        b[fromX][fromY] = temp;
        
        return b;
    }
    
    
// unit testing (required) 
    public static void main(String[] args)           
    {
        int[][] input = { {0, 1, 3}, {4, 2, 5}, {7, 8, 6} };
        Board test = new Board(input);
        
        StdOut.println(test.toString());
        StdOut.println("size() = " + test.size());
        StdOut.println("tileAt(1, 1) = " + test.tileAt(1, 1));
        StdOut.println("hamming() = " + test.hamming());
        StdOut.println("manhattan() = " + test.manhattan());
        StdOut.println("isGoal() = " + test.isGoal());
        StdOut.println("isSolvable() = " + test.isSolvable());
        StdOut.println("Neighbors:");
        for (Board neighbor : test.neighbors())
            StdOut.println(neighbor);
        
        int[][] input2 = { {2, 5, 8, 17, 11}, {3, 9, 13, 15, 16},
            {1, 12, 4, 6, 18}, {14, 10, 7, 0, 19}, {21, 20, 22, 23, 24} };
        Board test2 = new Board(input2);
        
        StdOut.println(test2.toString());
        StdOut.println("size() = " + test2.size());
        StdOut.println("tileAt(1, 1) = " + test2.tileAt(1, 1));
        StdOut.println("hamming() = " + test2.hamming());
        StdOut.println("manhattan() = " + test2.manhattan());
        StdOut.println("isGoal() = " + test2.isGoal());
        StdOut.println("isSolvable() = " + test2.isSolvable());
        StdOut.println("Neighbors:");
        for (Board neighbor : test2.neighbors())
            StdOut.println(neighbor);
        
        int[][] input3 = { {0, 1, 3}, {4, 2, 5}, {7, 8, 6} };
        Board testCopy = new Board(input3);
        
        StdOut.println("Are two identical boards equal? " 
                           + test.equals(testCopy));
        StdOut.println("Are two different boards equal? " 
                           + test.equals(test2));    
        
    }
}