/*
    Started by Ian Chen on 7/26/2024
    GitHub: https://github.com/IanC04
 */

package game.minesweeper;

import game.minesweeper.logic.Difficulty;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;

import java.time.Duration;
import java.time.LocalTime;

public final class Timer extends AnimationTimer {

    private final Label label;
    private Duration remainingTime;
    private LocalTime end;
    private Difficulty difficulty;

    private final BooleanProperty timeEnded;

    public Timer(Label label) {
        this.label = label;
        this.difficulty = null;
        this.timeEnded = new SimpleBooleanProperty(false);
    }

    public Timer setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.remainingTime = Duration.ofMinutes(difficulty.getMinutesNeeded());
        this.label.setText(formatTime(remainingTime));
        return this;
    }

    public void startTimer() {
        end = LocalTime.now()
                .plusMinutes(difficulty.getMinutesNeeded())
                .plusSeconds(0);
        start();
    }

    public void reset() {
        stop();
        label.setText(null);
        remainingTime = Duration.ZERO;
        timeEnded.set(false);
    }

    public ReadOnlyBooleanProperty getTimeEnded() {
        return timeEnded;
    }

    @Override
    public void handle(long l) {
        remainingTime = Duration.between(LocalTime.now(), end);
        if (remainingTime.isPositive()) {
            label.setText(formatTime(remainingTime));
        } else {
            label.setText(formatTime(Duration.ZERO));
            timeEnded.set(true);
            stop();
        }
    }

    private String formatTime(Duration remaining) {
        return String.format("%02d:%02d",
                remaining.toMinutesPart(),
                remaining.toSecondsPart()
        );
    }
}
