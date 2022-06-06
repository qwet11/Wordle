package cs203.assignment5.Wordle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is used to create the game logic for Wordle
 * 
 * @author William Moss
 * @version 1.0
 */
public class Wordle {
    private Dictionary dictionary;
    private boolean hasWon;
    private boolean isPlaying;
    private List<String> guesses;
    private String currentWord;
    private int currentGuesses;
    final int MAX_GUESSES = 6;

    public Wordle() {
        this.dictionary = new Dictionary("src\\main\\java\\cs203\\assignment5\\Wordle\\dictionary5.txt");
        this.hasWon = false;
        this.isPlaying = true;
        this.guesses = new LinkedList<String>();
        this.currentWord = dictionary.randomWord();
        this.currentGuesses = 0;
    }

    public Wordle(Dictionary dictionary) {
        this.dictionary = dictionary;
        this.hasWon = false;
        this.isPlaying = true;
        this.guesses = new LinkedList<String>();
        this.currentWord = dictionary.randomWord();
        this.currentGuesses = 0;
    }

    public Wordle(Dictionary dictionary, HashMap<String, Integer> usedWords) {
        this.dictionary = dictionary;
        this.hasWon = false;
        this.isPlaying = true;
        this.guesses = new LinkedList<String>();
        this.currentGuesses = 0;

        // Make a copy of the dictionary to avoid modifying the original one
        Dictionary copy = dictionary.copy();

        // Check if the current word was used before
        while (usedWords.containsKey(this.currentWord) && copy.size() > 0) {
            // Word was used before. Get a new word

            this.currentWord = copy.randomWord();
            copy.remove(currentWord); // Keeps track of words already tried
        }

        if (copy.size() == 0) {
            throw new RuntimeException("User already used all words. Cannot play anymore.");
        }
    }

    /**
     * Checks if the game is still in progress
     * 
     * @return true if the game is still in progress, false otherwise
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Processes the guess and return the result
     * 
     * @param guess the guess to process
     * @return an array of the response for every letter in the word
     */
    public String[] guess(String guess) {
        if (legalGuess(guess)) {
            currentGuesses++;
            if (guess.equals(currentWord)) {
                hasWon = true;
                isPlaying = false;
                return getGuessResponse(guess);
            }

            if (currentGuesses == MAX_GUESSES) {
                isPlaying = false;
                return getGuessResponse(guess);
            }

            guesses.add(guess);

            return getGuessResponse(guess);
        } else {
            throw new IllegalArgumentException("Invalid guess.");
        }
    }

    /**
     * Returns the response for the given guess
     * 
     * @param guess the guess to process
     * @return an array of the response for every letter in the word
     */
    private String[] getGuessResponse(String guess) {
        return getGuessResponse(currentWord, guess);
    }

    /**
     * Returns the response for the given guess and given word
     * 
     * @param currentWord the word to guess
     * @param guess       the guess to process
     * @return an array of the response for every letter in the word
     */
    static String[] getGuessResponse(String currentWord, String guess) {
        String[] guessResponse = new String[guess.length()];
        for (int i = 0; i < guess.length(); i++) {
            if (guessResponse[i] != null) {
                // Already placed response
                continue;
            }

            if (currentWord.charAt(i) == guess.charAt(i)) {
                guessResponse[i] = "Correct";
                currentWord = currentWord.substring(0, i) + "-" + currentWord.substring(i + 1);
            } else if (currentWord.contains(guess.charAt(i) + "")) {
                int index = currentWord.indexOf(guess.charAt(i));

                // Special case when the letter is in the correct place
                if (currentWord.charAt(index) == guess.charAt(index)) {
                    guessResponse[index] = "Correct";
                    currentWord = currentWord.substring(0, index) + "-" + currentWord.substring(index + 1);
                    // Do this one again
                    i--;
                    continue;
                }

                guessResponse[i] = "Right letter, but wrong position";
                currentWord = currentWord.substring(0, index) + "-" + currentWord.substring(index + 1);
            } else {
                guessResponse[i] = "Wrong";
            }
        }
        return guessResponse;
    }

    /**
     * Checks if the given guess is valid
     * 
     * @param guess the guess to check
     * @return true if the guess is valid, false otherwise
     */
    public boolean legalGuess(String guess) {
        // Check the size of the guess
        if (guess.length() != 5) {
            return false;
        }

        // Check if the guess is in the dictionary
        if (!dictionary.isWord(guess)) {
            return false;
        }

        // Check if the guess has already been guessed
        if (guesses.contains(guess)) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the user has won the game
     * 
     * @return true if the user has won the game, false otherwise
     */
    public boolean hasWon() {
        return hasWon;
    }

    /**
     * Gets the current word
     * 
     * @return the current word
     */
    public String getCurrentWord() {
        return currentWord;
    }

    /**
     * Gets the current number of guesses
     * 
     * @return the current number of guesses
     */
    public int getCurrentGuesses() {
        return currentGuesses;
    }
}