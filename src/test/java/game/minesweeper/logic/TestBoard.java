/*
    Started by Ian Chen on 6/12/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestBoard {

    private static final Random random = new Random();

    private Board testBoard;
    private int[] initialCoordinates;

    @BeforeEach
    public void setupBoard() {
        Difficulty d = Difficulty.BABY;
        initialCoordinates = new int[]{random.nextInt(d.getDimensions()), random.nextInt(d.getDimensions())};
        testBoard = new Board(d, initialCoordinates[0], initialCoordinates[1]);
    }

    @Test
    public void boardIsSquare() {
        byte[][] underlyingGrid = testBoard.getUnderlyingGrid();

        for (byte[] row : underlyingGrid) {
            assertEquals(row.length, underlyingGrid.length);
        }
    }

    @Test
    public void initialSquareIsBlank() {
        byte[][] underlyingGrid = testBoard.getUnderlyingGrid();

        for (int i = initialCoordinates[0] - 1; i <= initialCoordinates[0] + 1; i++) {
            for (int j = initialCoordinates[1] - 1; j <= initialCoordinates[1] + 1; j++) {
                if (testBoard.inBounds(i, j)) {
                    assertFalse(testBoard.isMine(i, j));
                }
                if (i == initialCoordinates[0] && j == initialCoordinates[1]) {
                    assertEquals(0, underlyingGrid[i][j]);
                }
            }
        }
    }
}
