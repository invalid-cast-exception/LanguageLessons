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

    @Test void wordCanBeANounOrVerb(){
        Word myThirdWord = new Word();

        Word myFourthWord = new Word();

        assertNotNull(myThirdWord);
        assertNotNull(myFourthWord);

        assertEquals(WordUsage.Verb, myThirdWord.getWordUsageHint());
        assertEquals(WordUsage.Noun, myFourthWord.getWordUsageHint());
        

    }
}
