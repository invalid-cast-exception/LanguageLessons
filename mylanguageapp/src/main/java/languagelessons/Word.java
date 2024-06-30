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

    //TODO: see: https://en.wikipedia.org/wiki/Discourse_marker 
    public enum WordUsage {
        Any, Noun, Verb, Adverb, Adjective, 
        //see: https://en.wikipedia.org/wiki/Determiner
        Quantifier
        , Preposition
        //e.g. in English, infinitive verbs are in the for "to verb" and in this case we are calling "to" a decorator
        , Decorator
        //e.g. in English: and, but, if, while
        , Conjunction

    }

    //see: https://en.wikipedia.org/wiki/Grammatical_tense
    //see: https://en.wikipedia.org/wiki/Relative_and_absolute_tense
    public enum WordTense {
        None, Past, Future, Present
    }


    //TODO: move this to sentence, because grammar is more related to sentences than individual words... As shown by interesting as a verb, or as a present participle (it's the same word but the usage in a sentence determines which mood it would be described as having)
    //see: https://en.wikipedia.org/wiki/Grammatical_mood
    public enum WordMood {
        //Event depending on a condition, e.g. can, may, shall, will = could, might, should, would
        //In English, combines with bare infinitive verb (e.g. would buy, should eat, might want, could talk)
        Conditional
        //e.g. Let's go
        , Imperative
        //e.g. she may go
        , Potential
        //future, past, present - statement of actuality (or high-probablity)
        , Indicative
        //From Wikipedia - John eats if he is hungry -> subjunctive -> John would eat if he were hungry
        , Subjunctive
    }

    //see: 
    public enum VerbForm {
        //non-finite - to-infinitive, like: to walk, to climb, to fly
        Infinitive, 
        //non-finite - verb/adjective hybrids, AKA using a verb in a different context so it's an adjective now
        //like for past participle: broken, attached, etc
        //like for present participles: interesting, exciting
        Participle, 
        //non-finite - verb acting as a noun (like painting, building, writing, etc)
        Gerund,
        //finite - immediately complements the subject (when not used in imperative mood)
        Finite
    }

}
