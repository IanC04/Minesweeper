/*
    Started by Ian Chen on 7/14/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper;

import java.util.ResourceBundle;

public final class Messages {

    private static final String FILE_PATH = "game/minesweeper/text";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(FILE_PATH);

    public static final String MINESWEEPER = "MINESWEEPER";
    public static final String WELCOME_TO_MINESWEEPER = "WELCOME_TO_MINESWEEPER";
    public static final String UNABLE_TO_LOAD = "UNABLE_TO_LOAD";
    public static final String GAME_OVER_WIN = "GAME_OVER_WIN";
    public static final String GAME_OVER_LOST = "GAME_OVER_LOST";
    public static final String CONTINUE = "CONTINUE";
    public static final String START = "START";
    public static final String MODE = "MODE";
    public static final String TIMER_ON = "TIMER_ON";
    public static final String TIMER_OFF = "TIMER_OFF";

    public static String getMessage(final String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}
