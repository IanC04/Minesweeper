/*
    Started by Ian Chen on 6/24/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper.logic;

import java.io.Serializable;
import java.util.*;

final class GameState {

    private static final int[][] SURROUNDINGS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    private Board currentBoard;
    private final Difficulty difficulty;
    private final CellState[][] currentBoardState;
    private final List<MoveCommand> moves;
    private State currentState;
    private int shownCellsCount;
    private Set<GameManager.Cell> affectedCells;
    private int clickCount;

    enum State {
        GAME_OVER_LOSS, GAME_OVER_WIN, OPENED_SINGLE, NEW_GAME, OPENED_FLOOD, FLAGGED, NOOP
    }

    private static final class CellState {

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
        clickCount = 0;
    }

    /**
     * Requires initial clicked cell since Minesweeper flood opens the map after initial click
     *
     * @param rowClicked initial row
     * @param colClicked initial column
     */
    void setupBoard(int rowClicked, int colClicked) {
        currentBoard = new Board(difficulty, rowClicked, colClicked);
        Arrays.stream(currentBoardState).forEach(row -> Arrays.stream(row).forEach(cell -> cell.setFlagged(false)));
        currentState = State.NEW_GAME;
    }

    State processFlag(MoveCommand moveCommand) {
        moves.add(moveCommand);

        final boolean flaggedCell = cellIsFlagged(moveCommand.r(), moveCommand.c());
        final boolean shownCell = cellIsShowing(moveCommand.r(), moveCommand.c());

        if (!shownCell) {
            setFlagged(moveCommand.r(), moveCommand.c(), !flaggedCell);
            affectedCells = Set.of(new GameManager.Cell(moveCommand.r(), moveCommand.c()));
            return State.FLAGGED;
        }

        return State.NOOP;
    }

    State processSelect(MoveCommand moveCommand) {
        moves.add(moveCommand);

        final int r = moveCommand.r(), c = moveCommand.c();
        final boolean flaggedCell = cellIsFlagged(r, c);
        final boolean shownCell = cellIsShowing(r, c);

        if (!shownCell && !flaggedCell) {
            clickCount++;

            if (cellIsNumber(r, c) || cellIsMine(r, c)) {
                setFlagged(r, c, false);
                setShown(r, c, true);
                shownCellsCount++;
                affectedCells = Set.of(new GameManager.Cell(moveCommand.r(), moveCommand.c()));
                if (cellIsMine(r, c)) {
                    return State.GAME_OVER_LOSS;
                }
                if (shownCellsCount == currentBoard.getSize() - currentBoard.getMineCount()) {
                    return State.GAME_OVER_WIN;
                }

                return State.OPENED_SINGLE;
            }

            State state = floodShow(r, c);
            if (shownCellsCount == currentBoard.getSize() - currentBoard.getMineCount()) {
                return State.GAME_OVER_WIN;
            }
            return state;
        }

        return State.NOOP;
    }

    /**
     * Called when selected cell is not a number, so need to flood fill
     *
     * @param row
     * @param col
     * @return
     */
    private State floodShow(final int row, final int col) {
        final Queue<GameManager.Cell> cellsToOpen = new LinkedList<>();
        final GameManager.Cell initialCell = new GameManager.Cell(row, col);
        cellsToOpen.add(initialCell);

        affectedCells = new HashSet<>();
        affectedCells.add(initialCell);

        while (!cellsToOpen.isEmpty()) {
            final GameManager.Cell cell = cellsToOpen.poll();
            int r = cell.r(), c = cell.c();
            if (!inBounds(r, c) || cellIsShowing(r, c)) {
                continue;
            }
            affectedCells.add(cell);
            setShown(r, c, true);
            shownCellsCount++;
            setFlagged(r, c, false);
            if (cellIsNumber(r, c)) {
                continue;
            }

            Arrays.stream(SURROUNDINGS).forEach(pair -> {
                int sr = pair[0], sc = pair[1];
                cellsToOpen.offer(new GameManager.Cell(r + sr, c + sc));
            });
        }

        return State.OPENED_FLOOD;
    }

    void action(MoveCommand moveCommand) {
        affectedCells = Collections.emptySet();
        currentState = switch (moveCommand.moveType()) {
            case FLAG -> processFlag(moveCommand);
            case SELECT -> processSelect(moveCommand);
        };
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

    int getClickCount() {
        return clickCount;
    }

    Set<GameManager.Cell> getAffectedCells() {
        return Set.copyOf(affectedCells);
    }

    /**
     * @param r row
     * @param c column
     * @return if the cell is [1, 8]
     * @apiNote 0 is not a number but considered blank
     */
    private boolean cellIsNumber(int r, int c) {
        return currentBoard.isNumber(r, c);
    }

    boolean inBounds(int r, int c) {
        return currentBoard.inBounds(r, c);
    }

    Board getCurrentBoard() {
        return currentBoard;
    }

    State getCurrentState() {
        return currentState;
    }

    private void setFlagged(int r, int c, boolean flagged) {
        currentBoardState[r][c].setFlagged(flagged);
    }

    private void setShown(int r, int c, boolean shown) {
        currentBoardState[r][c].setShown(shown);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < currentBoardState.length; i++) {
            for (int j = 0; j < currentBoardState.length; j++) {
                Serializable cellData = cellIsShowing(i, j)
                        ? currentBoard.getCell(i,j) : "";
                cellData = cellIsFlagged(i, j) ? "#" : cellData;
                output.append(String.format("%4s", cellData));
            }
            output.append('\n');
        }
        return output.toString();
    }
}
