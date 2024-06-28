package languagelessons;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import languagelessons.Word.WordTense;
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

    private String textDescription;
    private Map<String, String> knownExamplesOfTransformResults;
    
    //Types of transform
    private Map<String, String> replacements;
    private List<String> addToStart;
    private List<String> addToEnd;
    //TODO: implement this option in replacement algorithm
    private boolean replacementsAfterAppendsAndPrepends = false;

    //Applies / Results
    private List<WordUsage> allowedWordUsagesThisTransformCanApplyTo;
    private WordUsage resultingWordUsage;

    private List<String> specialInclusionsForThisRule;
    private List<String> specialExclusionsFromThisRule;

    public WordTransform(){

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
    public static Word applyTransformationToWord(WordTransform transformToUse, Word wordToTransform){

        return WordTransform.applyTransformationToWord(transformToUse, wordToTransform.rootWord(), wordToTransform.wordUsage());
    }

    public static Word applyTransformationToWord(WordTransform transformToUse, String wordToTransform){
        return WordTransform.applyTransformationToWord(transformToUse, wordToTransform, WordUsage.Any);
    }

    public static Word applyTransformationToWord(WordTransform transformToUse, String wordToTransform, WordUsage originalWordUsage){

        String resultingWord = wordToTransform;
        //TODO:
        //1. check pre-requisites
        //  - allowedWordUsagesThisTransformCanApplyTo
        //  - special inclusion / exclusion
        boolean shouldApplyTransform = isWordTransformValidForGivenWord(transformToUse, wordToTransform, originalWordUsage);

        if (shouldApplyTransform){
            if (transformToUse.replacementsAfterAppendsAndPrepends){
                
                resultingWord = WordTransform.applyPrefixesAndSuffixes(transformToUse, resultingWord);
                resultingWord = WordTransform.applyReplacements(transformToUse, resultingWord);

            }else{

                resultingWord = WordTransform.applyReplacements(transformToUse, resultingWord);
                resultingWord = WordTransform.applyPrefixesAndSuffixes(transformToUse, resultingWord);

            }
        }

        //depending on order: replacementsAfterAppendsAndPrepends
        //2. apply replacements
        //3. apply append/prepend

        
        //resulting word usage should not be overriden here if there is an originalWordUsage, and resultingWordUsage was not explcitly set - i.e. has default value
        //TODO: may want to use a new WordUsage.Unknown to explicitly show where it's not provided
        WordUsage actualResultingWordUsage = (originalWordUsage != WordUsage.Any && transformToUse.resultingWordUsage == WordUsage.Any) ? originalWordUsage : transformToUse.resultingWordUsage;

        return new Word(actualResultingWordUsage, resultingWord);

    }

    //TODO: move to WordTransformHelper (to clean API surface while keeping unit tests)
    public static String applyReplacements(WordTransform transformToUse, String rootWord) {
        
        String output = rootWord;
        for (String thingToReplace : transformToUse.replacements.keySet()){

            output = output.replace(thingToReplace, transformToUse.replacements.get(thingToReplace));

        }
        return output;

    }
    //TODO: move to WordTransformHelper (to clean API surface while keeping unit tests)
    public static String applyPrefixesAndSuffixes(WordTransform transformToUse, String rootWord) {
        
        String output = rootWord;

        for (String prefix : transformToUse.addToStart){
            output = prefix + output;
        }
        for (String suffix : transformToUse.addToEnd){
            output += suffix;
        }

        return output;

    }

    protected static boolean isWordTransformValidForGivenWord(WordTransform transformToUse, Word wordToTransform) {
        return isWordTransformValidForGivenWord(transformToUse, wordToTransform.rootWord(), wordToTransform.wordUsage());
    }

    protected static boolean isWordTransformValidForGivenWord(WordTransform transformToUse, String wordToTransform, WordUsage usageOfWordToTransform) {
        
        boolean doesApply = false;

        boolean isSpeciallyIncluded = transformToUse.specialInclusionsForThisRule.contains(wordToTransform);
        boolean isSpeciallyExcluded = transformToUse.specialExclusionsFromThisRule.contains(wordToTransform);
        if (isSpeciallyIncluded && isSpeciallyExcluded){
            //TODO: handle case of misconfigured WordTransform - it should not have the same word as included AND excluded
        }
        else if (isSpeciallyIncluded){
            return true;
        }
        else if (isSpeciallyExcluded){
            return false;
        }

        if (
        usageOfWordToTransform == WordUsage.Any 
        || transformToUse.allowedWordUsagesThisTransformCanApplyTo.contains(WordUsage.Any) 
        || transformToUse.allowedWordUsagesThisTransformCanApplyTo.contains(usageOfWordToTransform)){
            doesApply = true;
        }

        return doesApply;

    }

    //TODO: change return type here or in an overload - because validation can have more detailed return value than boolean - like how many examples were checked? If failed, what was expected vs. actual?
    public static int countKnownExamplesThatAreCorrectlyTransformed(WordTransform transformToValidate){

        int successfullyValidated = 0;

        String[] keysOfKnownExamplesMap = transformToValidate.knownExamplesOfTransformResults.keySet().toArray(new String[]{});
        for (int indexOfExampleWord = 0; indexOfExampleWord < keysOfKnownExamplesMap.length; indexOfExampleWord++){
            String inputWord = keysOfKnownExamplesMap[indexOfExampleWord];
            String expectedResultWord = transformToValidate.knownExamplesOfTransformResults.get(inputWord);

            String actualResultOfTransformation = WordTransform.applyTransformationToWord(transformToValidate, inputWord).rootWord();
            if (expectedResultWord.equals(actualResultOfTransformation)){
                successfullyValidated++;
            }else{
                System.out.println("Validation for index=" + indexOfExampleWord + " failed. Expected:" + expectedResultWord + ", but got:" + actualResultOfTransformation);
            }

        }

        return successfullyValidated;

    }

    public String getTextDescription() {
        return this.textDescription;
    }

    public int getReplacementRuleCount() {
        return this.replacements.size();
    }

    public int getKnownExampleCount() {
        return this.knownExamplesOfTransformResults.size();
    }

    public int getCountOfPrefixes() {
        return this.addToStart.size();
    }
    public int getCountOfSuffixes() {
        return this.addToEnd.size();
    }

    public int getCountOfSpecialExclusionsFromThisRule() {
        return this.specialExclusionsFromThisRule.size();
    }
    public int getCountOfSpecialInclusionsForThisRule() {
        return this.specialInclusionsForThisRule.size();
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public String getExcludedWordsAsCommaDelimitedString() {
        return this.getSpecialCaseWordListAsCommaDelimitedConcatenatedString(SpecialCaseListType.ExclusionsFrom);
    }

    public String getIncludedWordsAsCommaDelimitedString() {
        return this.getSpecialCaseWordListAsCommaDelimitedConcatenatedString(SpecialCaseListType.InclusionsTo);
    }

    private enum SpecialCaseListType{
        InclusionsTo, ExclusionsFrom
    };

    public String getSpecialCaseWordListAsCommaDelimitedConcatenatedString(SpecialCaseListType typeOfList){

        StringBuilder concatenatedSpecialCaseWords = new StringBuilder();

        List<String> temporaryCopyOfSpecialCaseWords = new ArrayList<String>();
        
        switch (typeOfList){
            case InclusionsTo:
                temporaryCopyOfSpecialCaseWords.addAll(this.specialInclusionsForThisRule);
                break;
            case ExclusionsFrom:
                temporaryCopyOfSpecialCaseWords.addAll(this.specialExclusionsFromThisRule);
                break;
            default:
                break;
        }

        //Basic comparator, using default comparison from String, to sort alphabetically
        temporaryCopyOfSpecialCaseWords.sort( (String a, String b) -> {
            return a.compareTo(b);
        } );

        
        for (int indexOfCurrentSpecialCaseWord = 0; indexOfCurrentSpecialCaseWord < temporaryCopyOfSpecialCaseWords.size(); indexOfCurrentSpecialCaseWord++){
            String currentSpecialCaseWord = temporaryCopyOfSpecialCaseWords.get(indexOfCurrentSpecialCaseWord);

            concatenatedSpecialCaseWords.append(currentSpecialCaseWord);
            if (indexOfCurrentSpecialCaseWord != temporaryCopyOfSpecialCaseWords.size()-1){
                concatenatedSpecialCaseWords.append(",");
            }

        }

        return concatenatedSpecialCaseWords.toString();


    }

    /*
    private List<WordUsage> allowedWordUsagesThisTransformCanApplyTo;
    private WordUsage resultingWordUsage;
     */

    public void setResultingWordUsage(WordUsage wordUsageIfTransformationSuccessfullyAppliedToWord){

        this.resultingWordUsage = wordUsageIfTransformationSuccessfullyAppliedToWord;

    }

    public void addWordUsageThisRuleAppliesTo(WordUsage wordUsageThisRuleCanApplyTo){

        //NOTE: if word usage being added is "WordUsage.Any" we COULD delete all other entries as an optimisation 
        //    - but since doing so would destroy the "history" and the resulting behavior will be the same: prefer to add "WordUsage.Any" in the usual way

        //stop using the default setting if we are adding a new word usage
        if (this.allowedWordUsagesThisTransformCanApplyTo.size() == 1 && this.allowedWordUsagesThisTransformCanApplyTo.get(0) == WordUsage.Any){
            if (wordUsageThisRuleCanApplyTo == WordUsage.Any) {
                //we have the default list - which is 1 entry of WordUsage.Any - and the caller is adding WordUsage.Any
                //for this case, we can just skip adding and early-out here
                //    - because the state of the list is already as requested
                //    - this assumes user just wants WordUsage.Any present, and does not care if the list contains 1 or 2 entries of it
                return;
            }else{
                this.allowedWordUsagesThisTransformCanApplyTo.clear();
            }
        }

        this.allowedWordUsagesThisTransformCanApplyTo.add(wordUsageThisRuleCanApplyTo);

    }

    public static boolean areAllKnownExamplesAsExpected(WordTransform transformToValidate) {
        
        return WordTransform.countKnownExamplesThatAreCorrectlyTransformed(transformToValidate) == transformToValidate.getKnownExampleCount();

    }


}