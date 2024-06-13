/*
    Written by Ian Chen on 5/27/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper.logic;

public enum Difficulty {
    EASY(20, 10), MEDIUM(30, 15), HARD(50, 20), INSANE(50, 40);

    final int dimensions, mineRateOutOf100;

    Difficulty(int d, int p) {
        this.dimensions = d;
        this.mineRateOutOf100 = p;
    }
}
