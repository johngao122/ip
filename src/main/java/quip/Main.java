package quip;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import quip.command.Command;
import quip.exception.QuipException;
import quip.parser.Parser;
import quip.ui.QuipDialogBox;
import quip.ui.UserDialogBox;

/**
 * Main application class for the Quip chat interface.
 * Handles the primary stage and scene setup.
 */
public class Main extends Application {
    private VBox dialogContainer;
    private TextField userInput;
    private ScrollPane scrollPane;
    private Quip quip;

    @Override
    public void start(Stage stage) {

        quip = new Quip();

        dialogContainer = new VBox();
        dialogContainer.setPadding(new Insets(10, 10, 10, 10));
        dialogContainer.setSpacing(10);

        scrollPane = new ScrollPane();
        scrollPane.setContent(dialogContainer);
        scrollPane.setFitToWidth(true);

        userInput = new TextField();
        userInput.setOnAction((event) -> handleUserInput());

        HBox inputArea = new HBox();
        inputArea.setAlignment(Pos.CENTER);
        inputArea.setPadding(new Insets(10));
        inputArea.getChildren().add(userInput);
        HBox.setHgrow(userInput, javafx.scene.layout.Priority.ALWAYS);

        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(scrollPane, inputArea);
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);

        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        stage.setTitle("Quip Chat");
        stage.setScene(scene);
        stage.setMinWidth(400);
        stage.setMinHeight(600);
        stage.show();

        showWelcomeMessage();
    }

    /**
     * Handles user input and sends it to Quip for processing.
     */
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (!input.isEmpty()) {

            dialogContainer.getChildren().add(new UserDialogBox(input));
            userInput.clear();

            try {
                Command command = Parser.parse(input);
                command.execute(quip.getTasks(), quip.getUi(), quip.getStorage());

                if(command.isExit()){
                    Platform.exit();
                }
            } catch (QuipException e) {
                dialogContainer.getChildren().add(new QuipDialogBox(e.getMessage()));
            }

            scrollPane.setVvalue(1.0);
        }
    }

    /**
     * Displays the welcome message when the application starts.
     */
    private void showWelcomeMessage() {
        String welcomeMessage = "Hi there mortal! I'm Quip!\n" +
                "What shenanigans can I help you with today?";
        dialogContainer.getChildren().add(new QuipDialogBox(welcomeMessage));
    }
}