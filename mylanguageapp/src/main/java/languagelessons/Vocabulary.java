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

    public Boolean addWord(Word wordToAdd){
        return addWord(wordToAdd, IfWordAlreadyExists.AddAsDuplicate);
    }

    public Boolean updateWord(Word wordToAdd){
        return addWord(wordToAdd, IfWordAlreadyExists.Overwrite);
    }

    protected enum IfWordAlreadyExists{
        DoNotAdd
        , Overwrite
        , AddAsDuplicate
    }

    public Boolean addWord(Word wordToAdd, IfWordAlreadyExists resolution){

        Boolean wasAdded = wordsInVocabulary.add(wordToAdd);

        return wasAdded;
    }

    public String getName() {
        return this.name;
    }

    public Integer getWordCount() {
        return this.wordsInVocabulary.size();
    }


}
