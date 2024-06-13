/*
    Written by Ian Chen on 5/27/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper;

import game.minesweeper.logic.Difficulty;
import game.minesweeper.logic.GameManager;
import static game.minesweeper.logic.Difficulty.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameController {
    @FXML
    public Button easyMode;
    @FXML
    public Button mediumMode;
    @FXML
    public Button hardMode;

    @FXML
    private Label startGame;

    public void initialize() {
        easyMode.setOnAction(e -> onStartButtonClick(EASY));
        mediumMode.setOnAction(e -> onStartButtonClick(MEDIUM));
        hardMode.setOnAction(e -> onStartButtonClick(HARD));
        hardMode.setOnAction(e -> onStartButtonClick(INSANE));
    }

    @FXML
    protected void onStartButtonClick(Difficulty difficulty) {
        System.out.println("Starting Game");
        GameManager game = new GameManager(difficulty);
        game.newGame();
        System.out.println(game.printBoard());
        startGame.setText("Welcome to Minesweeper!");
    }
}