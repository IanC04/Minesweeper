/*
    Started by Ian Chen on 6/12/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper.logic;

import game.minesweeper.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBoard extends BaseTest {

    private Board testBoard;

    public TestBoard() {
        super();
    }

    @BeforeEach
    public void setupBoard() {
        testBoard = new Board(Difficulty.BABY);
    }

    @Test
    public void testBoardIsSquare() {
        byte[][] underlyingGrid = testBoard.getUnderlyingGrid();
        for (byte[] row : underlyingGrid) {
            assertEquals(row.length, underlyingGrid.length);
        }
    }
}
