/*
    Written by Ian Chen on 5/27/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper.logic;

import java.util.Random;

public class Board {
    private final byte[][] grid;

    private final byte MINE = -1;
    Difficulty difficulty;

    public Board(Difficulty d) {
        difficulty = d;
        grid = new byte[d.dimensions][d.dimensions];

        generateMines();
        setNumbers();
    }

    private void generateMines() {
        Random r = new Random();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (r.nextInt(100) < difficulty.mineRateOutOf100) {
                    grid[i][j] = MINE;
                }
            }
        }
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

    private boolean isMine(int row, int col) {
        return grid[row][col] == MINE;
    }

    private boolean inBounds(int row, int col) {
        return (row >= 0 && row < grid.length) && (col >= 0 && col < grid.length);
    }

    public byte getCell(int r, int c) {
        if (!inBounds(r, c)) {
            throw new IllegalArgumentException("Parameters not in bounds");
        }

        return grid[r][c];
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        for (byte[] line : grid) {
            for (byte b : line) {
                output.append(String.format("%4s", (b == MINE ? "*" : b)));
            }
            output.append("\n");
        }
        return output.toString();
    }
}
