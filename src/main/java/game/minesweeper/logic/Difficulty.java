/*
    Written by Ian Chen on 5/27/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper.logic;

public enum Difficulty {
    BABY(5, 15, 1), EASY(10, 20, 2), MEDIUM(15, 25, 5), HARD(20, 30, 10), INSANE(25, 35, 10);

    private final int dimensions, mineRateOutOf100, minutesNeeded;

    Difficulty(int d, int p, int m) {
        this.dimensions = d;
        this.mineRateOutOf100 = p;
        this.minutesNeeded = m;
    }

    public int getDimensions() {
        return dimensions;
    }

    public int getMineRateOutOf100() {
        return mineRateOutOf100;
    }

    public int getMinutesNeeded() {
        return minutesNeeded;
    }

    @Override
    public String toString() {
        final String allCaps = super.toString();
        return allCaps.substring(0, 1).toUpperCase() + allCaps.substring(1).toLowerCase();
    }
}
