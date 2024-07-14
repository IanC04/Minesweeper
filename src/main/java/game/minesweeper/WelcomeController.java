/*
    Started by Ian Chen on 6/13/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper;

import game.minesweeper.logic.Difficulty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Objects;

public final class WelcomeController {
    @FXML
    private HBox modes;
    @FXML
    private Label welcomeInfo;

    @FXML
    private void initialize() {
        for (Difficulty d : Difficulty.values()) {
            Button b = new Button(d.toString());
            b.setOnAction(event -> onStartButtonClick(event, d));
            modes.getChildren().add(b);
        }
    }

    @FXML
    private void onStartButtonClick(ActionEvent event, Difficulty difficulty) {
        welcomeInfo.setText("Welcome to Minesweeper!");
        try {
            changePaneToBoard(event, difficulty);
        } catch (IOException exception) {
            welcomeInfo.setText("Unable to load game!");
        }
    }

    private void changePaneToBoard(ActionEvent event, Difficulty difficulty) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("game.fxml")));
        loader.setControllerFactory((c) -> new GameController(difficulty));
        Parent pane = loader.load();

        StackPane parent =
                (StackPane) ((Node) event.getSource()).getScene().lookup(AppController.getPaneNameForLookup());
        parent.getChildren().clear();
        parent.getChildren().add(pane);
    }
}
