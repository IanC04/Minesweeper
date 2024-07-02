/*
    Written by Ian Chen on 5/27/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper.logic;

import game.minesweeper.Images;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

import java.text.MessageFormat;
import java.util.*;

import static game.minesweeper.Images.IMAGE_MAP;

public class GameManager {

    private record Cell(int r, int c) {
    }

    private final List<GameState> previousGames;
    private GameState currentGame;
    private Map<Cell, ObjectProperty<Image>> display;
    private boolean firstClick;

    public GameManager() {
        previousGames = new ArrayList<>();
        currentGame = null;
        display = null;
        firstClick = true;
    }

    public GameManager newGame(Difficulty d) {
        if (currentGame != null) {
            reset();
        }
        currentGame = new GameState(d);
        display = new HashMap<>(d.getDimensions() * d.getDimensions());
        firstClick = true;
        return this;
    }

    private void reset() {
        Optional.of(currentGame).ifPresent(previousGames::add);
        currentGame = null;
    }

    public ObservableValue<Image> createDisplayBinding(int r, int c) {
        final Cell cell = new Cell(r, c);
        final ObjectProperty<Image> cellProperty = new SimpleObjectProperty<>();
        display.put(cell, cellProperty);
        return cellProperty;
    }

    public void move(int r, int c) {
        if (firstClick) {
            currentGame.setupBoard(r, c);
            firstClick = false;
            display.forEach(((__, imageShown) -> imageShown.set(null)));
            showInitialCells(r, c);
            return;
        }

        validateSelection(r, c);
        currentGame.action(new MoveCommand(r, c, MoveCommand.MoveType.CLICK));

        ObjectProperty<Image> cellProperty = display.get(new Cell(r, c));
        Image image = currentGame.cellIsMine(r, c) ?
                Images.MINE.getImage() :
                IMAGE_MAP.get(String.valueOf(currentGame.getCurrentBoard().getCell(r, c))).getImage();
        cellProperty.set(image);

        GameState.State newState = currentGame.getCurrentState();
        // TODO Implement game end logic
        switch (newState) {
            case GAME_OVER_LOSS -> System.out.println("Game Over");
            case GAME_OVER_WIN -> System.out.println("Winner");
        }
    }

    public void flag(int r, int c) {
        validateSelection(r, c);
        currentGame.action(new MoveCommand(r, c, MoveCommand.MoveType.FLAG));

        ObjectProperty<Image> cellProperty = display.get(new Cell(r, c));
        Image image = currentGame.cellIsFlagged(r, c) ? Images.FLAG.getImage() : null;
        cellProperty.set(image);
    }

    private void showInitialCells(int r, int c) {
        if (!currentGame.inBounds(r, c) || currentGame.cellIsShowing(r, c)) {
            return;
        }

        currentGame.action(new MoveCommand(r, c, MoveCommand.MoveType.CLICK));

        ObjectProperty<Image> cellProperty = display.get(new Cell(r, c));
        Image image = IMAGE_MAP.get(String.valueOf(currentGame.getCurrentBoard().getCell(r, c))).getImage();
        cellProperty.set(image);

        if (currentGame.cellIsNumber(r, c)) {
            return;
        }

        showInitialCells(r - 1, c - 1);
        showInitialCells(r - 1, c);
        showInitialCells(r - 1, c + 1);
        showInitialCells(r, c - 1);
        showInitialCells(r, c + 1);
        showInitialCells(r + 1, c - 1);
        showInitialCells(r + 1, c);
        showInitialCells(r + 1, c + 1);
    }

    private void validateSelection(int r, int c) {
        final boolean valid = !currentGame.cellIsShowing(r, c);
        if (!valid) {
            throw new IllegalStateException(
                    MessageFormat.format("[{0}, {1}] already selected", r, c));
        }
    }

    @Override
    public String toString() {
        return currentGame.getCurrentBoard().toString();
    }
}
