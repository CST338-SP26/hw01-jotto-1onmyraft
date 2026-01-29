/**
 * @author 1onmyraft
 * @version 0.1.1
 * @Since 1/29/26
 **/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Jotto {
    private final ArrayList<String> wordList;
    private final ArrayList<String> playerGuesses;
    private final ArrayList<String> playedWords;
    private String currentWord;
    private String filename;
    private int score;


    public ArrayList<String> getWordList() {
        return wordList;
    }

    public ArrayList<String> getPlayerGuesses() {
        return playerGuesses;
    }

    public ArrayList<String> getPlayedWords() {
        return playedWords;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Jotto(String filename) {
        this.wordList = new ArrayList<>();
        this.playerGuesses = new ArrayList<>();
        this.playedWords = new ArrayList<>();
        this.filename = filename;

        this.readWords();
    }

    public ArrayList<String> readWords(){

        File words = new File(filename);
        try {
            Scanner scan = new Scanner(words);

            while (scan.hasNextLine()) {
                wordList.add(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
           System.out.println("Couldn't open " + filename);
        }

        return wordList;
    }

    public void player(){
        Scanner input = new Scanner(System.in);

        String userGuess = "";
        while (!userGuess.equals("zz")){

            switch (userGuess){
                case "one":
                case "1":
                    boolean pick = this.pickword();
                    if (pick)
                        this.showPlayerGuesses();
                    else
                        score += this.guess();

                    break;
                case "two":
                case "2":
                    this.showWordList();
                    break;
                case "three":
                case "3":
                    this.showPlayedWords();
                    break;
                case "four":
                case "4":
                    this.showPlayerGuesses();
                    break;
                case "":
                    break;
                default:
                    System.out.println("I don't know what " + userGuess + "is");

            }

            if (!userGuess.isEmpty())
            {
                System.out.print("Press enter to continue");
                input.nextLine();
            }

            System.out.print("=-=-=-=-=-=-=-=-=-=-=\n" +
                    "Choose one of the following:\n" +
                    "1:\t Start the game\n" +
                    "2:\t See the word list\n" +
                    "3:\t See the chosen words\n" +
                    "4:\t Show Player guesses\n" +
                    "zz to exit\n" +
                    "=-=-=-=-=-=-=-=-=-=-=\nWhat is your choice: ");

            userGuess = input.nextLine().toLowerCase();
        }
    }


    public String showPlayedWords(){
        if (playedWords.isEmpty())
            return "No words have been played";

        StringBuilder tmp = new StringBuilder("Current list of played words:\n");

        for (String word : playedWords)
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
