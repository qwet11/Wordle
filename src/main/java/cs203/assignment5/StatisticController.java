package cs203.assignment5;

import cs203.assignment5.Wordle.Statistic;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * This class is used to handle the Statistic GUI.
 * 
 * @author William Moss
 * @version 1.0
 */
public class StatisticController {
    @FXML
    private Text numPlayed;
    @FXML
    private Text numWinRatio;
    @FXML
    private Text numCurrentStreak;
    @FXML
    private Text numMaxStreak;

    @FXML
    private Text guessDistCount1;
    @FXML
    private Text guessDistCount2;
    @FXML
    private Text guessDistCount3;
    @FXML
    private Text guessDistCount4;
    @FXML
    private Text guessDistCount5;
    @FXML
    private Text guessDistCount6;
    private Text[] guessDistCounts = new Text[6];

    @FXML
    private Rectangle guessDistBar1;
    @FXML
    private Rectangle guessDistBar2;
    @FXML
    private Rectangle guessDistBar3;
    @FXML
    private Rectangle guessDistBar4;
    @FXML
    private Rectangle guessDistBar5;
    @FXML
    private Rectangle guessDistBar6;
    private Rectangle[] guessDistBars = new Rectangle[6];

    /**
     * Set the starting sequence of the scene.
     */
    public void initialize() {
        System.out.println("Stats");
        Statistic stat = new Statistic(WordleController.name);

        setStats(stat);
        setDist(stat);
    }

    /**
     * Update the GUI with the user's statistics.
     * 
     * @param stat the stats of the user
     */
    private void setStats(Statistic stat) {
        numPlayed.setText(String.valueOf(stat.getNumPlayed()));
        numWinRatio.setText(String.valueOf(stat.percentGuessedCorrectly()));
        numCurrentStreak.setText(String.valueOf(stat.getCurrentStreak()));
        numMaxStreak.setText(String.valueOf(stat.getMaxStreak()));
    }

    /**
     * Update the GUI with the user's guess distribution.
     * 
     * @param stat the stats of the user
     */
    private void setDist(Statistic stat) {
        guessDistCounts[0] = guessDistCount1;
        guessDistCounts[1] = guessDistCount2;
        guessDistCounts[2] = guessDistCount3;
        guessDistCounts[3] = guessDistCount4;
        guessDistCounts[4] = guessDistCount5;
        guessDistCounts[5] = guessDistCount6;

        guessDistBars[0] = guessDistBar1;
        guessDistBars[1] = guessDistBar2;
        guessDistBars[2] = guessDistBar3;
        guessDistBars[3] = guessDistBar4;
        guessDistBars[4] = guessDistBar5;
        guessDistBars[5] = guessDistBar6;

        for (int i = 0; i < guessDistCounts.length; i++) {
            int count = stat.numWordsWithNumGuesses(i + 1);
            guessDistCounts[i].setText(String.valueOf(count));

            if (count > 0) {
                guessDistBars[i].setWidth(30 * (count + 1));
                guessDistBars[i].setFill(Color.GREEN);
                guessDistCounts[i].setX(guessDistCounts[i].getX() + (30 * count));
            }

        }
    }
}
