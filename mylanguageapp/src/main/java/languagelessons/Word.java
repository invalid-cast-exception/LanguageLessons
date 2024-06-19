package languagelessons;

public class Word {

    private WordUsage wordUsage;
    private String rootWord = "Nothing";

    public Word(){
        //default constructor
    }

    public Word(WordUsage wordUsage) {
        this.wordUsage = wordUsage;
    }

    public Word(String rootWord) {
        this.rootWord = rootWord;
    }

    public String getRootWord() {
        return this.rootWord;
    }

    public String getLanguage() {
        return "English";
    }

    public WordUsage getWordUsageHint() {
        return this.wordUsage;
    }

    public String toString(){
        return getRootWord();
    }

    public static Word[][] getMultipleWordArraysFromStringSentences(String[] sentencesAsStrings) {
        Word[][] output = new Word[sentencesAsStrings.length][];

        int currentWordListIndex = 0;
        for (String sentenceAsString : sentencesAsStrings) {
            
            String[] sentenceAsStringArray = sentenceAsString.split(" ");

            output[currentWordListIndex] = new Word[sentenceAsStringArray.length];

            int currentWordIndex = 0;
            for (String wordAsString : sentenceAsStringArray) {
                output[currentWordListIndex][currentWordIndex]= new Word(wordAsString);
                currentWordIndex++;
            }

            currentWordListIndex++;
        }

        return output;
    }



}
