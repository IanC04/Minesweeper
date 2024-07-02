/*
    Written by Ian Chen on 5/27/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper.logic;

public enum Difficulty {
    BABY(5, 15), EASY(10, 20), MEDIUM(15, 25), HARD(20, 30), INSANE(25, 35);

    private final int dimensions, mineRateOutOf100;

    Difficulty(int d, int p) {
        this.dimensions = d;
        this.mineRateOutOf100 = p;
    }

    public int getDimensions() {
        return dimensions;
    }

    public int getMineRateOutOf100() {
        return mineRateOutOf100;
    }

    @Override
    public String toString() {
        final String allCaps = super.toString();
        return allCaps.substring(0, 1).toUpperCase() + allCaps.substring(1).toLowerCase();
    }
}
