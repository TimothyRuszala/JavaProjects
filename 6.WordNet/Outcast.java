/******************************************************************************
  *  Name:     Tim Ruszala
  *  NetID:    truszala
  *  Precept:  P04A
  *
  *  Partner Name:       N/A
  *  Partner NetID:      N/A
  *  Partner Precept:    N/A
  *
  *  Description: Using the WordNet datatype and given an input consisting
  *  of english-language nouns, this program determines which one "doesn't
  *  belong," or in other words, which word is semantically the most unrelated
  *  to the rest.
  *
  *****************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Outcast 
{
    // holds the WordNet input
    private final WordNet wordnet;
    
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet)   
    {
        this.wordnet = wordnet;
    }
    
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns)  
    {
        int maxDistSum = Integer.MIN_VALUE;
        String outcast = null;
        
        for (int i = 0; i < nouns.length; i++)
        {
            int distSum = 0;
            for (int j = 0; j < nouns.length; j++)
            {
                distSum += wordnet.distance(nouns[i], nouns[j]);
            }
            if (distSum > maxDistSum)
            {
                maxDistSum = distSum;
                outcast = nouns[i];
            }
        } 
        return outcast;
    }
    
    // test client (see below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}