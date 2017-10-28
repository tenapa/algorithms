import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Tetiana_Prynda
 * Created on 10/27/2017.
 */
public class WordNetTest {

    @Test(expected = IllegalArgumentException.class)
    public void wordNew_synsets3_hypernyms3InvalidCycle() throws Exception {
        new WordNet("wordnet/synsets3.txt", "wordnet/hypernyms3InvalidCycle.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void wordNew_synsets3_hypernyms3InvalidTwoRoots() throws Exception {
        new WordNet("wordnet/synsets3.txt", "wordnet/hypernyms3InvalidTwoRoots.txt");
    }

    @Test
    public void wordNew_synsets15_hypernyms15Tree() throws Exception {
        final WordNet wordNet = new WordNet("wordnet/synsets15.txt", "wordnet/hypernyms15Tree.txt");
        assertTrue(wordNet.isNoun("i"));
        assertFalse(wordNet.isNoun("p"));
        assertEquals(2, wordNet.distance("d", "e"));
        assertEquals("b", wordNet.sap("n", "b"));
        assertEquals("a", wordNet.sap("n", "c"));
        assertEquals("f", wordNet.sap("n", "j"));
    }

}