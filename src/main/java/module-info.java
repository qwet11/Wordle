module cs203.assignment5 {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    opens cs203.assignment5 to javafx.fxml;

    exports cs203.assignment5;
}