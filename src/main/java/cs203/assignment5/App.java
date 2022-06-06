package cs203.assignment5;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is used to start the program GUI.
 * 
 * @author William Moss
 * @version 1.0
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    /**
     * Set the starting sequence of the program's GUI.
     * 
     * @param inStage the stage of the program
     */
    @Override
    public void start(Stage inStage) throws IOException {
        stage = inStage;
        scene = new Scene(loadFXML("user"), 496, 381);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Get the stage of the program.
     * 
     * @return the stage of the program
     */
    static Stage getStage() {
        return stage;
    }

    /**
     * Set the root of the scene.
     * 
     * @param fxml the fxml file to load
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Set the size of the stage.
     * 
     * @param width  the width of the stage
     * @param height the height of the stage
     */
    static void setSize(int width, int height) {
        stage.setWidth(width);
        stage.setHeight(height);
    }

    /**
     * Load the fxml file.
     * 
     * @param fxml the fxml file to load
     * @return the root of the fxml file
     */
    static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}