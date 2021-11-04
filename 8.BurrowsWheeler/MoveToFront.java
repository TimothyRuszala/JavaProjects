/******************************************************************************
 *  Name:     Tim Ruszala
 *  NetID:    truszala
 *  Precept:  P04A
 *
 *  Partner Name:       N/A
 *  Partner NetID:      N/A
 *  Partner Precept:    N/A
 *
 *  Description: Does the Move-to-Front transformation on a given input
 *  string. An intermediary step for Burrows-Wheeler data compression.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    
    private static final int R = 256; // ASCII alphabet size
    
    // apply move-to-front encoding, reading from standard input and
    // writing to standard output
    public static void encode()
    {
        char[] chars = new char[R];
        for (int i = 0; i < R; i++)
            chars[i] = (char) i;
        
        while (!BinaryStdIn.isEmpty())
        {
            int x = BinaryStdIn.readChar();
            int location = 0;
            for (int i = 0; chars[i] < R; i++)
            {
                if (chars[i] == x) 
                {
                    location = i;
                    BinaryStdOut.write(location, 8); 
                    break;
                }
            }
            for (int i = location; i > 0; i--)
            {
                char temp = chars[i-1];
                chars[i-1] = chars[i];
                chars[i] = temp;
            }
        }
        
        BinaryStdOut.flush();
    }
    
    // apply move-to-front decoding, reading from standard input and
    // writing to standard output
    public static void decode()
    {
        int[] chars = new int[R];
        for (int i = 0; i < R; i++)
            chars[i] = i;
        
        while (!BinaryStdIn.isEmpty())
        {
            int x = BinaryStdIn.readInt(8);
            BinaryStdOut.write(chars[x], 8);
            for (int i = x; i > 0; i--)
            {
                int temp = chars[i-1];
                chars[i-1] = chars[i];
                chars[i] = temp;
            }
        }
        
        BinaryStdOut.flush();
            
    }
    
    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args)
    {
        char c = args[0].charAt(0);
        if (c == '-')
        {
            encode();
        }
        
        else if (c == '+')
        {
            decode();
        }
    }
}
