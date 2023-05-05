import java.util.InputMismatchException;
import java.util.Scanner;

public class GameManager {

    Board currentBoard;

    public GameManager() {
        currentBoard = null;
    }

    public void createBoard() {
        try {
            System.out.print("Difficulty: ");
            Scanner s = new Scanner(System.in);
            Difficulty difficulty = Difficulty.values()[s.nextInt()];
            currentBoard = new Board(difficulty);
        } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Type a number between 1-4.");
            createBoard();
        }
    }
}
