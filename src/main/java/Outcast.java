/**
 * @author Tetiana_Prynda
 * Created on 10/28/2017.
 */
public class Outcast {
    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxDist = -1;
        String outcast = "";
        for (int i = 0; i < nouns.length; i++) {
            int distance = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i == j) continue;
                final int dist = wordNet.distance(nouns[i], nouns[j]);
                if (dist > 0) {
                    distance += dist;
                }
            }
            if (maxDist < distance) {
                outcast = nouns[i];
                maxDist = distance;
            }
        }
        return outcast;
    }

    // see test client below
    public static void main(String[] args) {
//        no tests here
    }
}
