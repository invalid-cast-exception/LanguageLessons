package languagelessons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//For more info about Java Record, since Java SE 14, see: https://docs.oracle.com/en/java/javase/17/language/records.html
public record Word (WordUsage wordUsage, String rootWord){
 
    //Explicit implementation for the record's compact constructor
    //By omitting the parameters from the method signature, they will be implicitly assumed to match the Record class declaration header
    public Word{
        
        //REMEMBER: after the compact constructor (if no exceptions) 
        //    - the value of the private final fields will get set from the implict parameters
        //e.g. compiler error prevents explicitly writing the below line 
        //    - because it conflicts with this automatic assignment:
        //this.rootWord = rootWord;

    }

    public Word(WordUsage wordUsage) {
        this(wordUsage, "Nothing"); 
    }

    public Word(String rootWord) {
        this(WordUsage.Noun, rootWord);
    }

    public Word() {
        this(WordUsage.Noun);
    }

    public String getLanguage() {
        return "English";
    }

    public String toString(){
        return this.rootWord();
    }

    public static Word[][] getMultipleWordArraysFromStringSentences(String[] sentencesAsStrings) {
        Word[][] output = new Word[sentencesAsStrings.length][];

        int currentWordListIndex = 0;
        for (String sentenceAsString : sentencesAsStrings) {
            
            String[] sentenceAsStringArray = sentenceAsString.split(" ");

            output[currentWordListIndex] = new Word[sentenceAsStringArray.length];

            int currentWordIndex = 0;
            for (String wordAsString : sentenceAsStringArray) {
                output[currentWordListIndex][currentWordIndex]= new Word(wordAsString);
                currentWordIndex++;
            }

            currentWordListIndex++;
        }

        return output;
    }

    public enum WordUsage {
        Any, Noun, Verb, Adverb, Adjective

    }

    public enum WordTense {
        Past, Future, Present, Conditional
    }

}
