import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Tetiana_Prynda
 * Created on 10/27/2017.
 */
public class WordNetTest {
    @Test
    public void wordNew_size3() throws Exception {
        WordNet wordNet = new WordNet("wordnet/synsets3.txt", "wordnet/hypernyms3InvalidCycle.txt");
        System.out.println(wordNet.nouns());

    }

}