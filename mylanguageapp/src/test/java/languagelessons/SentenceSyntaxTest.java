package languagelessons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import languagelessons.Word.WordUsage;

public class SentenceSyntaxTest {


    @Test void canConstructASentenceSyntax(){

        assertNotNull(new SentenceSyntax());

    }

    @Test void canConfirmAGivenSentenceMatchesSyntax(){

        //Example Syntax:
        //[quantifier] [adjective] [noun, subject] [adverb] [verb] [preposition] [quantifier] [noun, object] [verb, infinitive] [quantifier] [noun]
        //Example sentence from syntax:
        //A happy goat joyfully jumps over a fence to eat some carrots

        SentenceSyntax syntaxToMatch = new SentenceSyntax();

        
        WordUsage[] sentenceSyntax = new WordUsage[]{
            WordUsage.Quantifier 
            , WordUsage.Adjective
            , WordUsage.Noun
            , WordUsage.Adverb
            , WordUsage.Verb
            , WordUsage.Preposition
            , WordUsage.Quantifier
            , WordUsage.Noun
            , WordUsage.Decorator
            , WordUsage.Verb
            , WordUsage.Quantifier
            , WordUsage.Noun};
    

        //defensive check
        Sentence test = new Sentence(new Word[]{
            new Word(WordUsage.Quantifier, "a") //quantifier
            , new Word(WordUsage.Adjective, "happy")
            , new Word(WordUsage.Noun, "goat")
            , new Word(WordUsage.Adverb, "joyfully")
            , new Word(WordUsage.Verb, "jumps")
            , new Word(WordUsage.Preposition, "over") //preposition
            , new Word(WordUsage.Quantifier, "a") //quantifier
            , new Word(WordUsage.Noun, "fence")
            , new Word(WordUsage.Decorator, "to") //infinitive
            , new Word(WordUsage.Verb, "eat")
            , new Word(WordUsage.Quantifier, "some") //quantifier
            , new Word(WordUsage.Noun, "carrots") 
        });

        //defensive check, before syntax is set
        assertFalse(syntaxToMatch.doesSentenceMatch(test));

        syntaxToMatch.setSyntax(sentenceSyntax);

        //TODO: separate to new unit test case
        assertEquals("[Quantifier][Adjective][Noun][Adverb][Verb][Preposition][Quantifier][Noun][Decorator][Verb][Quantifier][Noun]"
        , SentenceSyntax.getSyntaxAsString(test));

        //TODO: separate to new unit test case
        assertEquals("[Quantifier][Adjective][Noun][Adverb][Verb][Preposition][Quantifier][Noun][Decorator][Verb][Quantifier][Noun]",
         SentenceSyntax.getSyntaxAsString(syntaxToMatch));

        assertTrue(syntaxToMatch.doesSentenceMatch(test));


    }

    @Test void canConfirmAGivenSentenceDoesNotMatchSyntax(){

        //Example Syntax:
        //[quantifier] [adjective] [noun, subject] [adverb] [verb] [preposition] [quantifier] [noun, object] [verb, infinitive] [quantifier] [noun]
        //Example sentence from syntax:
        //A happy goat joyfully jumps over a fence to eat some carrots

        SentenceSyntax syntaxToMatch = new SentenceSyntax();

        
        WordUsage[] sentenceSyntax = new WordUsage[]{
            WordUsage.Quantifier 
            , WordUsage.Adjective
            , WordUsage.Noun
            , WordUsage.Adverb
            , WordUsage.Verb
            , WordUsage.Preposition
            , WordUsage.Quantifier
            , WordUsage.Noun
            , WordUsage.Decorator
            , WordUsage.Verb
            , WordUsage.Quantifier
            , WordUsage.Noun};
    

        //defensive check
        Sentence test = new Sentence(new Word[]{
            new Word(WordUsage.Quantifier, "a") //quantifier
            , new Word(WordUsage.Adjective, "happy")
            , new Word(WordUsage.Noun, "goat")
            , new Word(WordUsage.Adverb, "joyfully")
            , new Word(WordUsage.Verb, "jumps")
            , new Word(WordUsage.Preposition, "over") //preposition
            , new Word(WordUsage.Quantifier, "a") //quantifier
            , new Word(WordUsage.Noun, "fence")
            , new Word(WordUsage.Conjunction, "while") //infinitive
            , new Word(WordUsage.Verb, "eating")
            , new Word(WordUsage.Quantifier, "some") //quantifier
            , new Word(WordUsage.Noun, "carrots") 
        });

        //defensive check, before syntax is set
        assertFalse(syntaxToMatch.doesSentenceMatch(test));

        syntaxToMatch.setSyntax(sentenceSyntax);

        assertFalse(syntaxToMatch.doesSentenceMatch(test));


    }


    @Test void canConfirmAGivenSentenceDoesNotMatchSyntaxWhenNumberOfWordsDiffers(){

        //Example Syntax:
        //[quantifier] [adjective] [noun, subject] [adverb] [verb] [preposition] [quantifier] [noun, object] [verb, infinitive] [quantifier] [noun]
        //Example sentence from syntax:
        //A happy goat joyfully jumps over a fence to eat some carrots

        SentenceSyntax syntaxToMatch = new SentenceSyntax();

        
        WordUsage[] sentenceSyntax = new WordUsage[]{
            WordUsage.Quantifier 
            , WordUsage.Adjective
            , WordUsage.Noun
            , WordUsage.Adverb
            , WordUsage.Verb
            , WordUsage.Preposition
            , WordUsage.Quantifier
            , WordUsage.Noun
            , WordUsage.Decorator
            , WordUsage.Verb
            , WordUsage.Quantifier
            , WordUsage.Noun};
    

        //defensive check
        Sentence test = new Sentence(new Word[]{
            new Word(WordUsage.Quantifier, "a") //quantifier
            , new Word(WordUsage.Adjective, "happy")
            , new Word(WordUsage.Noun, "goat")
            , new Word(WordUsage.Verb, "jumps")
            , new Word(WordUsage.Preposition, "over") //preposition
            , new Word(WordUsage.Quantifier, "a") //quantifier
            , new Word(WordUsage.Noun, "fence")
            , new Word(WordUsage.Decorator, "to") //infinitive
            , new Word(WordUsage.Verb, "eat")
            , new Word(WordUsage.Quantifier, "some") //quantifier
            , new Word(WordUsage.Noun, "carrots") 
        });

        //defensive check, before syntax is set
        assertFalse(syntaxToMatch.doesSentenceMatch(test));

        syntaxToMatch.setSyntax(sentenceSyntax);

        assertFalse(syntaxToMatch.doesSentenceMatch(test));


    }


}
