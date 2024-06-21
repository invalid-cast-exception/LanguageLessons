package languagelessons;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SentenceTest {

    static Word[][] listOfListsOfWords;
    static final String[] sentencesAsStrings = new String[]{
        "The Tall Alligator Snaps"
        , "The Quick Duck Quacked."
    };
    
    //named static helper for SentenceTest - for use with MethodSource annotation
    static Stream<Arguments> getAllArraysOfWords(){
        return getSubsetOfArraysOfWordsAndCorrespondingStringSentence(-1);
    }

    //named static helper for SentenceTest - for use with MethodSource annotation
    static Stream<Arguments> getFirstArrayOfWords(){
        return getSubsetOfArraysOfWordsAndCorrespondingStringSentence(1);
    }

    //DRY - unified method to handle any requested count of entries in arguments stream - while always using the same local SentenceTest properties
    static Stream<Arguments> getSubsetOfArraysOfWordsAndCorrespondingStringSentence(int requestedCount){
        return getArraysOfWordsAndCorrespondingStringSentenceAsArguments(requestedCount,listOfListsOfWords,sentencesAsStrings);

    }
    
    //core helper overload - functional and public static - intended for use in other tests, e.g. WordTest.java
    public static Stream<Arguments> getArraysOfWordsAndCorrespondingStringSentenceAsArguments(int count, Word[][] listOfListsOfWords, String[] sentencesAsStrings){
        Builder<Arguments> a = Stream.builder();

        int currentSentenceAsStringIndex = 0;
        for (Word[] listOfWords : listOfListsOfWords){
            
            //handles the case where a limited range was requested AND we already got the needed values
            if (count != -1 && currentSentenceAsStringIndex >= count) break;

            a.add(Arguments.of((Object)listOfWords, (Object)sentencesAsStrings[currentSentenceAsStringIndex]));
            currentSentenceAsStringIndex++;
        }

        return a.build();

    }

    @BeforeAll
    static void setupTestValues(){

        listOfListsOfWords = Word.getMultipleWordArraysFromStringSentences(sentencesAsStrings);
        
        assertEquals(sentencesAsStrings.length, listOfListsOfWords.length);

    }
    
    @ParameterizedTest
    @MethodSource("getAllArraysOfWords")
    protected void verifyTestSetupAsExpected(Word[] sentenceAsWords, String sentenceAsString) {

        String[] wordsInSentence = sentenceAsString.split(" ");
        int currentWordIndex = 0;
        for (String wordAsString : wordsInSentence) {
            assertEquals(wordAsString, sentenceAsWords[currentWordIndex].rootWord());
            currentWordIndex++;
        }

    }

    @ParameterizedTest
    @MethodSource("getFirstArrayOfWords")
    protected void sentenceCanBeConstructedFromWords(Word[] sentenceAsWords, String sentenceAsString) {

        Sentence myFirstSentence = new Sentence(sentenceAsWords);
        
        assertNotNull(myFirstSentence);

    }
    @ParameterizedTest
    @MethodSource("getFirstArrayOfWords")
    protected void sentenceCanBePrintedToString(Word[] sentenceAsWords, String sentenceAsString) {

        Sentence myFirstSentence = new Sentence(sentenceAsWords);

        assertEquals(sentenceAsString, myFirstSentence.toString());

    }


}
