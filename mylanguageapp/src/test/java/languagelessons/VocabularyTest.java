package languagelessons;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class VocabularyTest {


    @Test void vocabularyCanBeConstructed(){

        String nameOfVocabulary = "Work";
        Vocabulary someWords = new Vocabulary(nameOfVocabulary);

        assertNotNull(someWords);
        assertEquals(nameOfVocabulary, someWords.getName());
        
    }

    @ParameterizedTest
    @ValueSource(strings={
        "a,b,c"
        , ""
        , "b"
    })
    void wordsCanBeAddedToVocabulary(String sampleWordsAsCommaDelimitedString){

        String[] wordsToAdd = sampleWordsAsCommaDelimitedString.split(",");

        Vocabulary someWords = new Vocabulary("FancyWords");

        //defensive check before the test starts - we should see 0 here
        assertEquals(0, someWords.getWordCount());

        for (String word : wordsToAdd){
            someWords.addWord(new Word(word));
        }

        assertEquals(wordsToAdd.length, someWords.getWordCount());


    }


}
