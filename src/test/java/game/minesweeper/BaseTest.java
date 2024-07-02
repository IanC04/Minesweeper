/*
    Started by Ian Chen on 7/9/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper;

import javafx.application.Platform;
import javafx.scene.robot.Robot;

public class BaseTest {

    Robot robot;

    public BaseTest() {
        Platform.startup(() -> robot = new Robot());
    }
}
