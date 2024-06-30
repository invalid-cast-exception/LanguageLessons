package languagelessons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WordConjugatorTest {


    //Example:
    //description: pluralise a singular noun, in English
    //WordTransform[0] = y->ies rule, as in Sky -> Skies
    //WordTransform[1] = e->es rule, as in Bike -> Bikes
    //WordTransform[2] = consonant->add s, as in Chair -> Chairs, Troll -> Trolls
    //WordTransform[3] = a->ae rule, as in Antenna -> Antennae
    //NOTES: 
    // - an individual WordTransform corresponds to the action taken on one word to change it's form (and if the rule is generic for multiple words then it will also be used for those words too)
    // - a GroupOfSimilarWordTransforms corresponds to a pattern - multiple WordTransforms collected together because they all achieve the same goal (but for different input/output words)

    WordConjugator makesWordsPlural;
    @BeforeEach
    public void SetupWordConjugator(){

        WordConjugator makesWordsPlural = new WordConjugator();

        //defensive check - an empty conjugator has no transforms yet
        assertEquals(0, makesWordsPlural.getCountOfWordTransforms());

        WordTransform part1 = new WordTransform("y->ies rule, as in Sky -> Skies");
        part1.addReplacementRule("y", "ies");
        WordTransform part2 = new WordTransform("e->es rule, as in Bike -> Bikes");
        part2.addReplacementRule("e", "es");
        WordTransform part3 = new WordTransform("consonant->add s, as in Chair -> Chairs, Troll -> Trolls");
        part3.addTextToAppend("s");
        WordTransform part4 = new WordTransform("a->ae rule, as in Antenna -> Antennae");
        part4.addReplacementRule("a", "ae");
        
        makesWordsPlural.addWordTransform(part1);
        makesWordsPlural.addWordTransform(part2);
        makesWordsPlural.addWordTransform(part3);
        makesWordsPlural.addWordTransform(part4);

    }

    @Test
    public void canConstructWordConjugator(){

        assertEquals(4, makesWordsPlural.getCountOfWordTransforms());

    }


}
