package languagelessons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import languagelessons.Word.WordUsage;

public class WordTransform{

    //NOTES
    //A WordTransform is a prototype for "something that handles the way words can be changed from the root word"
    //It depends on 3 key parts:
    //    - settings (stored on the instance, specifying how that instance will operate)
    //    - input (the given root word)
    //    - transformation process (an algorithm, operating on the input according to the settings, to produce a resulting word)
    //Capabilities of WordTransforms
    //    - when does it apply? (custom per-word, can be duplicates or can be unique)
    //    - prioritised substitutions (order of applying, old pattern, new pattern)
    //    - metadata - explanation for the transform, reason it applies
    //    - example words - shows what the transform does, allows to validate by using a known baseline
    //Implementation status
    //    - This version of the class is highly experimental and intended for exploration. So it is fully expected that a more optimal design exists - a better design should be converged on as iteration continues.
    //    - Where there are similar but different options possible, it is intended to create a unique WordTransform for each (there will be more duplication of data, but a clearer - approaching 1:1 mapping - between "possible transformation of a word according to natural language" and the WordTransform instances)
    //For more on natural language, see: https://en.wikipedia.org/wiki/Natural_language 

    String textDescription;
    Map<String, String> knownExamplesOfTransformResults;
    
    //Types of transform
    Map<String, String> replacements;
    List<String> addToStart;
    List<String> addToEnd;
    boolean replacementsAfterAppendsAndPrepends = false;

    //Applies / Results
    List<WordUsage> allowedWordUsagesThisTransformCanApplyTo;
    WordUsage resultingWordUsage;

    List<String> specialInclusionsForThisRule;
    List<String> specialExclusionsFromThisRule;

    protected WordTransform(){

        this.replacements = new HashMap<String, String>();
        this.knownExamplesOfTransformResults = new HashMap<String,String>();
        this.addToEnd = new ArrayList<String>();
        this.addToStart = new ArrayList<String>();
        this.allowedWordUsagesThisTransformCanApplyTo = new ArrayList<WordUsage>();
        //default for this constructor will apply for any kind of words
        this.allowedWordUsagesThisTransformCanApplyTo.add(WordUsage.Any);
        this.resultingWordUsage = WordUsage.Any;
        this.specialExclusionsFromThisRule = new ArrayList<String>();
        this.specialInclusionsForThisRule = new ArrayList<String>();
    }

    public WordTransform(String textDescription){
        this();
        this.textDescription = textDescription;
    }

    public String addReplacementRule(String patternToFindInWord, String replacementPattern){

        return this.replacements.put(patternToFindInWord, replacementPattern);

    }

    public String addKnownReplacementExample(String originalWord, String exampleResultAfterTransform){

        return this.knownExamplesOfTransformResults.put(originalWord, exampleResultAfterTransform);

    }

    public boolean addTextToPrepend(String textToPrepend){
        return this.addToStart.add(textToPrepend);
    }

    public boolean addTextToAppend(String textToAppend){
        return this.addToEnd.add(textToAppend);
    }

    public void addSpecialInclusionForGivenWord(String wordToInclude){
        this.specialInclusionsForThisRule.add(wordToInclude);
    }

    public void addSpecialExclusionForGivenWord(String wordToExcludeFromThisTransform){
        this.specialExclusionsFromThisRule.add(wordToExcludeFromThisTransform);
    }

    //Overload to handle Word as input (for convenience so that calling code does not need to extract the root word)
    public Word applyTransformationToWord(Word wordToTransform){

        return applyTransformationToWord(wordToTransform.rootWord());
    }

    //TODO: could be static pure function
    public Word applyTransformationToWord(String wordToTransform){

        String resultingWord = wordToTransform;
        //TODO:
        //1. check pre-requisites
        //  - allowedWordUsagesThisTransformCanApplyTo
        //  - special inclusion / exclusion

        //depending on order: replacementsAfterAppendsAndPrepends
        //2. apply replacements
        //3. apply append/prepend
        
        return new Word(this.resultingWordUsage, resultingWord);

    }

    //TODO: change return type here or in an overload - because validation can have more detailed return value than boolean - like how many examples were checked? If failed, what was expected vs. actual?
    public boolean validateKnownExamples(){

        String[] keysOfKnownExamplesMap = this.knownExamplesOfTransformResults.keySet().toArray(new String[]{});
        for (int indexOfExampleWord = 0; indexOfExampleWord < keysOfKnownExamplesMap.length; indexOfExampleWord++){
            String inputWord = keysOfKnownExamplesMap[indexOfExampleWord];
            String expectedResultWord = this.knownExamplesOfTransformResults.get(inputWord);

            if (expectedResultWord != this.applyTransformationToWord(inputWord).rootWord()){
                return false;
            }

        }

        //if we get here, all tested examples were successfully transformed by the rules
        //    - proving this WordTransform works as expected (for the known example words)
        return true;


    }


}