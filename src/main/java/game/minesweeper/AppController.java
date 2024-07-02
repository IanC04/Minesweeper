/*
    Written by Ian Chen on 5/27/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AppController {
    @FXML
    private Label timer;
    @FXML
    private ToggleButton timer_control;
    @FXML
    private StackPane panes;

    public static String getPaneNameForLookup() {
        return "#panes";
    }

    public void initialize() throws IOException {
        timer.visibleProperty().bind(timer_control.selectedProperty());
        loadWelcomePane();
    }

    @FXML
    private void openRepository() throws IOException {
        Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome https://github.com/IanC04/Minesweeper"});
    }

    @FXML
    private void loadWelcomePane() throws IOException {
        Parent pane =
                FXMLLoader.load(Objects.requireNonNull(getClass().getResource("welcome.fxml")));
        panes.getChildren().clear();
        panes.getChildren().add(pane);
    }

    @FXML
    private void exit(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }
}