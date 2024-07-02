/*
    Started by Ian Chen on 6/12/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper;

import game.minesweeper.logic.Difficulty;
import game.minesweeper.logic.GameManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.text.MessageFormat;

public class GameController {
    @FXML
    private Label game_info;
    @FXML
    private GridPane boardPane;

    private final Difficulty difficulty;
    private final GameManager gameManager;

    public GameController(Difficulty difficulty) {
        this.difficulty = difficulty;
        gameManager = new GameManager().newGame(difficulty);
    }

    public void initialize() {
        game_info.setText(MessageFormat.format("{0} Mode", difficulty.toString()));

        for (int r = 0; r < difficulty.getDimensions(); r++) {
            for (int c = 0; c < difficulty.getDimensions(); c++) {
                final Rectangle tile = new Rectangle(25, 25);
                tile.setFill(Color.BURLYWOOD);
                tile.setStroke(Color.BLACK);
                tile.setArcWidth(5);
                tile.setArcHeight(5);
                final int finalR = r, finalC = c;
                tile.setOnMouseClicked(event -> clickTile(event, finalR, finalC));
                final ImageView imageView = new ImageView();
                imageView.setMouseTransparent(true);
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                imageView.imageProperty().bind(gameManager.createDisplayBinding(r, c));
                boardPane.add(new StackPane(tile, imageView), r, c);
            }
        }
    }

    void clickTile(MouseEvent event, int r, int c) {
        switch (event.getButton()) {
            case PRIMARY -> {
                gameManager.move(r, c);
                Node target = (Node) event.getTarget();
                target.setMouseTransparent(true);
            }
            case SECONDARY -> gameManager.flag(r, c);
        }
    }
}
