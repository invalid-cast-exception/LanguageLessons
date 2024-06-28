package languagelessons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import languagelessons.Word.WordUsage;

public class WordTransformTest {

    // - bool validateKnownExamples
    

    WordTransform myTestWordTransform;

    @BeforeEach void SetupDefaultWordTransform(){
        myTestWordTransform = new WordTransform("A default WordTransform constructed by test setup.");

    }

    @Test void canConstructWordTransformWithDefaultConstructor(){
        
        assertNotNull(new WordTransform());

    }

    @Test void canConstructWordTransformGivenATextDescription(){
        
        String textDescription = "Add -ed suffix to make a verb past tense";

        WordTransform t = new WordTransform(textDescription);
        assertNotNull(t);

        assertEquals(textDescription, t.getTextDescription());

    }

    @ParameterizedTest
    @ValueSource(strings={
        "a,b,c,d,e,f"
        //bake -> baked
        , "e,ed"
        //better->be"tt"est, greater->greatest
        , "er,est"
    })
    void canAddReplacementRuleToWordTransform(String rulesAsCommaDelimitedString){

        //each pair of strings represents a single WordTransform "Replacement Rule"
        String[] testInputRules = rulesAsCommaDelimitedString.split(",");

        //defensive check to stop misconfigured test issues - must be provided string pairs to work
        assertEquals(0, testInputRules.length % 2);

        //defensive check - should start from default instance with 0 rules established
        assertEquals(0, myTestWordTransform.getReplacementRuleCount());

        for (int indexOfFirstStringInRule = 0; indexOfFirstStringInRule < testInputRules.length; indexOfFirstStringInRule+=2){

            myTestWordTransform.addReplacementRule(testInputRules[indexOfFirstStringInRule], testInputRules[indexOfFirstStringInRule+1]);

        }
        
        assertEquals(testInputRules.length / 2, myTestWordTransform.getReplacementRuleCount());

    }

    @ParameterizedTest
    @ValueSource(strings={
        "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z"
        , "bake,baked,fake,faked"
        , "better,best,greater,greatest"
    })
    void canAddKnownReplacementExample(String knownExamplesAsCommaDelimitedString){

        //each pair of strings represents a single WordTransform "Replacement Rule"
        String[] testInputKnownExamples = knownExamplesAsCommaDelimitedString.split(",");

        //defensive check to stop misconfigured test issues - must be provided string pairs to work
        assertEquals(0, testInputKnownExamples.length % 2);

        //defensive check - should start from default instance with 0 rules established
        assertEquals(0, myTestWordTransform.getKnownExampleCount());

        for (int indexOfFirstStringInRule = 0; indexOfFirstStringInRule < testInputKnownExamples.length; indexOfFirstStringInRule+=2){

            myTestWordTransform.addKnownReplacementExample(testInputKnownExamples[indexOfFirstStringInRule], testInputKnownExamples[indexOfFirstStringInRule+1]);

        }
        
        assertEquals(testInputKnownExamples.length / 2, myTestWordTransform.getKnownExampleCount());

    }

    @Test void canAddTextToBePrependedByWordTransformToTheRootWord(){

        assertEquals(0, myTestWordTransform.getCountOfPrefixes());

        final String[] wordsToPrepend = new String[]{
            "Be"
            , "Re"
            , "Un"
            , "Anti"
            , "Dis"
            , "Con"
            , "Pre"
            , "Pro"
            , "Hyper"
            , "Hypo"
        };

        for (String currentPrefix : wordsToPrepend){
            myTestWordTransform.addTextToPrepend(currentPrefix);
        }

        assertEquals(wordsToPrepend.length, myTestWordTransform.getCountOfPrefixes());

    }


    @Test void canAddTextToBeAppendedByWordTransformToTheRootWord(){

        assertEquals(0, myTestWordTransform.getCountOfSuffixes());

        final String[] wordsToAppend = new String[]{
            "tion"
            , "ed"
            , "ism"
            , "ian"
            , "ist"
            , "ly"
        };

        for (String currentSuffix : wordsToAppend){
            myTestWordTransform.addTextToAppend(currentSuffix);
        }

        assertEquals(wordsToAppend.length, myTestWordTransform.getCountOfSuffixes());

    }

