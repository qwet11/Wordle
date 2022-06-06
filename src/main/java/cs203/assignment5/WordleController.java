package cs203.assignment5;

import java.io.IOException;

import cs203.assignment5.Wordle.FileHandler;
import cs203.assignment5.Wordle.Wordle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class is used to handle the Wordle GUI.
 * 
 * @author William Moss
 * @version 1.0
 */
public class WordleController {
    @FXML
    private GridPane outputGrid;
    @FXML
    private TextField userInput;
    @FXML
    private Text messageText;
    @FXML
    private MenuItem helpMenu;
    private Wordle wordle;
    private Timeline statsWindow;
    static String name;
    // NOTE: GREY, ORANGE, BLACK, and GREY will be used

    /**
     * Set the starting sequence of the scene.
     */
    public void initialize() {
        App.setSize(640, 600);

        helpMenu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                helpMenuClicked(event);
            }
        });

        this.wordle = new Wordle();
        messageText.setText("Make a 5-letter guess.");

        // Setup the output grid
        for (int row = 0; row < outputGrid.getRowCount(); row++) {
            for (int col = 0; col < outputGrid.getColumnCount(); col++) {
                StackPane stack = new StackPane();

                Rectangle rect = new Rectangle(60, 60);
                rect.setFill(Color.BLACK);

                Text text = new Text("");
                text.setFill(Color.WHITE);
                text.getFont();
                text.setFont(Font.font(30));

                stack.getChildren().addAll(rect, text);
                outputGrid.add(stack, col, row);
            }
        }

        // Setup game over screen
        gameOver();
    }

    /**
     * Create the enter key event handler.
     */
    @FXML
    public void onEnter(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            processGuess();
        } else {
            if (!messageText.getText().equals("")) {
                messageText.setText("");
            }
        }
    }

    /**
     * Create the help menu click event handler.
     * 
     * @param e The event.
     */
    @FXML
    public void helpMenuClicked(ActionEvent e) {
        System.out.println("Help menu clicked");
        Scene scene;
        try {
            scene = new Scene(App.loadFXML("wordle-help"), 418, 547);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Process the user's guess.
     */
    private void processGuess() {
        // Get guess
        String inputGuess = userInput.getText().toLowerCase();
        userInput.clear();

        // Check if guess is legal
        if (wordle.legalGuess(inputGuess)) {
            // Guess is legal. Generate response
            String[] guessResponse = wordle.guess(inputGuess);
            int guessNum = wordle.getCurrentGuesses() - 1;

            // Print response to grid
            for (Node node : outputGrid.getChildren()) {
                int row = GridPane.getRowIndex(node);
                if (row == guessNum) {
                    int col = GridPane.getColumnIndex(node);
                    StackPane stack = (StackPane) node;
                    ObservableList<Node> list = stack.getChildren();
                    Rectangle rect = (Rectangle) list.get(0);
                    Text text = (Text) list.get(1);

                    // Set color
                    if (guessResponse[col].equals("Wrong")) {
                        rect.setFill(Color.GREY);
                    } else if (guessResponse[col].equals("Correct")) {
                        rect.setFill(Color.GREEN);
                    } else if (guessResponse[col].equals("Right letter, but wrong position")) {
                        rect.setFill(Color.ORANGE);
                    }

                    // Set text
                    text.setText(inputGuess.charAt(col) + "");
                }
            }
        } else {
            messageText.setText("Invalid guess. Try again.");
        }
    }

    /**
     * Create the game over thread.
     * When the game is over, the statistics window will appear.
     */
    private void gameOver() {
        statsWindow = new Timeline(
                new KeyFrame(Duration.seconds(0.5),
                        new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                if (!wordle.isPlaying()) {
                                    System.out.println("Game over");
                                    FileHandler.saveResults(wordle, name);
                                    openStats();
                                }
                            }
                        }));
        statsWindow.setCycleCount(Timeline.INDEFINITE);
        statsWindow.play();
    }

    /**
     * Open the statistics window.
     */
    private void openStats() {
        // Open stats window
        Scene scene;
        try {
            scene = new Scene(App.loadFXML("statistic"), 515, 564);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        statsWindow.stop();

    }
}
