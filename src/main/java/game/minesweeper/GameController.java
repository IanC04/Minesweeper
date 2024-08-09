/*
    Started by Ian Chen on 6/12/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper;

import game.minesweeper.logic.Difficulty;
import game.minesweeper.logic.GameManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.text.MessageFormat;
import java.util.List;

public final class GameController {

    private static final int TILE_SIZE = 25;
    private static final Bloom EFFECT = new Bloom();
    private static final double HOVER_OPACITY = 0.8;

    @FXML
    private Label gameInfo;
    @FXML
    private GridPane boardPane;

    private final Difficulty difficulty;
    private final GameManager gameManager;
    private final Timer timer;
    private final boolean useTimer;

    private Node tileWasHighlight;

    public GameController(Difficulty difficulty, Button hintButton, Timer timer, boolean useTimer) {
        this.difficulty = difficulty;
        this.gameManager = new GameManager().newGame(difficulty);
        this.tileWasHighlight = null;
        this.timer = timer.setDifficulty(difficulty);
        this.useTimer = useTimer;

        hintButton.setDisable(false);
        hintButton.setOnAction(event -> getHint());

        if (useTimer) {
            timer.getTimeEnded().addListener((__, oldValue, newValue) -> {
                if (newValue) {
                    manageGameOver();
                }
            });
        }
    }

    private void getHint() {
        resetHighlightedTile();

        GameManager.Cell cellToHighlight = gameManager.getHint();
        if (cellToHighlight != null) {
            Node tileToHighlight = getBoardNode(cellToHighlight.r(), cellToHighlight.c());
            tileToHighlight.setEffect(EFFECT);
            tileWasHighlight = tileToHighlight;
        }
    }

    private void resetHighlightedTile() {
        if (tileWasHighlight != null) {
            tileWasHighlight.setEffect(null);
        }
    }

    @FXML
    private void initialize() {
        gameInfo.setText(MessageFormat.format(Messages.getMessage(Messages.MODE), difficulty.toString()));

        for (int r = 0; r < difficulty.getDimensions(); r++) {
            for (int c = 0; c < difficulty.getDimensions(); c++) {
                final Rectangle rectangle = new Rectangle(TILE_SIZE, TILE_SIZE, Color.BURLYWOOD);
                rectangle.setMouseTransparent(true);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(5);
                rectangle.setArcHeight(5);

                final ImageView imageView = new ImageView();
                imageView.setMouseTransparent(true);
                imageView.setFitWidth(TILE_SIZE);
                imageView.setFitHeight(TILE_SIZE);
                imageView.imageProperty().bind(gameManager.createDisplayBinding(r, c));

                final StackPane tile = new StackPane(rectangle, imageView);
                final int finalR = r, finalC = c;
                tile.opacityProperty().bind(tile.hoverProperty().map(x -> x ? HOVER_OPACITY : 1));
                tile.setOnMouseClicked(event -> clickTile(event, finalR, finalC));
                // GridPane is column then row index
                boardPane.add(tile, c, r);
            }
        }
    }

    void clickTile(MouseEvent event, int r, int c) {
        resetHighlightedTile();

        switch (event.getButton()) {
            case PRIMARY -> {
                List<GameManager.Cell> cellsTurnedShown = gameManager.move(r, c);
                cellsTurnedShown.forEach(cell -> getBoardNode(cell.r(), cell.c()).setMouseTransparent(true));

                if (useTimer && gameManager.afterFirstClick()) {
                    timer.startTimer();
                }
            }
            case SECONDARY -> gameManager.flag(r, c);
        }

        if (gameManager.gameOver()) {
            manageGameOver();
        } else {
            manageGameContinue();
        }
    }

    private void manageGameOver() {
        boardPane.setMouseTransparent(true);
        if (gameManager.gameWon()) {
            gameInfo.setText(Messages.getMessage(Messages.GAME_OVER_WIN));
        } else {
            gameInfo.setText(Messages.getMessage(Messages.GAME_OVER_LOST));
        }
    }

    private void manageGameContinue() {
        if (gameManager.afterFirstClick()) {
            gameInfo.setText(Messages.getMessage(Messages.START));
        } else {
            gameInfo.setText(MessageFormat.format(Messages.getMessage(Messages.CONTINUE),
                    gameManager.getClickCount()));
        }
    }

    private Node getBoardNode(int r, int c) {
        return boardPane.getChildrenUnmodifiable().get(r * difficulty.getDimensions() + c);
    }
}