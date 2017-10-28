import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Tetiana_Prynda
 * Created on 10/27/2017.
 */
public class WordNet {
    private final SAP sap;
    private final Map<Integer, SynSet> dictionary;
    private final Map<String, Collection<Integer>> nounToId;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException("Input can not be null");

        dictionary = new HashMap<>();
        nounToId = new HashMap<>();
        final In synsetsInput = new In(synsets);

        while (synsetsInput.hasNextLine()) {
            final String[] data = synsetsInput.readLine().split(",");
            final int id = Integer.parseInt(data[0]);
            final List<String> nouns = Arrays.asList(data[1].split(" "));
            for (String noun : nouns) {
                Collection<Integer> idsForNoun = nounToId.get(noun);
                if (idsForNoun == null) {
                    idsForNoun = new LinkedList<>();
                    nounToId.put(noun, idsForNoun);
                }
                idsForNoun.add(id);
            }
            dictionary.put(id, new SynSet(id, data[1], data[2]));
        }
        Digraph graph = new Digraph(dictionary.size());

        Collection<Integer> roots = new ArrayList<>(dictionary.keySet());
        Set<Integer> nodesWithChildren = new HashSet<>(dictionary.keySet().size());

        final In hypernymsInput = new In(hypernyms);
        while (hypernymsInput.hasNextLine()) {
            final String line = hypernymsInput.readLine();
            final String[] hyperhyms = line.split(",");
            final int child = Integer.parseInt(hyperhyms[0]);
            for (int i = 1; i < hyperhyms.length; i++) {
                final int parent = Integer.parseInt(hyperhyms[i]);

                roots.remove(child);
                graph.addEdge(child, parent);
                nodesWithChildren.add(parent);
            }
        }

        if (roots.isEmpty() || roots.size() > 1)
            throw new IllegalArgumentException("Not a rooted WordNet");

        int rootId = roots.iterator().next();
        if (!nodesWithChildren.contains(rootId))
            throw new IllegalArgumentException("Root does not have any children");

        sap = new SAP(graph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounToId.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("Input can not be null");

        return nounToId.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException("Input can not be null");
        return sap.length(nounToId.get(nounA), nounToId.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException("Input can not be null");

        final int ancestor = sap.ancestor(nounToId.get(nounA), nounToId.get(nounB));
        return dictionary.get(ancestor).getNouns();
    }

    // do unit testing of this class
    public static void main(String[] args) {
//        no tests here
    }

    private class SynSet {
        private final int id;
        private final String nouns;
        private final String definition;

        public SynSet(int id, String nouns, String definition) {
            this.id = id;
            this.nouns = nouns;
            this.definition = definition;
        }

        public int getId() {
            return id;
        }

        public String getNouns() {
            return nouns;
        }

        public String getDefinition() {
            return definition;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            SynSet synSet = (SynSet) obj;
            return id == synSet.id &&
                    Objects.equals(nouns, synSet.nouns) &&
                    Objects.equals(definition, synSet.definition);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, nouns, definition);
        }
    }
}
