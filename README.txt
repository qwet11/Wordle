This project allows different users to play Wordle. 

When the main executable is run, the user is taken to a user selection screen. Once a user is selected, the user is allowed to play Wordle. 

If the user wants to play Wordle again, the executable must be run again.


To run the executable, open the terminal in the same directory as the wordle_v2.jar file and run the command:

java -jar --module-path [PATH_TO_JAVAFX] --add-modules=javafx.controls, javafx.fxml wordle_v2.jar

Replace [PATH_TO_JAVAFX] with the path to the installed JavaFX lib. See example-execution-command.txt for an example.


For easy execution, I copied my version of JavaFX in the project. The executable run-wordle.bat can be run to open the project. Feel free to change the extension to run-wordle.txt to see what is written inside to ensure no malicious code.