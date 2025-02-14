package quip.ui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * A dialog box component for displaying error messages.
 */
public class ErrorDialogBox extends DialogBox {

    private static final Image quipImage = new Image(Objects.requireNonNull(
            ErrorDialogBox.class.getResourceAsStream("/images/quip.png")));

    /**
     * Creates a new dialog box with the given error message.
     * @param errorMessage the error message to display
     */
    public ErrorDialogBox(String errorMessage) {
        super(errorMessage, new ImageView(quipImage));
        getStyleClass().addAll("error-dialog-box", "dialog-box");

        Label textLabel = (Label) getChildren().get(1);
        textLabel.getStyleClass().add("error-text");
    }
}
