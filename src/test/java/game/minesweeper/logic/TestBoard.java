/*
    Started by Ian Chen on 6/12/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestBoard {

    private static final Random random = new Random();

    private Board testBoard;

    @BeforeEach
    public void setupBoard() {
        testBoard = randomBoardWithDifficulty(Difficulty.BABY);
    }

    @ParameterizedTest
    @EnumSource(Difficulty.class)
    public void boardSizeEqualsDifficulty(Difficulty difficulty) {
        testBoard = randomBoardWithDifficulty(difficulty);
        assertEquals(testBoard.getSize(), testBoard.getLength() * testBoard.getLength());
    }

    @Test
    public void initialSquareIsBlankWithNoMineNeighbors() {
        assertFalse(testBoard.isNumber(testBoard.getInitialRow(), testBoard.getInitialColumn()));

        for (int i = testBoard.getInitialRow() - 1; i <= testBoard.getInitialRow() + 1; i++) {
            for (int j = testBoard.getInitialColumn() - 1; j <= testBoard.getInitialColumn() + 1; j++) {
                if (testBoard.inBounds(i, j)) {
                    assertFalse(testBoard.isMine(i, j));
                }
            }
        }
    }

    private Board randomBoardWithDifficulty(Difficulty d) {
        return new Board(d, random.nextInt(d.getDimensions()), random.nextInt(d.getDimensions()));
    }
}
