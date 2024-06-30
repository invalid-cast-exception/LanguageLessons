package languagelessons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import languagelessons.Word.WordUsage;

/*
* specifies a syntax for a sentence
* For use in various places, such as:
* - generate a sentence by replacing words with words from vocabularies
* - generating quizzes (e.g. what form of the word fits in the sentence?)
* - ... 
*/


public class SentenceSyntax {

    List<WordUsage> sentenceSyntax;

    public SentenceSyntax(){
        this.sentenceSyntax = new ArrayList<WordUsage>();
    }

    public void setSyntax(WordUsage[] sentenceSyntax) {
        this.sentenceSyntax.clear();
        this.sentenceSyntax.addAll(Arrays.asList(sentenceSyntax));
    }

    public boolean doesSentenceMatch(Sentence testSentence) {

        //TODO: perhaps the word count does not always need to match?
        if (testSentence.wordsInSentence.length != this.sentenceSyntax.size()) return false;

        String thisSyntax = SentenceSyntax.getSyntaxAsString(this);
        String testSyntax = SentenceSyntax.getSyntaxAsString(testSentence);

        return (
            //validity checks
            thisSyntax != null && thisSyntax != "" && testSyntax != null && testSyntax != ""
            //actually compare test vs. baseline
            && thisSyntax.equals(testSyntax)
        );
    
    }

    public static String getSyntaxAsString(SentenceSyntax syntaxToPrint){
        return SentenceSyntax.getSyntaxAsString(syntaxToPrint.sentenceSyntax.toArray(new WordUsage[]{}));
    }
    public static String getSyntaxAsString(Sentence syntaxToPrint){

        Word[] words = syntaxToPrint.wordsInSentence;
        int numberOfWordsInSentence = words.length;

        WordUsage[] usages = new WordUsage[numberOfWordsInSentence];

        for (int indexOfUsage = 0; indexOfUsage < numberOfWordsInSentence; indexOfUsage++){
            usages[indexOfUsage] = words[indexOfUsage].wordUsage();
        }

        return SentenceSyntax.getSyntaxAsString(usages);
    }

    private static String getSyntaxAsString(WordUsage[] wordUsages){

        StringBuilder builder = new StringBuilder();

        for (WordUsage wordUsage : wordUsages) {
            builder.append("[");
            builder.append(wordUsage);
            builder.append("]");
        }
        
        return builder.toString();

    }



}
