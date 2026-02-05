package gippy;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Gippy gippy;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private final Image gippyImage = new Image(this.getClass().getResourceAsStream("/images/gippy.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Gippy instance */
    public void setGippy(Gippy g) {
        gippy = g;
        dialogContainer.getChildren().add(
                DialogBox.getGippyDialog(gippy.printHello(), gippyImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String userText = userInput.getText();

        if (userText.trim().equals("bye")) {
            Platform.exit();
        }

        String gippyText = gippy.handleResponse(userText);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getGippyDialog(gippyText, gippyImage)
        );
        userInput.clear();
    }
}