    @Test void canAddSpecialExclusionForGivenWord(){

        assertEquals(0, myTestWordTransform.getCountOfSpecialExclusionsFromThisRule());

        //not needed for the unit test but helps illustrate the usage
        //TODO: replacement rule will also need an option to control how replacement gets done - just endings? anywhere in string? others?
        myTestWordTransform.addReplacementRule("ke", "ked");
        //also not needed but good example
        myTestWordTransform.setTextDescription("Change words ending 'ke' to past tense, in English.");

        //take becomes taken, not taked
        myTestWordTransform.addSpecialExclusionForGivenWord("take");
        //make becomes made, not maked
        myTestWordTransform.addSpecialExclusionForGivenWord("make");
        //forsake becomes forsook, not forsaked
        myTestWordTransform.addSpecialExclusionForGivenWord("forsake");



        assertEquals(3, myTestWordTransform.getCountOfSpecialExclusionsFromThisRule());

        //assumes order will be deterministic using alphabetic sorting, because that's the more predicatable than the adding order
        assertEquals("forsake,make,take", myTestWordTransform.getExcludedWordsAsCommaDelimitedString());


    }

    @Test void canAddSpecialInclusionForGivenWord(){

        assertEquals(0, myTestWordTransform.getCountOfSpecialInclusionsForThisRule());

        //not needed for the unit test but helps illustrate the usage
        //TODO: replacement rule will also need an option to control how replacement gets done - just endings? anywhere in string? others?
        myTestWordTransform.addReplacementRule("ke", "ked");
        //also not needed but good example
        myTestWordTransform.setTextDescription("Change words ending 'ke' to past tense, in English.");

        //take becomes taken, not taked
        myTestWordTransform.addSpecialInclusionForGivenWord("take");
        //make becomes made, not maked
        myTestWordTransform.addSpecialInclusionForGivenWord("make");
        //forsake becomes forsook, not forsaked
        myTestWordTransform.addSpecialInclusionForGivenWord("forsake");



        assertEquals(3, myTestWordTransform.getCountOfSpecialInclusionsForThisRule());

        //assumes order will be deterministic using alphabetic sorting, because that's the more predicatable than the adding order
        assertEquals("forsake,make,take", myTestWordTransform.getIncludedWordsAsCommaDelimitedString());


    }

    @Test void canDetermineWordTransformDoesNotApplyForGivenWordAccordingToWordUsage(){

        myTestWordTransform.addWordUsageThisRuleAppliesTo(WordUsage.Verb);

        Word noun = new Word(WordUsage.Noun, "Train");

        assertFalse(WordTransform.isWordTransformValidForGivenWord(myTestWordTransform, noun));

    }
    @Test void canDetermineWordTransformDoesApplyForGivenWordAccordingToWordUsage(){

        myTestWordTransform.addWordUsageThisRuleAppliesTo(WordUsage.Verb);

        Word verb = new Word(WordUsage.Verb, "Climb");

        assertTrue(WordTransform.isWordTransformValidForGivenWord(myTestWordTransform, verb));


    }
    
    @Test void canDetermineWordTransformDoesNotApplyForGivenWordAccordingToSpecialExclusions(){

        myTestWordTransform.addWordUsageThisRuleAppliesTo(WordUsage.Verb);
        myTestWordTransform.addSpecialExclusionForGivenWord("Climb");
        Word verb = new Word(WordUsage.Verb, "Climb");

        assertFalse(WordTransform.isWordTransformValidForGivenWord(myTestWordTransform, verb));

    }

    @Test void canDetermineWordTransformDoesApplyForGivenWordAccordingToSpecialInclusions(){

        myTestWordTransform.addWordUsageThisRuleAppliesTo(WordUsage.Verb);
        myTestWordTransform.addSpecialInclusionForGivenWord("Train");

        Word noun = new Word(WordUsage.Noun, "Train");

        assertTrue(WordTransform.isWordTransformValidForGivenWord(myTestWordTransform, noun));


    }


