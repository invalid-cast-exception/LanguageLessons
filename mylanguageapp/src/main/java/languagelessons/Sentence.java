package languagelessons;

public class Sentence {

    Word[] wordsInSentence;

    public Sentence(Word[] sentenceAsWords) {
        
        this.wordsInSentence = sentenceAsWords;

    }

    public String toString(){
        StringBuilder sentenceStringBuilder = new StringBuilder();

        int currentWordIndex = 0;
        for (Word word : wordsInSentence){
            
            if (currentWordIndex != 0) sentenceStringBuilder.append(" ");
            sentenceStringBuilder.append(word.rootWord());
            currentWordIndex++;
        }

        return sentenceStringBuilder.toString();

    }

}
