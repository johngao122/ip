package quip.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 * Base class for dialog boxes in the chat interface.
 * Provides common functionality for both user and bot messages.
 */
public class DialogBox extends HBox {
    private static final double SPACING = 10;
    private static final double PADDING = 10;
    private static final double MAX_WIDTH = 400;
    private static final double IMAGE_SIZE = 60;

    protected DialogBox(String text, ImageView displayPicture) {
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(SPACING);
        setPadding(new Insets(PADDING));

        displayPicture.setFitHeight(IMAGE_SIZE);
        displayPicture.setFitWidth(IMAGE_SIZE);
        displayPicture.setPreserveRatio(true);
        displayPicture.setSmooth(true);
        displayPicture.setCache(true);
        displayPicture.setCacheHint(CacheHint.QUALITY);

        StackPane imageContainer = new StackPane(displayPicture);
        imageContainer.getStyleClass().add("image-container");
        imageContainer.setPrefSize(IMAGE_SIZE, IMAGE_SIZE);
        imageContainer.setMinSize(IMAGE_SIZE, IMAGE_SIZE);
        imageContainer.setMaxSize(IMAGE_SIZE, IMAGE_SIZE);

        Label textLabel = new Label(text);
        textLabel.setWrapText(true);
        textLabel.setMaxWidth(MAX_WIDTH);

        getChildren().addAll(displayPicture, textLabel);
    }

    /**
     * Flips the dialog box for user messages.
     */
    protected void flip() {
        setAlignment(Pos.CENTER_RIGHT);
        ImageView displayPicture = (ImageView) getChildren().remove(0);
        Label textLabel = (Label) getChildren().remove(0);
        getChildren().addAll(textLabel, displayPicture);
    }
}
