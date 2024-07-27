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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class AppController {
    @FXML
    private Button hint;
    @FXML
    private Label timerLabel;
    @FXML
    private ToggleButton timerControl;
    @FXML
    private StackPane panes;

    private Timer timer;

    public static final String HINT_NAME_FOR_LOOKUP = "#hint";
    public static final String TIMER_CONTROL_NAME_FOR_LOOKUP = "#timerControl";
    public static final String PANEL_NAME_FOR_LOOKUP = "#panes";

    @FXML
    private void initialize() throws IOException {
        timer = new Timer(timerLabel);
        timerControl.setText(Messages.getMessage(Messages.TIMER_OFF));
        timerControl.selectedProperty().addListener((
                (__, _1, newValue) -> {
                    timerControl.setText(
                            Messages.getMessage(newValue ? Messages.TIMER_ON : Messages.TIMER_OFF));
                    timerLabel.setVisible(newValue);
                }));

        loadWelcomePane();
    }

    @FXML
    private void openRepository() throws IOException, InterruptedException {
        final List<String[]> webApps = new ArrayList<>(Arrays.asList(
                new String[]{"cmd", "/c", "start", "microsoft-edge:https://github.com/IanC04/Minesweeper"},
                new String[]{"cmd", "/c", "start", "chrome", "https://github.com/IanC04/Minesweeper"}));

        int exitVal;
        do {
            Process process = Runtime.getRuntime().exec(webApps.removeLast());
            process.waitFor();
            exitVal = process.exitValue();
        }
        while (exitVal != 0 && !webApps.isEmpty());
    }

    @FXML
    private void changeSettings() {
        // TODO See what settings could be useful
    }

    @FXML
    private void loadWelcomePane() throws IOException {
        hint.setDisable(true);
        timer.reset();
        timerControl.setDisable(false);

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("welcome.fxml")));
        loader.setControllerFactory(__ -> new WelcomeController(timer));

        Parent pane = loader.load();
        panes.getChildren().clear();
        panes.getChildren().add(pane);
    }

    @FXML
    private void exit(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }
}