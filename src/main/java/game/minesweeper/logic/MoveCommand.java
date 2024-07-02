/*
    Written by Ian Chen on 5/26/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper.logic;

record MoveCommand(int r, int c, MoveType moveType) {
    enum MoveType {
        FLAG, CLICK
    }
}
