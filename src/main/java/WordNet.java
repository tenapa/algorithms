import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.*;

/**
 * @author Tetiana_Prynda
 * Created on 10/27/2017.
 */
public class WordNet {
    private final Digraph graph;
    private final Map<Integer, SynSet> dictionary;
    private final Map<String, Integer> nounToId;

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
                nounToId.put(noun, id);
            }
            dictionary.put(id, new SynSet(id, nouns, data[2]));
        }
        graph = new Digraph(dictionary.size());

        final In hypernymsInput = new In(hypernyms);
        while (hypernymsInput.hasNextLine()) {
            final String[] hyperhyms = hypernymsInput.readLine().split(",");
            final int original = Integer.parseInt(hyperhyms[0]);
            for (int i = 1; i < hyperhyms.length; i++) {
                graph.addEdge(Integer.parseInt(hyperhyms[i]), original);
            }
        }
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
        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException("Input can not be null");

        return "";
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }

    private class SynSet {
        private final int id;
        private final Set<String> nouns;
        private final String definition;

        public SynSet(int id, Collection<String> nouns, String definition) {
            this.id = id;
            this.nouns = new HashSet<>(nouns);
            this.definition = definition;
        }

        public int getId() {
            return id;
        }

        public Set<String> getNouns() {
            return nouns;
        }

        public String getDefinition() {
            return definition;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SynSet synSet = (SynSet) o;
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
