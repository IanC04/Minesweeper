/*
    Started by Ian Chen on 6/12/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper;

import game.minesweeper.logic.Difficulty;
import game.minesweeper.logic.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.text.MessageFormat;
import java.util.List;

public final class GameController {
    @FXML
    private Label gameInfo;
    @FXML
    private GridPane boardPane;

    private final Difficulty difficulty;
    private final GameManager gameManager;

    public GameController(Difficulty difficulty) {
        this.difficulty = difficulty;
        gameManager = new GameManager().newGame(difficulty);
    }

    @FXML
    private void initialize() {
        gameInfo.setText(MessageFormat.format(Messages.getMessage(Messages.MODE), difficulty.toString()));

        for (int r = 0; r < difficulty.getDimensions(); r++) {
            for (int c = 0; c < difficulty.getDimensions(); c++) {
                final Rectangle rectangle = new Rectangle(25, 25);
                rectangle.setMouseTransparent(true);
                rectangle.setFill(Color.BURLYWOOD);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(5);
                rectangle.setArcHeight(5);

                final ImageView imageView = new ImageView();
                imageView.setMouseTransparent(true);
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                imageView.imageProperty().bind(gameManager.createDisplayBinding(r, c));

                final StackPane tile = new StackPane(rectangle, imageView);
                final int finalR = r, finalC = c;
                tile.setOnMouseClicked(event -> clickTile(event, finalR, finalC));
                // GridPane is column then row index
                boardPane.add(tile, c, r);
            }
        }
    }

    void clickTile(MouseEvent event, int r, int c) {
        switch (event.getButton()) {
            case PRIMARY -> {
                List<GameManager.Cell> cellsTurnedShown = gameManager.move(r, c);
                cellsTurnedShown.forEach(cell -> boardPane.getChildrenUnmodifiable().get(
                        cell.r() * difficulty.getDimensions() + cell.c()).setMouseTransparent(true));
            }
            case SECONDARY -> gameManager.flag(r, c);
        }

        if (gameManager.gameOver()) {
            boardPane.setMouseTransparent(true);
            if (gameManager.gameWon()) {
                gameInfo.setText(Messages.getMessage(Messages.GAME_OVER_WIN));
            } else {
                gameInfo.setText(Messages.getMessage(Messages.GAME_OVER_LOST));
            }
        } else {
            if (gameManager.wasFirstClick()) {
                gameInfo.setText(Messages.getMessage(Messages.START));
            } else {
                gameInfo.setText(MessageFormat.format(Messages.getMessage(Messages.CONTINUE),
                        gameManager.getClickCount()));
            }
        }
    }
}
