package cs203.assignment5;

import java.io.IOException;

import cs203.assignment5.Wordle.FileHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * This class is used to handle the User GUI.
 * 
 * @author William Moss
 * @version 1.0
 */
public class UserController {
    @FXML
    private ListView<String> userList = new ListView<>();
    @FXML
    private MenuItem helpMenu;
    private String lastClickedUser;

    /**
     * Set the starting sequence of the scene.
     */
    public void initialize() {
        helpMenu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                helpMenuClicked(event);
            }
        });

        String[] users = FileHandler.getFolderContents();

        for (int i = 0; i < users.length; i++) {
            users[i] = users[i].split("\\.")[0];
        }

        ObservableList<String> list = FXCollections.observableArrayList(users);
        userList.setItems(list);
        userList.getItems().add(0, "Make a new user.");
    }

    /**
     * Create the click event handler.
     */
    @FXML
    public void onClick() {
        // Only select option if option is double clicked
        System.out.println("Clicked!");
        String user = userList.getSelectionModel().getSelectedItem();

        if (lastClickedUser == null) {
            lastClickedUser = user;
            return;
        } else {
            if (!lastClickedUser.equals(user)) {
                lastClickedUser = user;
                return;
            }
        }

        // Make a new user
        if (lastClickedUser.equals("Make a new user.")) {
            Popup popup = new Popup();
            popup.setAutoHide(true);

            Label label = new Label("Enter a username.");
            label.setMinWidth(120);
            label.setMinHeight(80);
            label.setFont(new Font(20));

            TextField textField = new TextField();

            Button button = new Button("Submit");
            button.setOnAction(e -> {
                String username = textField.getText();
                if (username.equals("")) {
                    popup.hide();
                    return;
                }

                FileHandler.createFile(username);
                userList.getItems().add(username);
                popup.hide();
            });

            VBox vBox = new VBox();
            vBox.setSpacing(10);
            vBox.setMinSize(180, 120);
            vBox.setPadding(new Insets(30));
            vBox.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));

            String cssLayout = "-fx-border-color: black;\n" +
                    "-fx-border-insets: 5;\n" +
                    "-fx-border-width: 3;\n";
            vBox.setStyle(cssLayout);
            vBox.getChildren().addAll(label, textField, button);

            popup.getContent().add(vBox);
            popup.show(App.getStage());
            lastClickedUser = null;
            return;
        }

        // User is selected
        lastClickedUser = null;
        WordleController.name = user;
        try {
            App.setRoot("wordle");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the help menu click event handler.
     * 
     * @param e
     */
    @FXML
    public void helpMenuClicked(ActionEvent e) {
        System.out.println("Help menu clicked");
        Scene scene;
        try {
            scene = new Scene(App.loadFXML("user-help"), 418, 161);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
