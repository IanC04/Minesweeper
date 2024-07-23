/*
    Written by Ian Chen on 5/27/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper.logic;

import java.util.HashSet;
import java.util.Set;

class Hinter {
    /**
     * Gets the tile to click for the player
     *
     * @param currentGame current game state
     * @return the cell to choose for the next move
     */
    static GameManager.Cell getHint(GameState currentGame) {
        return getHintInternal(currentGame);
    }

    /**
     * @param currentGame current game state
     * @return a safe edge cell if possible
     * @implNote Gets a safe non-showing cell adjacent to an already shown one
     */
    private static GameManager.Cell getHintInternal(GameState currentGame) {
        Set<GameManager.Cell> edgeCells = getEdgeCells(currentGame);

        for (GameManager.Cell cell : edgeCells) {
            if (!currentGame.cellIsMine(cell.r(), cell.c())) {
                return cell;
            }
        }

        return null;
    }

    private static Set<GameManager.Cell> getEdgeCells(GameState currentGame) {
        Set<GameManager.Cell> cells = new HashSet<>();

        for (int i = 0; i < currentGame.getCurrentBoard().getLength(); i++) {
            for (int j = 0; j < currentGame.getCurrentBoard().getLength(); j++) {
                if (hasShownNeighbor(currentGame, i, j) && !currentGame.cellIsShowing(i, j)) {
                    cells.add(new GameManager.Cell(i, j));
                }
            }
        }

        return cells;
    }

    private static boolean hasShownNeighbor(GameState currentGame, int r, int c) {
        for (int i = r - 1; i <= r + 1; i++) {
            for (int j = c - 1; j <= c + 1; j++) {
                if (i == r && j == c) {
                    continue;
                }
                if (currentGame.inBounds(i, j) && currentGame.cellIsShowing(i, j)) {
                    return true;
                }
            }
        }

        return false;
    }
}