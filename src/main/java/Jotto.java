/**
 * @author 1onmyraft
 * @version 0.1.1
 * @Since 1/29/26
 **/
import java.util.ArrayList;

public class Jotto {
    private final ArrayList<String> wordList;
    private final ArrayList<String> playGuesses;
    private final ArrayList<String> playWords;
    private String currentWord;
    private String filename;
    private int score;
    private static final boolean DEBUG = true;
    private static final int WORD_SIZE = 5;



    public Jotto(String filename) {
        this.wordList = new ArrayList<>();
        this.playGuesses = new ArrayList<>();
        this.playWords = new ArrayList<>();
        this.filename = filename;

        this.readWords();
    }

    public ArrayList<String> readWords(){
        return wordList;
    }

    public void play(){
    }

    public int guess(){
        return 0;
    }

    public boolean pickWord(){
        return false;
    }

    public ArrayList<String> showPlayerGuesses(){
        return playGuesses;
    }

    void playerGuessScores(ArrayList<String> s){

    }

    public void setCurrentWord(String currentWord){
        this.currentWord = currentWord;
    }

    public ArrayList<String> getPlayWords() {
        return playWords;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public int getLetterCount(String s){
        return 0;
    }

    public void addPlayerGuess(String s){

    }

    public void updateWordList(){

    }

    public String showPlayedWords(){
        if (playWords.isEmpty())
            return "No words have been played";

        StringBuilder tmp = new StringBuilder("Current list of played words:\n");

        for (String word : playWords)
            tmp.append(word).append("\n");

        return tmp.toString();
    }


    public String showWordList(){
        StringBuilder tmp = new StringBuilder("Current word list:\n");

        for (String word : wordList)
            tmp.append(word).append("\n");

        return tmp.toString();
    }

}
