package game.minesweeper;

import javafx.scene.image.Image;

import java.util.Map;
import java.util.Objects;

public enum Images {
    BOMB_ICON("images/jpg/bomb.jpg", "💣"),
    MINE("images/jpg/mine.jpg", "💥"),
    FLAG("images/jpg/flag.jpg", "🚩"),
    ZERO("images/jpg/0.jpg", "0"),
    ONE("images/jpg/1.jpg", "1"),
    TWO("images/jpg/2.jpg", "2"),
    THREE("images/jpg/3.jpg", "3"),
    FOUR("images/jpg/4.jpg", "4"),
    FIVE("images/jpg/5.jpg", "5"),
    SIX("images/jpg/6.jpg", "6"),
    SEVEN("images/jpg/7.jpg", "7"),
    EIGHT("images/jpg/8.jpg", "8");

    private final String text;
    private final Image image;

    public static final Map<String, Images> IMAGE_MAP = Map.of(
            "0", ZERO,
            "1", ONE,
            "2", TWO,
            "3", THREE,
            "4", FOUR,
            "5", FIVE,
            "6", SIX,
            "7", SEVEN,
            "8", EIGHT);

    Images(String path, String symbol) {
        image = new Image(Objects.requireNonNull(Application.class.getResourceAsStream(path)));
        text = symbol;
    }

    public Image getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}
