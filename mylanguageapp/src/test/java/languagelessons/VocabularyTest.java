package languagelessons;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import languagelessons.Vocabulary.IfWordAlreadyExists;

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

    @ParameterizedTest
    @ValueSource(strings={
        "a,b,b"
        , ""
        , "b,c,d,e,f,g,c"
    })
    void wordsCanBeAddedToVocabularyOnlyIfUnique(String sampleWordsAsCommaDelimitedString){

        String[] wordsToAdd = sampleWordsAsCommaDelimitedString.split(",");

        Vocabulary someWords = new Vocabulary("FancyWords");

        //defensive check before the test starts - we should see 0 here
        assertEquals(0, someWords.getWordCount());

        List<String> addedWords = new ArrayList<String>();
        for (String word : wordsToAdd){
            Boolean wordShouldBeAdded = !addedWords.contains(word);
            Boolean wasAdded = someWords.addWord(new Word(word), IfWordAlreadyExists.DoNotAdd);
            assertEquals(wordShouldBeAdded, wasAdded);
            if (wasAdded) addedWords.add(word);
        }

        //TODO: replace this with a hard-coded expected value per-test-case
        //count only unique words for this test case
        List<String> uniqueWords = new ArrayList<String>();
        for (String currentWord : wordsToAdd){
            if (!uniqueWords.contains(currentWord)) uniqueWords.add(currentWord);
        }

        //check only unique words were added
        assertEquals(uniqueWords.size(), someWords.getWordCount());


    }

    
    @ParameterizedTest
    @ValueSource(strings={
        "a,b,b"
        , ""
        , "b,c,d,e,f,g,c"
    })
    void wordsCanBeUpdatedInVocabulary(String sampleWordsAsCommaDelimitedString){

        String[] wordsToAdd = sampleWordsAsCommaDelimitedString.split(",");

        Vocabulary someWords = new Vocabulary("FancyWords");

        //defensive check before the test starts - we should see 0 here
        assertEquals(0, someWords.getWordCount());

        for (String word : wordsToAdd){
            Boolean wasAdded = someWords.addWord(new Word(word), IfWordAlreadyExists.Overwrite);
            assertTrue(wasAdded);
        }

        //TODO: replace this with a hard-coded expected value per-test-case
        //count only unique words for this test case
        List<String> uniqueWords = new ArrayList<String>();
        for (String currentWord : wordsToAdd){
            if (!uniqueWords.contains(currentWord)) uniqueWords.add(currentWord);
        }

        //check only unique words were added
        assertEquals(uniqueWords.size(), someWords.getWordCount());


    }

}
