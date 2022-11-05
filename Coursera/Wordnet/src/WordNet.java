
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {

    private Digraph graph;
    private SAP sap;

    private int graphV = 0;

    private HashMap<String, ArrayList<Integer>> nounList = new HashMap<>();
    private HashMap<Integer, String> synsetList = new HashMap<>();
    private void parseSynsets(String synsets) {
        In input = new In(synsets);
        while (input.hasNextLine()) {
            String data = input.readLine();
            String[] parsedElements = data.split(",");
            int id = Integer.parseInt(parsedElements[0]);
            String[] nouns = parsedElements[1].split(" ");
            for (String noun : nouns) {
                ArrayList<Integer> nounId;
                if (nounList.containsKey(noun))
                    nounId = nounList.get(noun);
                else {
                    nounId = new ArrayList<>();
                    nounList.put(noun, nounId);
                }
                nounId.add(id);
            }
            synsetList.put(id, parsedElements[1]);
            ++graphV;
        }
    }

    private void parseHypernyms(String hypernyms) {
        In input = new In(hypernyms);
        while (input.hasNextLine()) {
            String data = input.readLine();
            String[] parsedElements = data.split(",");
            int u = Integer.parseInt(parsedElements[0]);
            for (int i = 1; i < parsedElements.length; ++i)
                graph.addEdge(u, Integer.parseInt(parsedElements[i]));
        }
    }
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("null string passed in constructor Wordnet(String, String)");
        parseSynsets(synsets);
        graph = new Digraph(graphV);
        parseHypernyms(hypernyms);

        if (new DirectedCycle(graph).hasCycle())
            throw new IllegalArgumentException("constructor Wordnet(String, String): graph has cycle");
        int countRoot = 0;
        for (int u = 0; u < graphV; ++u)
            if (!graph.adj(u).iterator().hasNext())
                ++countRoot;
        if (countRoot > 1)
            throw new IllegalArgumentException("constructor Wordnet(String, String): graph is not a rooted DAG");
        sap = new SAP(graph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounList.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("null string passed in function Wordnet::isNoun");
        return nounList.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if(!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("null string passed or not contained noun in function Wordnet::distance");
        return sap.length(nounList.get(nounA), nounList.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in the shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if(!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("null string passed or not contained noun in function Wordnet::sap");
        int ancestor = sap.ancestor(nounList.get(nounA), nounList.get(nounB));
        return synsetList.get(ancestor);
    }
}