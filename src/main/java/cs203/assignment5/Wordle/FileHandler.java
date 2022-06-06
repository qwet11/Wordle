package cs203.assignment5.Wordle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to handle the file management of the program.
 * 
 * @author William Moss
 * @version 1.0
 */
public class FileHandler {
    private final static File folder = new File(".\\src\\main\\java\\cs203\\assignment5\\Wordle\\users");

    /**
     * Make a new user folder.
     */
    static void makeFolder() {
        folder.mkdir();
    }

    /**
     * Create a new user file.
     * 
     * @param name the name of the user
     */
    public static void createFile(String name) {
        // Create a new file
        String filePath = userFolderPath(name);

        try {
            FileWriter writer = new FileWriter(filePath);
            writer.append("Current Streak:0\n");
            writer.append("Max Streak:0\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Checks if the user file exists.
     * 
     * @param name the name of the user
     * @return true if the file exists, false otherwise
     */
    static boolean fileExists(String name) {
        // Check if file exists
        String filePath = userFolderPath(name);
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * Checks if the user folder exists.
     * 
     * @return true if the folder exists, false otherwise
     */
    static boolean folderExists() {
        // Check if folder exists
        return folder.exists();
    }

    /**
     * Get the path of the user file.
     * 
     * @param name the name of the user
     * @return the path of the user file
     */
    static String getFilePath(String name) {
        // Get file path
        String filePath = userFolderPath(name);
        return filePath;
    }

    /**
     * Get the contents of the user file.
     * 
     * @return the contents of the user file in the format [name, currentStreak,
     *         maxStreak, words...]
     */
    public static String[] getFolderContents() {
        // Get folder contents
        String[] contents = folder.list();
        return contents;
    }

    /**
     * Get the words the user already played.
     * 
     * @param name the name of the user
     * @return the words the user already played
     */
    static HashMap<String, Integer> getUsedWords(String name) {
        // Get the words used by previous Wordle games
        HashMap<String, Integer> usedWords = null;
        try {
            String filePath = userFolderPath(name);
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            // Remove the stored statistics
            lines.remove(0);
            lines.remove(0);

            // Create a hashmap to store the words
            usedWords = new HashMap<>();
            for (String line : lines) {
                String[] split = line.split(":");
                usedWords.put(split[0], Integer.parseInt(split[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usedWords;
    }

    /**
     * Get the current streak of the user.
     * 
     * @param name the name of the user
     * @return the current streak of the user
     */
    static int getCurrentStreak(String name) {
        // Get current streak
        try {
            String filePath = userFolderPath(name);
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            return Integer.parseInt(lines.get(0).split(":")[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1; // Should not happen
    }

    /**
     * Get the max streak of the user.
     * 
     * @param name the name of the user
     * @return the max streak of the user
     */
    static int getMaxStreak(String name) {
        // Get max streak
        try {
            String filePath = userFolderPath(name);
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            return Integer.parseInt(lines.get(1).split(":")[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1; // Should not happen
    }

    /**
     * Update the used words of the user.
     * 
     * @param name    the name of the user
     * @param word    the word the user played
     * @param guesses the number of guesses the user made
     */
    static void updateUsedWords(String name, String word, int guesses) {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(userFolderPath(name), true));
            output.append(word + ":" + guesses);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the current streak of the user.
     * 
     * @param name the name of the user
     */
    static void updateCurrentStreak(String name) {
        // Update current streak
        int currentStreak = getCurrentStreak(name);
        replaceSelected(name, "Current Streak", Integer.toString(currentStreak), Integer.toString(currentStreak + 1));
    }

    /**
     * Update the max streak of the user.
     * 
     * @param name the name of the user
     */
    static void updateMaxStreak(String name) {
        // Update max streak
        int maxStreak = getMaxStreak(name);
        replaceSelected(name, "Max Streak", Integer.toString(maxStreak), Integer.toString(maxStreak + 1));
    }

    /**
     * Get the path of the user folder.
     * 
     * @param name the name of the user
     * @return the path of the user folder
     */
    private static String userFolderPath(String name) {
        return folder.toString() + "/" + name.toLowerCase() + ".txt";
    }

    // Courtesy of StackOverflow
    /**
     * Replace the selected line in the file.
     * 
     * @param name        the name of the user
     * @param section     the section of the file to replace
     * @param original    the original line
     * @param replacement the replacement line
     */
    private static void replaceSelected(String name, String section, String original, String replacement) {
        try {
            // input the file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader(userFolderPath(name)));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
            String inputStr = inputBuffer.toString();

            // logic to replace lines in the string (could use regex here to be generic)
            inputStr = inputStr.replace(section + ":" + original, section + ":" + replacement);

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(userFolderPath(name));
            fileOut.write(inputStr.getBytes());
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reset the user's current streak to 0
     * 
     * @param name the name of the user
     */
    static void resetCurrentStreak(String name) {
        int currentStreak = getCurrentStreak(name);
        replaceSelected(name, "Current Streak", Integer.toString(currentStreak), "0");
    }

    /**
     * Save the results of the user's game
     * 
     * @param name   the name of the user
     * @param wordle the wordle game object
     */
    public static void saveResults(Wordle wordle, String name) {
        if (wordle.hasWon()) {
            // Won. Update current streak and max streak if applicable
            FileHandler.updateCurrentStreak(name);

            if (FileHandler.getCurrentStreak(name) > FileHandler.getMaxStreak(name)) {
                FileHandler.updateMaxStreak(name);
            }
        } else {
            // Lost. Reset current streak
            FileHandler.resetCurrentStreak(name);
        }

        int guess = (wordle.hasWon()) ? wordle.getCurrentGuesses() : -1;
        FileHandler.updateUsedWords(name, wordle.getCurrentWord(), guess);
    }

}
