package languagelessons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class WordTest {
    @Test void wordCanBeConstructed() {

        Word myFirstWord = new Word();

        assertNotNull(myFirstWord);
        assertEquals(myFirstWord.getRootWord(), "Nothing");

    }
}
