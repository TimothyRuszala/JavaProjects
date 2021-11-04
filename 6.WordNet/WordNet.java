/******************************************************************************
  *  Name:     Tim Ruszala
  *  NetID:    truszala
  *  Precept:  P04A
  *
  *  Partner Name:       N/A
  *  Partner NetID:      N/A
  *  Partner Precept:    N/A
  *
  *  Description: A data struture which constructs a  DAG of english-language
  *  nouns. Can measure closest shared hypernym group, and give a quantitative
  *  value for semantic distance.
  *
  *****************************************************************************/

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class WordNet {
    
    // st with id as key and synset as val
    private final ST<Integer, String> idKey = new ST<Integer, String>();
    
    // st with word as key and id as val
    private final ST<String, Stack<Integer>> wordKey 
        = new ST<String, Stack<Integer>>(); 
    
    // holds the ShortestCommonAncestor data structure used for WordNet methods
    private final ShortestCommonAncestor sca;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();
        In syn = new In(synsets);
        In hyper = new In(hypernyms);
        while (!syn.isEmpty())
        {
            String line = syn.readLine();
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            idKey.put(id, fields[1]);
            String[] syns = fields[1].split(" ");
            for (int i = 0; i < syns.length; i++)
            {
                if (wordKey.get(syns[i]) == null)
                    wordKey.put(syns[i], new Stack<Integer>());
                wordKey.get(syns[i]).push(id);
            }
        }
        Digraph connections = new Digraph(idKey.size());
        while (!hyper.isEmpty())
        {
            String line = hyper.readLine();
            String[] fields = line.split(",");
            for (int i = 1; i < fields.length; i++)
                connections.addEdge(Integer.parseInt(fields[0]), 
                                    Integer.parseInt(fields[i]));
        }
        sca = new ShortestCommonAncestor(connections);
    }
    
    // all WordNet nouns
    public Iterable<String> nouns()
    {
        return wordKey;
    }
    
    // is the word a WordNet noun?
    public boolean isNoun(String word)
    {
        if (word == null) throw new IllegalArgumentException();
        return wordKey.contains(word);
    }
    
    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2)
    {
        if (noun1 == null || noun2 == null) throw new 
            IllegalArgumentException();
        Stack<Integer> v = wordKey.get(noun1);
        Stack<Integer> w = wordKey.get(noun2);
        int scaID = sca.ancestorSubset(v, w);
        return idKey.get(scaID);
    }
    
    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2)
    {
        if (noun1 == null || noun2 == null) throw new IllegalArgumentException();
        if (!isNoun(noun1) || !isNoun(noun2)) 
            throw new IllegalArgumentException();
        Stack<Integer> v = wordKey.get(noun1);
        Stack<Integer> w = wordKey.get(noun2);
        return sca.lengthSubset(v, w); 
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        String synsets = args[0];
        String hypernyms = args[1];
        WordNet test = new WordNet(synsets, hypernyms);
        Iterable<String> testNouns = test.nouns();
        StdOut.println(testNouns);
        test.isNoun("dragon");
        while (!StdIn.isEmpty())
        {
            String noun1 = StdIn.readString();
            String noun2 = StdIn.readString();
            String sca = test.sca(noun1, noun2);
            int d = test.distance(noun1, noun2);
            StdOut.println("length = " + d + ", ancestor = " + sca);
        }
    }
}