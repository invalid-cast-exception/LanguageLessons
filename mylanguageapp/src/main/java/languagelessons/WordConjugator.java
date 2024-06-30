package languagelessons;

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

    public Object getCountOfWordTransforms() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCountOfWordTransforms'");
    }

    public void addWordTransform(WordTransform part1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addWordTransform'");
    }

}
