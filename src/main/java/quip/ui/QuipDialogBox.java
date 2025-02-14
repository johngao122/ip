package quip.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Dialog box component for Quip's messages.
 */
public class QuipDialogBox extends DialogBox {
    private static final Image quipImage = new Image(Objects.requireNonNull(
            QuipDialogBox.class.getResourceAsStream("/images/quip.png")));

    public QuipDialogBox(String text) {
        super(text, new ImageView(quipImage));
        getStyleClass().add("quip-dialog-box");
    }
}