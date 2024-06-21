package languagelessons;

import java.util.ArrayList;
import java.util.List;

//For more info about Java Record, since Java SE 14, see: https://docs.oracle.com/en/java/javase/17/language/records.html
public class Vocabulary{

    private String name;
    private List<Word> wordsInVocabulary;

    public Vocabulary(String nameOfVocabulary) {
        
        this.wordsInVocabulary = new ArrayList<>();
        this.name = nameOfVocabulary;

    }

    public boolean addWord(Word wordToAdd){
        return addWord(wordToAdd, IfWordAlreadyExists.AddAsDuplicate);
    }

    public boolean updateWord(Word wordToAdd){
        return addWord(wordToAdd, IfWordAlreadyExists.Overwrite);
    }

    protected enum IfWordAlreadyExists{
        DoNotAdd
        , Overwrite
        , AddAsDuplicate
    }

    public boolean addWord(Word wordToAdd, IfWordAlreadyExists resolution) {//} throws Exception{

        boolean alreadyExists = wordsInVocabulary.contains(wordToAdd);

        int invalidIndex = -1;
        int indexToInsertAt = invalidIndex;

        if (alreadyExists){
            switch (resolution) {
                case DoNotAdd:
                    return false;
                case Overwrite:
                    indexToInsertAt = wordsInVocabulary.indexOf(wordToAdd);
                    break;
            
                //fallthrough if adding as duplicate, to continue with adding
                case AddAsDuplicate:
                default:
                    break;
            }
        }

        boolean wasAdded = false;

        if (indexToInsertAt == invalidIndex){
            wasAdded = wordsInVocabulary.add(wordToAdd);
        }else{
            wordsInVocabulary.set(indexToInsertAt, wordToAdd);
            //TODO: handle the case where something went wrong (e.g. indexToInsertAt was not valid)
            wasAdded = true;
        }

        return wasAdded;
    }

    public String getName() {
        return this.name;
    }

    public int getWordCount() {
        return this.wordsInVocabulary.size();
    }


}
