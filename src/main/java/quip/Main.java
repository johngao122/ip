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
import quip.storage.Storage;
import quip.task.TaskList;
import quip.ui.JavaFxUi;
import quip.ui.ReminderService;
import quip.ui.UserDialogBox;

import java.util.Objects;

/**
 * Main application class for the Quip chat interface.
 * Handles the primary stage and scene setup.
 */
public class   Main extends Application {
    private VBox dialogContainer;
    private TextField userInput;
    private ScrollPane scrollPane;
    private TaskList tasks;
    private Storage storage;
    private JavaFxUi ui;
    private ReminderService reminderService;

    @Override
    public void start(Stage stage) {
        initializeComponents();
        setupLayout(stage);
        initializeReminderService();
        ui.showWelcome();
    }

    @Override
    public void stop() {
        if (reminderService != null) {
            reminderService.stop();
        }
    }

    private void initializeComponents() {
        dialogContainer = new VBox();
        dialogContainer.setPadding(new Insets(10, 10, 10, 10));
        dialogContainer.setSpacing(10);

        scrollPane = new ScrollPane();
        scrollPane.setContent(dialogContainer);
        scrollPane.setFitToWidth(true);

        userInput = new TextField();
        userInput.setOnAction((event) -> handleUserInput());

        storage = new Storage();
        try {
            tasks = new TaskList(storage.load());
        } catch (QuipException e) {
            tasks = new TaskList();
        }
        ui = new JavaFxUi(dialogContainer);
    }

    private void setupLayout(Stage stage) {
        HBox inputArea = new HBox();
        inputArea.setAlignment(Pos.CENTER);
        inputArea.setPadding(new Insets(10));
        inputArea.getChildren().add(userInput);
        HBox.setHgrow(userInput, javafx.scene.layout.Priority.ALWAYS);

        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(scrollPane, inputArea);
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);

        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        stage.setTitle("Quip Chat");
        stage.setScene(scene);
        stage.setMinWidth(400);
        stage.setMinHeight(600);
        stage.show();
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
                command.execute(tasks, ui, storage);

                if (command.isExit()) {
                    Platform.exit();
                }
            } catch (QuipException e) {
                ui.showError(e.getMessage());
            }

            scrollPane.setVvalue(1.0);
        }
    }

    private void initializeReminderService() {
        reminderService = new ReminderService(tasks, (JavaFxUi) ui);
        reminderService.start();
    }

}
