/*
    Written by Ian Chen on 5/27/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper.logic;

import java.util.Random;

final class Board {
    private static final byte MINE = -1;

    private final byte[][] grid;
    private final Difficulty difficulty;
    private final int mineCount;

    Board(Difficulty d, int rowSelected, int colSelected) {
        grid = new byte[d.getDimensions()][d.getDimensions()];
        difficulty = d;

        mineCount = generateMines(rowSelected, colSelected);
        setNumbers();
    }

    byte getCell(int r, int c) {
        if (!inBounds(r, c)) {
            throw new IllegalArgumentException("Parameters not in bounds");
        }

        return grid[r][c];
    }

    boolean isMine(int row, int col) {
        return getCell(row, col) == MINE;
    }

    boolean isNumber(int row, int col) {
        return getCell(row, col) > 0;
    }

    int getSize() {
        return difficulty.getDimensions() * difficulty.getDimensions();
    }

    int getMineCount() {
        return mineCount;
    }

    boolean inBounds(int row, int col) {
        return (row >= 0 && row < grid.length) && (col >= 0 && col < grid.length);
    }

    /**
     * @return the underlying grid
     * @apiNote returned grid is modifiable
     */
    byte[][] getUnderlyingGrid() {
        return grid;
    }

    private int generateMines(int rowSeed, int colSeed) {
        int mineCount = 0;
        Random r = new Random();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (Math.abs(rowSeed - i) <= 1 && Math.abs(colSeed - j) <= 1) {
                    continue;
                }
                if (r.nextInt(100) < difficulty.getMineRateOutOf100()) {
                    grid[i][j] = MINE;
                    mineCount++;
                }
            }
        }

        return mineCount;
    }

    private void setNumbers() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (!isMine(i, j)) {
                    grid[i][j] = calculateSurroundings(i, j);
                }
            }
        }
    }

    private byte calculateSurroundings(int row, int col) {
        byte sum = 0;
        for (int i = row - 1; i < row + 2; i++) {
            for (int j = col - 1; j < col + 2; j++) {
                if (inBounds(i, j) && isMine(i, j)) {
                    sum++;
                }
            }
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        for (byte[] line : grid) {
            for (byte b : line) {
                output.append(String.format("%4s", (b == MINE ? "*" : b)));
            }
            output.append('\n');
        }
        return output.toString();
    }
}
