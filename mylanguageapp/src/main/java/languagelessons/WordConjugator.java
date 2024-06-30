package languagelessons;

import java.util.ArrayList;
import java.util.List;

//TODO: this class will contain an array of WordTransforms
//This allows very specific WordTransforms to be created 
// - maybe even affecting only 1 word 
// - but still be connected to WordTransforms that do the same transform, but configured for different input words or slightly different results
//Example:
//description: pluralise a singular noun, in English
//WordTransform[0] = y->ies rule, as in Sky -> Skies
//WordTransform[1] = e->es rule, as in Bike -> Bikes
//WordTransform[2] = consonant->add s, as in Chair -> Chairs, Troll -> Trolls
//WordTransform[3] = a->ae rule, as in Antenna -> Antennae
//NOTES: 
// - an individual WordTransform corresponds to the action taken on one word to change it's form (and if the rule is generic for multiple words then it will also be used for those words too)
// - a GroupOfSimilarWordTransforms corresponds to a pattern - multiple WordTransforms collected together because they all achieve the same goal (but for different input/output words)

public class WordConjugator {

    List<WordTransform> wordTransforms;

    //by default, apply only the first (seems most common usage)
    WordConjugationPriority priority = WordConjugationPriority.ApplyFirstOnly;

    public enum WordConjugationPriority{
        ApplyFirstOnly
        , ApplyAll
    }

    public WordConjugator(){
        wordTransforms = new ArrayList<WordTransform>();
    }

    public int getCountOfWordTransforms() {
        return wordTransforms.size();
    }

    public void addWordTransform(WordTransform transformToAdd) {
        
        if (transformToAdd != null){
            wordTransforms.add(transformToAdd);
        }

    }

    public String getTransformedWord(String wordToTransform) {
        
        String output = wordToTransform;

        for (WordTransform wordTransform : wordTransforms) {

            //TODO: can log the history of transformations, plus the count of transformations done by storing these intermediary changes
            String oldWord = output;
            output = WordTransform.applyReplacements(wordTransform, output);

            //TODO: some transforms may potentially apply but NOT change the word...
            boolean wasApplied = oldWord != output;

            if (wasApplied){
                if (this.priority == WordConjugationPriority.ApplyFirstOnly) break;
            }
        }

        return output;

    }

}
