package gippy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main running class for GUI
 */
public class Main extends Application {
    private final Gippy gippy = new Gippy("data/gippy.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Gippy");
            stage.setMinHeight(220);
            stage.setMinWidth(450);
            fxmlLoader.<MainWindow>getController().setGippy(gippy);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
