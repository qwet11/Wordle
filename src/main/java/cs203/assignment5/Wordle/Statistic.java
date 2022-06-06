package cs203.assignment5.Wordle;

import java.util.HashMap;

/**
 * This class is used to generate the statistics of the user.
 * 
 * @author William Moss
 * @version 1.0
 */
public class Statistic {
    private String name;
    private int currentStreak;
    private int maxStreak;
    private HashMap<String, Integer> usedWords;

    public Statistic(String name) {
        this.name = name;
        this.currentStreak = FileHandler.getCurrentStreak(name);
        this.maxStreak = FileHandler.getMaxStreak(name);
        this.usedWords = FileHandler.getUsedWords(name);
    }

    /**
     * Get the name of the user.
     * 
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Get the current streak of the user.
     * 
     * @return the current streak of the user
     */
    public int getCurrentStreak() {
        return currentStreak;
    }

    /**
     * Get the max streak of the user.
     * 
     * @return the max streak of the user
     */
    public int getMaxStreak() {
        return maxStreak;
    }

    /**
     * Get the number of games played by the user.
     * 
     * @return the number of games played by the user
     */
    public int getNumPlayed() {
        return usedWords.size();
    }

    /**
     * Get the number of games won by the user in a given number of guess.
     * 
     * @param numGuesses the number of guesses
     * @return the number of games won by the user in a given number of guess
     */
    public int numWordsWithNumGuesses(int numGuesses) {
        int count = 0;
        for (String word : usedWords.keySet()) {
            if (usedWords.get(word).intValue() == numGuesses) {
                count++;
            }
        }

        return count;
    }

    /**
     * Get the number of games won by the user
     * 
     * @return the number of games won by the user
     */
    public int numWordsGuessedCorrectly() {
        int count = 0;
        for (String word : usedWords.keySet()) {
            if (usedWords.get(word).intValue() != -1) {
                count++;
            }
        }

        return count;
    }

    /**
     * Get the percentage of games won by the user
     * 
     * @return the percentage of games won by the user
     */
    public int percentGuessedCorrectly() {
        return (int) (((double) numWordsGuessedCorrectly() / usedWords.size()) * 100);
    }
}
