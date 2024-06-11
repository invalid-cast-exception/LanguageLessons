package languagelessons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class WordTest {
    @Test void wordCanBeConstructed() {

        Word myFirstWord = new Word();

        assertNotNull(myFirstWord);
        assertEquals("Nothing", myFirstWord.getRootWord());

    }

    
    @Test void wordCanHaveAnAssociatedLanguage() {

        Word mySecondWord = new Word();

        assertNotNull(mySecondWord);
        assertEquals("English", mySecondWord.getLanguage());

    }
}
