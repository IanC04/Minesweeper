/*
    Written by Ian Chen on 5/27/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public final class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        final Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

        final FXMLLoader gameFxmlLoader = new FXMLLoader(
                Application.class.getResource("app.fxml"));
        final double minWidth = screenSize.getWidth() * 0.5,
                minHeight = screenSize.getHeight() * 0.75;
        final Scene welcomeScene = new Scene(gameFxmlLoader.load(), minWidth, minHeight);

        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);
        stage.setX((screenSize.getWidth() - minWidth) / 2);
        stage.setY((screenSize.getHeight() - minHeight) / 2);

        stage.setTitle(Messages.getMessage(Messages.MINESWEEPER));
        stage.getIcons().add(Images.BOMB_ICON.getImage());
        stage.requestFocus();
        stage.setScene(welcomeScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}