/*
    Started by Ian Chen on 6/24/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GameState {

    private Board currentBoard;
    private final Difficulty difficulty;
    private final CellState[][] currentBoardState;
    private final List<MoveCommand> moves;
    private State currentState;
    private int shownCells;

    enum State {
        GAME_OVER_LOSS, GAME_OVER_WIN, CONTINUE
    }

    private static class CellState {

        private boolean isFlagged() {
            return flagged;
        }

        private boolean isShown() {
            return shown;
        }

        private boolean flagged, shown;

        private CellState() {
            flagged = false;
            shown = false;
        }

        private void setFlagged(boolean flagged) {
            this.flagged = flagged;
        }

        private void setShown(boolean shown) {
            this.shown = shown;
        }
    }


    GameState(Difficulty d) {
        difficulty = d;
        currentBoardState = new CellState[d.getDimensions()][d.getDimensions()];
        for (CellState[] row : currentBoardState) {
            for (int i = 0; i < row.length; i++) {
                row[i] = new CellState();
            }
        }
        moves = new ArrayList<>();
    }

    /**
     * Requires initial clicked cell since Minesweeper flood opens the map after initial click
     * @param rowClicked
     * @param colClicked
     */
    void setupBoard(int rowClicked, int colClicked) {
        currentBoard = new Board(difficulty, rowClicked, colClicked);
        Arrays.stream(currentBoardState).forEach(row -> Arrays.stream(row).forEach(cell -> cell.setFlagged(false)));
    }

    void action(MoveCommand moveCommand) {
        final boolean flaggedCell = cellIsFlagged(moveCommand.r(), moveCommand.c());
        final boolean shownCell = cellIsShowing(moveCommand.r(), moveCommand.c());
        currentState =switch (moveCommand.moveType()) {
            case FLAG -> {
                if (!shownCell) {
                    currentBoardState[moveCommand.r()][moveCommand.c()].setFlagged(!flaggedCell);
                }
                yield State.CONTINUE;
            }
            case CLICK -> {
                if (!shownCell) {
                    currentBoardState[moveCommand.r()][moveCommand.c()].setFlagged(false);
                    currentBoardState[moveCommand.r()][moveCommand.c()].setShown(true);
                    shownCells++;
                    if (cellIsMine(moveCommand.r(), moveCommand.c())) {
                        yield State.GAME_OVER_LOSS;
                    }
                    if (shownCells == currentBoard.getSize() - currentBoard.getMineCount()) {
                        yield State.GAME_OVER_WIN;
                    }
                }
                yield State.CONTINUE;
            }
        };
        moves.add(moveCommand);
    }

    boolean cellIsFlagged(int r, int c) {
        return currentBoardState[r][c].isFlagged();
    }

    boolean cellIsShowing(int r, int c) {
        return currentBoardState[r][c].isShown();
    }

    boolean cellIsMine(int r, int c) {
        return currentBoard.isMine(r, c);
    }

    /**
     * @apiNote 0 is not a number but considered blank
     * @param r
     * @param c
     * @return
     */
    boolean cellIsNumber(int r, int c) {
        return currentBoard.isNumber(r, c);
    }

    boolean inBounds(int r, int c) {
        return currentBoard.inBounds(r ,c);
    }

    Board getCurrentBoard() {
        return currentBoard;
    }

    State getCurrentState() {
        return currentState;
    }
}
