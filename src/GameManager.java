import java.util.InputMismatchException;
import java.util.Scanner;

public class GameManager {

    Board currentBoard;

    public GameManager() {
        currentBoard = null;
    }

    public void createBoard() {
        try {
            System.out.print("Choose a difficulty between 0-3 inclusive: ");
            Scanner s = new Scanner(System.in);
            Difficulty difficulty = Difficulty.values()[s.nextInt()];
            currentBoard = new Board(difficulty);
        } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Type a number between 0-3.");
            createBoard();
        }
    }

    public String printBoard() {
        return currentBoard.toString();
    }
}
