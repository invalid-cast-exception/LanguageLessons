package languagelessons;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


public class WordTest {
    @Test void wordCanBeConstructed() {

        Word myFirstWord = new Word();

        assertNotNull(myFirstWord);
        assertEquals("Nothing", myFirstWord.rootWord());

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

        assertEquals(WordUsage.Verb, myThirdWord.wordUsage());
        assertEquals(WordUsage.Noun, myFourthWord.wordUsage());
        

    }

    @ParameterizedTest
    @ValueSource(strings={"My", "First", "Words"})
    protected void wordCanBeConstructedWithARootWord(String wordAsString){

        Word thisWord = new Word(wordAsString); 

        assertEquals(wordAsString, thisWord.rootWord());
        

    }
    
    @ParameterizedTest
    @ValueSource(strings={"My", "First", "Words"})
    protected void wordCanBePrintedToString(String wordAsString){

        Word thisWord = new Word(wordAsString); 

        assertEquals(wordAsString, thisWord.toString());
        

    }

    
    @ParameterizedTest
    @ValueSource(strings={"OneWord", "Two Words", "This. Is. Three."})
    protected void stringSentencesCanBeConvertedToMultipleArraysOfWords(String sentencesAsStringCommaDelimited){

        //the input string is expected to contain multiple comma-delimited sentences
        //split the sentences from the input string
        //The sentences are only packed in comma-delimited string for convenient test data preparation
        //  - Because ValueSource can provide strings but not string arrays for the test (and MethodSource and similar would add excessive complexity and indirection)
        String[] sentencesAsStringArray = sentencesAsStringCommaDelimited.split(",");

        Word[][] arrayOfArraysOfWords = Word.getMultipleWordArraysFromStringSentences(sentencesAsStringArray);

        //confirm that INPUT words arrays count == OUTPUT sentences count
        assertEquals(sentencesAsStringArray.length, arrayOfArraysOfWords.length);

        //Rebuild sentences to confirm the Word was correctly created
        //Note that this is testing by side-effect (i.e. confirming that the sentence was reconstructed from the Word instances, not confirming the exact state of the actual Word instances)
        //TODO: push this complex test code into the Word class as a helper function (showing a need for symetrical API - in this case the need is to reconstruct a string sentence from a list of Word instances - which is the reverse function of what is being testing here)
        //START - SENTENCE RECONSTRUCTION ROUTINE
        String[] reconstructedSentences = new String[arrayOfArraysOfWords.length];
        for (int indexOfCurrentSentence = 0; indexOfCurrentSentence < arrayOfArraysOfWords.length; indexOfCurrentSentence++) {
            
            Word[] currentSentence = arrayOfArraysOfWords[indexOfCurrentSentence];

            StringBuilder reconstructedSentence = new StringBuilder();
            
            
            for (int indexOfCurrentWord = 0; indexOfCurrentWord < currentSentence.length; indexOfCurrentWord++) {

                Word currentWord = currentSentence[indexOfCurrentWord];

                String currentWordAsString = currentWord.rootWord();
                reconstructedSentence.append(currentWordAsString);
                if (indexOfCurrentWord < currentSentence.length-1) reconstructedSentence.append(" ");

            }

            reconstructedSentences[indexOfCurrentSentence] = reconstructedSentence.toString();

        }
        //END - SENTENCE RECONSTRUCTION ROUTINE
        
        for (int currentSentenceIndex = 0; currentSentenceIndex < sentencesAsStringArray.length; currentSentenceIndex++){
            assertEquals(sentencesAsStringArray[currentSentenceIndex], reconstructedSentences[currentSentenceIndex]);
        }

    }

}
