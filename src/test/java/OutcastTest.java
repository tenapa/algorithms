import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 * @author Tetiana_Prynda
 * Created on 10/28/2017.
 */
public class OutcastTest {

    @Test
    public void outcast_5() throws Exception {
        final Outcast outcast = buildOutcast("wordnet/synsets.txt", "wordnet/hypernyms.txt");
        String[] nouns = readNounsFromFile("wordnet/outcast5.txt");

        final String noun = outcast.outcast(nouns);

        assertEquals("table", noun);
    }


    @Test
    public void outcast_8() throws Exception {
        final Outcast outcast = buildOutcast("wordnet/synsets.txt", "wordnet/hypernyms.txt");
        String[] nouns = readNounsFromFile("wordnet/outcast8.txt");

        final String noun = outcast.outcast(nouns);

        assertEquals("bed", noun);
    }

    @Test
    public void outcast_11() throws Exception {
        final Outcast outcast = buildOutcast("wordnet/synsets.txt", "wordnet/hypernyms.txt");
        String[] nouns = readNounsFromFile("wordnet/outcast11.txt");

        final String noun = outcast.outcast(nouns);

        assertEquals("potato", noun);
    }

    private Outcast buildOutcast(String synset, String hypernym) {
        WordNet wordNet = new WordNet(synset, hypernym);
        return new Outcast(wordNet);
    }

    private String[] readNounsFromFile(String filename) {
        In in = new In(filename);
        Collection<String> outcastTerms = new LinkedList<>();
        while (in.hasNextLine()) {
            outcastTerms.add(in.readLine());
        }
        return outcastTerms.toArray(new String[outcastTerms.size()]);
    }

}