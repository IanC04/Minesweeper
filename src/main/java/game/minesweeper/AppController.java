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

public final class AppController {
    @FXML
    private Label timer;
    @FXML
    private ToggleButton timerControl;
    @FXML
    private StackPane panes;

    public static String getPaneNameForLookup() {
        return "#panes";
    }

    @FXML
    private void initialize() throws IOException {
        timer.visibleProperty().bind(timerControl.selectedProperty());
        timerControl.setText(Messages.getMessage(Messages.TIMER_OFF));
        timerControl.selectedProperty().addListener((
                (__, _1, newValue) -> timerControl.setText(
                        Messages.getMessage(newValue ? Messages.TIMER_ON : Messages.TIMER_OFF))));
        loadWelcomePane();
    }

    @FXML
    private void openRepository() throws IOException {
        Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome https://github.com/IanC04/Minesweeper"});
    }

    @FXML
    private void getHint() {
    }

    @FXML
    private void changeSettings() {
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