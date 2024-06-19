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



}
