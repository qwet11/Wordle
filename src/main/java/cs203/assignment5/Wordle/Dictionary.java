package cs203.assignment5.Wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class is used to create a dictionary which stores the words given in the
 * text file.
 * 
 * @author William Moss
 * @version 3.0
 */

public class Dictionary {
    private HashMap<String, String> dictionary;

    public Dictionary() {
        dictionary = new HashMap<>();
    }

    /**
     * Read the dictionary file and build the dictionary.
     * 
     * @param fileName the name of the dictionary file
     * @throws FileNotFoundException
     */

    public Dictionary(String filename) {
        this();

        // Read the file line by line. Add each word to the dictionary
        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNext()) {
                addWord(in.next());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the dictionary.
     * 
     * @return the dictionary tree
     */
    public HashMap<String, String> getDictionary() {
        return dictionary;
    }

    /**
     * Add a word to the dictionary.
     * 
     * @param word the word to add
     */
    public void addWord(String word) {
        dictionary.put(word, word);
    }

    /**
     * Check if the given word is in the dictionary and is unused
     * 
     * @param word the word to check
     * @return true if the word is in the dictionary and unused, false otherwise
     */
    public boolean isWord(String word) {
        return dictionary.containsKey(word);
    }

    /**
     * Get a random word from the dictionary.
     * 
     * @return a random word from the dictionary
     */
    public String randomWord() {
        int index = (int) (dictionary.size() * Math.random());

        return (String) dictionary.keySet().toArray()[index];
    }

    /**
     * Get the size of the dictionary.
     * 
     * @return the size of the dictionary
     */
    public int size() {
        return dictionary.size();
    }

    /**
     * Generate a deep copy of the dictionary.
     * 
     * @return a deep copy of the dictionary
     */
    public Dictionary copy() {
        Dictionary copy = new Dictionary();

        for (String key : dictionary.keySet()) {
            copy.addWord(key);
        }

        return copy;
    }

    public void remove(String word) {
        dictionary.remove(word);
    }
}