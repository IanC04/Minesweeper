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

public final class GameManager {

    public record Cell(int r, int c) {
    }

    private final List<GameState> previousGames;

    private GameState currentGame;
    private Map<Cell, ObjectProperty<Image>> display;

    public GameManager() {
        previousGames = new ArrayList<>();
        currentGame = null;
        display = null;
    }

    public GameManager newGame(Difficulty d) {
        if (currentGame != null) {
            reset();
        }
        currentGame = new GameState(d);
        display = new HashMap<>(d.getDimensions() * d.getDimensions());
        return this;
    }

    private void reset() {
        Optional.of(currentGame).ifPresent(previousGames::add);
        currentGame = null;
    }

    public ObservableValue<Image> createDisplayBinding(int r, int c) {
        final Cell cell = new Cell(r, c);
        final ObjectProperty<Image> cellProperty = new SimpleObjectProperty<>();
        cellProperty.set(null);
        display.put(cell, cellProperty);
        return cellProperty;
    }

    public List<Cell> move(final int row, final int col) {
        if (beforeFirstClick()) {
            currentGame.setupBoard(row, col);
        }

        validateSelection(row, col);
        currentGame.action(new MoveCommand(row, col, MoveCommand.MoveType.SELECT));

        for (Cell cell : currentGame.getAffectedCells()) {
            ObjectProperty<Image> cellProperty = display.get(cell);
            final int r = cell.r(), c = cell.c();
            Image image = currentGame.cellIsMine(r, c) ?
                    Images.MINE.getImage() :
                    IMAGE_MAP.get(String.valueOf(currentGame.getCurrentBoard().getCell(r, c))).getImage();
            cellProperty.set(image);
        }

        return List.of(currentGame.getAffectedCells().toArray(new Cell[0]));
    }

    public void flag(int r, int c) {
        validateSelection(r, c);
        currentGame.action(new MoveCommand(r, c, MoveCommand.MoveType.FLAG));

        ObjectProperty<Image> cellProperty = display.get(new Cell(r, c));
        Image image = currentGame.cellIsFlagged(r, c) ? Images.FLAG.getImage() : null;
        cellProperty.set(image);
    }

    public boolean beforeFirstClick() {
        return currentGame.getClickCount() == 0;
    }

    public boolean afterFirstClick() {
        return currentGame.getClickCount() == 1;
    }

    public boolean gameOver() {
        return currentGame.getCurrentState().equals(GameState.State.GAME_OVER_WIN) ||
                currentGame.getCurrentState().equals(GameState.State.GAME_OVER_LOSS);
    }

    public boolean gameWon() {
        return currentGame.getCurrentState().equals(GameState.State.GAME_OVER_WIN);
    }

    public int getClickCount() {
        return currentGame.getClickCount();
    }

    public Cell getHint() {
        if (beforeFirstClick() || gameOver()) {
            return null;
        }

        return Hinter.getHint(currentGame);
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
        return currentGame.toString();
    }
}