    @Test void canApplyPrefixesAndSuffixesAsExpected(){
        String expectedResult = "Antidisestablishmentarianism";
        String rootWord = "establish";

        myTestWordTransform.addTextToPrepend("dis");
        myTestWordTransform.addTextToPrepend("Anti");
        myTestWordTransform.addTextToAppend("ment");
        myTestWordTransform.addTextToAppend("arian");
        myTestWordTransform.addTextToAppend("ism");

        String actualResult = WordTransform.applyPrefixesAndSuffixes(myTestWordTransform, rootWord);

        assertEquals(expectedResult, actualResult);

    }

    @Test void canApplyReplacementsAsExpected(){
        String expectedResult = "proudly";
        String rootWord = "proud";

        myTestWordTransform.addReplacementRule("d", "dly");
        myTestWordTransform.addKnownReplacementExample("excitedly", "excited");

        String actualResult = WordTransform.applyReplacements(myTestWordTransform, rootWord);

        assertEquals(expectedResult, actualResult);

    }

    @Test void canApplyReplacementsAsExpectedWithTwoReplacements(){
        String expectedResult = "best";
        String rootWord = "better";

        //This specific rule applies for better->best, BUT might not always apply for words with "ett"...
        myTestWordTransform.addReplacementRule("ett", "");
        myTestWordTransform.addReplacementRule("er", "est");
        myTestWordTransform.addKnownReplacementExample("greater", "greatest");

        String actualResult = WordTransform.applyReplacements(myTestWordTransform, rootWord);

        assertEquals(expectedResult, actualResult);

    }


    @Test void doesNotApplyReplacementWhenNothingToReplace(){
        String expectedResult = "proud";
        String rootWord = "proud";

        myTestWordTransform.addReplacementRule("e", "ed"); 

        String actualResult = WordTransform.applyReplacements(myTestWordTransform, rootWord);

        assertEquals(expectedResult, actualResult);

    }

    //TODO: more complex test case with more transforms and more known examples to validate
    //e.g. canCountCorrectTransformedResultsWhenGivenMultipleKnownExamples
    @Test void canCountCorrectTransformedResultsWhenGivenASingleKnownExample(){
        
        //This specific rule applies for better->best, BUT might not always apply for words with "ett"...
        myTestWordTransform.addReplacementRule("ett", "");
        myTestWordTransform.addReplacementRule("er", "est");
        myTestWordTransform.addKnownReplacementExample("greater", "greatest");
        
        //defensive check that all examples were successfully checked
        assertEquals(1, WordTransform.countKnownExamplesThatAreCorrectlyTransformed(myTestWordTransform));

    }
    

    //TODO: more test cases for validation methods (currently just checks 1 transform with 1 example)
    @Test void canSuccessfullyValidateRuleOnKnownTestStrings(){
        
        //This specific rule applies for better->best, BUT might not always apply for words with "ett"...
        myTestWordTransform.addReplacementRule("ett", "");
        myTestWordTransform.addReplacementRule("er", "est");
        myTestWordTransform.addKnownReplacementExample("greater", "greatest");
        
        //defensive check that all examples were successfully checked
        assertEquals(myTestWordTransform.getKnownExampleCount(), WordTransform.countKnownExamplesThatAreCorrectlyTransformed(myTestWordTransform));

        //check the simple API - this returns true when all examples pass, or false if any fail validation
        boolean allVerifiedSuccessfully = WordTransform.areAllKnownExamplesAsExpected(myTestWordTransform);

        assertTrue(allVerifiedSuccessfully);

    }

    @Test void canApplyTransformToGivenString(){

        //setup the transform for the test case
        //    - a rule to pluralise a singular noun by adding "s" to the end
        myTestWordTransform.setTextDescription("Singular to plural, in English");
        myTestWordTransform.addKnownReplacementExample("wing", "wings");
        myTestWordTransform.addKnownReplacementExample("kettle", "kettles");
        myTestWordTransform.addWordUsageThisRuleAppliesTo(WordUsage.Noun);
        myTestWordTransform.addTextToAppend("s");
        //Acting as a mock user, we know that Sky becomes skies, not skys - so we can add an exclusion here
        myTestWordTransform.addSpecialExclusionForGivenWord("Sky");

        Word transformedWord = WordTransform.applyTransformationToWord(myTestWordTransform, "Kite");

        assertEquals("Kites", transformedWord.rootWord());

        transformedWord = WordTransform.applyTransformationToWord(myTestWordTransform, "Sky");

        assertEquals("Sky", transformedWord.rootWord());

    }





}
