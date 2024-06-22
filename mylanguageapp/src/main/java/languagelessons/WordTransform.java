package languagelessons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import languagelessons.Word.WordUsage;

public class WordTransform{

    //NOTES
    //Capabilities of WordTransforms
    // - when does it apply? (custom per-word, can be duplicates or can be unique)
    // - prioritised substitutions (order of applying, old pattern, new pattern)
    // - metadata - explanation for the transform, reason it applies
    // - example words - shows what the transform does, allows to validate by using a known baseline

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

    public Word applyTransformationToWord(Word wordToTransform){

        return applyTransformationToWord(wordToTransform.rootWord());
    }

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