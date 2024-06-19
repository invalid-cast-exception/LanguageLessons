package languagelessons;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
        Word myThirdWord = new Word(WordUsage.Verb);

        Word myFourthWord = new Word(WordUsage.Noun);

        assertNotNull(myThirdWord);
        assertNotNull(myFourthWord);

        assertEquals(WordUsage.Verb, myThirdWord.getWordUsageHint());
        assertEquals(WordUsage.Noun, myFourthWord.getWordUsageHint());
        

    }

    @ParameterizedTest
    @ValueSource(strings={"My", "First", "Words"})
    void wordCanBeConstructedWithARootWord(String wordAsString){

        Word thisWord = new Word(wordAsString); 

        assertEquals(wordAsString, thisWord.getRootWord());
        

    }
    
    @ParameterizedTest
    @ValueSource(strings={"My", "First", "Words"})
    void wordCanBePrintedToString(String wordAsString){

        Word thisWord = new Word(wordAsString); 

        assertEquals(wordAsString, thisWord.toString());
        

    }

}
