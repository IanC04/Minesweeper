import java.util.Random;

public class Board {
    int rowSize, columnSize, mineProbability;
    byte[][] grid;

    private final byte MINE = -1;
    Difficulty difficulty;

    public Board(Difficulty d) {
        switch (d) {
            case EASY -> {
                rowSize = columnSize = 20;
                mineProbability = 10;
            }
            case MEDIUM -> {
                rowSize = columnSize = 20;
                mineProbability = 50;
            }
            case HARD -> {
                rowSize = columnSize = 40;
                mineProbability = 10;
            }
            case INSANE -> {
                rowSize = columnSize = 40;
                mineProbability = 50;
            }
            default -> throw new IllegalStateException(
                    "Invalid difficulty when creating the minesweeper board.");
        }
        difficulty = d;
        createGrid();
        generateMines();
        setNumbers();
    }

    private void createGrid() {
        grid = new byte[rowSize][columnSize];
    }

    private void generateMines() {
        Random r = new Random();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                if (r.nextInt(100) < mineProbability) {
                    grid[i][j] = MINE;
                }
            }
        }
    }

    private void setNumbers() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
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
        return (row >= 0 && row < rowSize) && (col >= 0 && col < columnSize);
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
