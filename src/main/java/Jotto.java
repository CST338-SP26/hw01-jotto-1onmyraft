/**
 * @author 1onmyraft
 * @version 0.1.1
 * @Since 1/29/26
 **/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Jotto {
    private final ArrayList<String> wordList;
    private final ArrayList<String> playerGuesses;
    private final ArrayList<String> playWords;
    private String currentWord;
    private final String filename;
    private int score;

    private static final boolean DEBUG = true;
    private static final int WORD_SIZE = 5;


    public int guess()
    {
        ArrayList<String> currentGuesses = new ArrayList<>();
     //   Used to store all the words entered by the user for the current round.
            Scanner scan = new Scanner(System.in);
       // To read user input and store it in wordGuess
        int letterCount;
        //The count of letters that wordGuess has in common with the currentWord.
        int score = WORD_SIZE + 1;
     //   The score for the current round
        String wordGuess = "";
        //The word the user has guessed.

        while (!wordGuess.equalsIgnoreCase("q")){
            System.out.println("Current Score: " + score );
            System.out.print("What is your guess (q to quit): ");
            wordGuess = scan.nextLine().trim().toLowerCase();

            if (wordGuess.equalsIgnoreCase("q")) break;

            if (wordGuess.length() != WORD_SIZE){
                System.out.println("Word must be "+WORD_SIZE+" characters (" + wordGuess + " is " +wordGuess.length()+ ")");
                continue;
            }

            this.addPlayerGuess(wordGuess);

            letterCount = this.getLetterCount(wordGuess);

            System.out.println(wordGuess + " has a Jotto score of " + letterCount);


            if (letterCount == WORD_SIZE)
            {

                if (wordGuess.equals(currentWord)) {

                    System.out.println("DINGDINGDINNG!!! the word was " + currentWord);


                    currentGuesses.add(currentWord);
                    playerGuessScores(currentGuesses);
                    System.out.println("Your score is " + score);
                    return score;
                }

                System.out.println("The word you chose is an anagram.");

            }


            currentGuesses.add(wordGuess);
            playerGuessScores(currentGuesses);
            score--;
        }

        score = 0;


        return score;
    }

    public boolean pickWord(){

        if (wordList.contains(currentWord) && playWords.size() == wordList.size()) {
            System.out.println("You've guessed them all!");
            return false;
        }

        Random rand = new Random();

        int idx = rand.nextInt(wordList.size());
        currentWord = wordList.get(idx);

        if (playWords.contains(currentWord))
            return this.pickWord();

        playWords.add(currentWord);

        if (DEBUG)
            System.out.println("[DEBUG] picked word = " + currentWord);

        return true;
    }

    public ArrayList<String> showPlayerGuesses() throws IOException{
       Scanner input = new Scanner(System.in);

       System.out.println("Current player guesses:");
        for (String playerGuess : playerGuesses)
            System.out.println(playerGuess);

       System.out.println("Would you like to add the words to the word list? (y/n)");

       if (input.nextLine().trim().equalsIgnoreCase("Y")) {
           this.updateWordList();
        System.out.println(this.showWordList());
       }
        return playerGuesses;
    }

    void playerGuessScores(ArrayList<String> s){
        System.out.println("Guess\t\tScore");

        for (String string : s)
            System.out.println(string + "\t\t" + getLetterCount(string));

        System.out.println("\n");
    }

    public void setCurrentWord(String currentWord){
        this.currentWord = currentWord;
    }

    public ArrayList<String> getPlayedWords() {
        return playWords;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public int getLetterCount(String wordGuess){
        if (wordGuess.equals(currentWord)) return 5;

        int count = 0;

        Set<Integer> letters = new HashSet<>();
        Set<Integer> letters2 = new HashSet<>();

        for (int i =0;i < WORD_SIZE; i++)
        {
            int current_char = wordGuess.charAt(i);
            letters.add(current_char);
            letters2.add((int)currentWord.charAt(i));
        }

        for (Integer i : letters2){
            if (letters.contains(i))
                count++;
        }

        return count;
    }

    public boolean addPlayerGuess(String wordGuess){
        if (!playerGuesses.contains(wordGuess)) {
            playerGuesses.add(wordGuess);
            return true;
        }
        return false;
    }

    public void updateWordList() throws IOException {
        System.out.println("Updating word list.");

        FileWriter fw;
        try {
            fw = new FileWriter(filename);
        } catch (IOException e) {
            System.out.println("Bad file name.");
            return;
        }

        for (String playerGuess : playerGuesses)
            if (!wordList.contains(playerGuess))
                wordList.add(playerGuess);

        for (String s : wordList)
            fw.write(s + "\n");

        fw.close();
    }

    public Jotto(String filename) {
        this.playerGuesses = new ArrayList<>();
        this.playWords = new ArrayList<>();
        this.filename = filename;

        this.wordList = this.readWords();
    }

    public ArrayList<String> readWords(){

        ArrayList<String> word_list = new ArrayList<>();

        File words = new File(filename);
        try {
            Scanner scan = new Scanner(words);

            while (scan.hasNextLine()) {
                word_list.add(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
           System.out.println("Couldn't open " + filename);
        }

        return word_list;
    }

    public void play() throws IOException{
        Scanner input = new Scanner(System.in);


        System.out.println("Welcome to the game.");
        System.out.println("Current Score: " + score);


        String userGuess = "";
        while (!userGuess.equalsIgnoreCase("zz")){

            switch (userGuess){
                case "one":
                case "1":
                    if (!this.pickWord())
                        this.showPlayerGuesses();
                    else
                        score += this.guess();

                    break;
                case "two":
                case "2":
                    System.out.print(this.showWordList());
                    break;
                case "three":
                case "3":
                    System.out.print(this.showPlayedWords());
                    break;
                case "four":
                case "4":
                    this.showPlayerGuesses();
                    break;
                case "":
                    break;
                default:
                    System.out.println("I don't know what \"" + userGuess + "\" is.");

            }

            if (!userGuess.isEmpty())
            {
                System.out.print("Press enter to continue");
                input.nextLine();
            }

            System.out.print("""
                    =-=-=-=-=-=-=-=-=-=-=
                    Choose one of the following:
                    1:\t Start the game
                    2:\t See the word list
                    3:\t See the chosen words
                    4:\t Show Player guesses
                    zz to exit
                    =-=-=-=-=-=-=-=-=-=-=
                    What is your choice:\s""");

            userGuess = input.nextLine().toLowerCase();
        }

        System.out.println("Final score: " + score );
        System.out.println("Thank you for playing");


    }


    public String showPlayedWords(){
        if (playWords.isEmpty())
            return "No words have been played.\n";

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
