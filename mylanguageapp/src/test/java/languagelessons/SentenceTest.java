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
    
    
    static Stream<Arguments> GetAllArraysOfWords(){
        return GetArraysOfWords(-1);
    }
    static Stream<Arguments> GetFirstArrayOfWords(){
        return GetArraysOfWords(1);
    }
    
    static Stream<Arguments> GetArraysOfWords(int count){
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
    static void SetupTestValues(){

        listOfListsOfWords = new Word[sentencesAsStrings.length][];

        int currentWordListIndex = 0;
        for (String sentenceAsString : sentencesAsStrings) {
            
            String[] sentenceAsStringArray = sentenceAsString.split(" ");

            listOfListsOfWords[currentWordListIndex] = new Word[sentenceAsStringArray.length];

            int currentWordIndex = 0;
            for (String wordAsString : sentenceAsStringArray) {
                listOfListsOfWords[currentWordListIndex][currentWordIndex]= new Word(wordAsString);
                currentWordIndex++;
            }

            currentWordListIndex++;
        }
        
        assertEquals(sentencesAsStrings.length, listOfListsOfWords.length);

    }
    
    @ParameterizedTest
    @MethodSource("GetAllArraysOfWords")
    public void verifyTestSetupAsExpected(Word[] sentenceAsWords, String sentenceAsString) {

        String[] wordsInSentence = sentenceAsString.split(" ");
        int currentWordIndex = 0;
        for (String wordAsString : wordsInSentence) {
            assertEquals(wordAsString, sentenceAsWords[currentWordIndex].getRootWord());
            currentWordIndex++;
        }

    }

    @ParameterizedTest
    @MethodSource("GetFirstArrayOfWords")
    public void sentenceCanBeConstructedFromWords(Word[] sentenceAsWords, String sentenceAsString) {

        Sentence myFirstSentence = new Sentence(sentenceAsWords);
        
        assertNotNull(myFirstSentence);

    }
    @ParameterizedTest
    @MethodSource("GetFirstArrayOfWords")
    public void sentenceCanBePrintedToString(Word[] sentenceAsWords, String sentenceAsString) {

        Sentence myFirstSentence = new Sentence(sentenceAsWords);

        assertEquals(sentenceAsString, myFirstSentence.toString());

    }


}
