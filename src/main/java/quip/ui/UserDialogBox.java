package quip.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Dialog box component for user messages.
 */
public class UserDialogBox extends DialogBox {
    private static final Image userImage = new Image(Objects.requireNonNull(UserDialogBox.class
            .getResourceAsStream("/images/user.png")));

    public UserDialogBox(String text) {
        super(text, new ImageView(userImage));
        getStyleClass().add("user-dialog-box");
        flip();
    }
}
