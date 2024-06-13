/*
    Written by Ian Chen on 5/27/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper.logic;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GameManager {

    private static class GameState {

        private final Board currentBoard;
        private final List<byte[][]> moves;

        private GameState(Difficulty d) {
            currentBoard = new Board(d);
            moves = new ArrayList<>();
        }

        private Board getCurrentBoard() {
            return currentBoard;
        }
    }

    GameState currentGame;

    public GameManager(Difficulty difficulty) {
        currentGame = null;
    }

    public void newGame() {
        try {
            System.out.print("Choose a difficulty between 0-3 inclusive: ");
            Scanner s = new Scanner(System.in);
            Difficulty difficulty = Difficulty.values()[s.nextInt()];
            currentGame = new GameState(difficulty);
        } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Type a number between 0-3.");
            newGame();
        }
    }

    public String printBoard() {
        return currentGame.getCurrentBoard().toString();
    }
}
