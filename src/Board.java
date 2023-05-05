public class Board {
    public Board(Difficulty diff) {
        switch (diff) {
            case EASY -> {

            }
            case MEDIUM -> {
            }
            case HARD -> {
            }
            case INSANE -> {
            }
            default -> throw new IllegalStateException(
                    "Invalid difficulty when creating the minesweeper board.");
        }
    }
}
