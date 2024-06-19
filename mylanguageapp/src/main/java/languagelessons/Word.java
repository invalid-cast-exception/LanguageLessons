package languagelessons;

public class Word {

    WordUsage wordUsage;

    public Word(){
        //default constructor
    }

    public Word(WordUsage wordUsage) {
        this.wordUsage = wordUsage;
    }

    public String getRootWord() {
        return "Nothing";
    }

    public String getLanguage() {
        return "English";
    }

    public WordUsage getWordUsageHint() {
        return this.wordUsage;
    }



}
