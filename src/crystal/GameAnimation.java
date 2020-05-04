package crystal;

import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import score.Score;

import java.util.Random;

public class GameAnimation implements Runnable {
    public static final int ROW_NUMBER = 8;
    public static final int COL_NUMBER = 8;
    public static final int CRYSTAL_SIZE = 49;
    public static final int SLEEP_DURATION = 40;
    public static final Duration UPDATE_DURATION = Duration.millis(500);
    Server server;
    Score score;
    PixelReader reader;
    int crystalNumber;
    int col;
    int row;
    Crystal[][] grid;
    boolean isActive;

    public GameAnimation(Server server, Score score, PixelReader reader, int crystalNumber, Crystal[][] grid) {
        isActive = true;
        this.server = server;
        this.score = score;
        this.reader = reader;
        this.crystalNumber = crystalNumber;
        this.grid = grid;
    }

    public void disable() {
        isActive = false;
    }

    @Override
    public void run() {
        while (isActive) {
            server.handleAnimation();
            findMatch();
            deleteMatch();
            updateField(reader, crystalNumber);
        }

    }

    public void findMatch() {
        for (row = 0; row < ROW_NUMBER; row++) {
            for (col = 0; col < COL_NUMBER; col++) {
                matchInColumn();
                matchInRow();
            }
        }
    }

    public void matchInColumn() {
        if (row != ROW_NUMBER - 1 && row != 0) {
            if (grid[row][col].kind == grid[row + 1][col].kind) {
                if (grid[row][col].kind == grid[row - 1][col].kind) {
                    for (int n = -1; n <= 1; n++) {
                        grid[row + n][col].match = true;
                        score.incrementScore();
                    }
                }
            }
        }
    }

    public void matchInRow() {
        if (col != COL_NUMBER - 1 && col != 0) {
            if (grid[row][col].kind == grid[row][col + 1].kind) {
                if (grid[row][col].kind == grid[row][col - 1].kind) {
                    for (int n = -1; n <= 1; n++) {
                        grid[row][col + n].match = true;
                        score.incrementScore();
                    }
                }
            }
        }
    }

    public void deleteMatch() {
        for (row = 0; row < ROW_NUMBER; row++) {
            for (col = 0; col < COL_NUMBER; col++) {
                if (grid[row][col].match) {
                    sleep().setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            grid[row][col].rectangle.setFill(null);
                        }
                    });
                    grid[row][col].transparent = true;
                    grid[row][col].swap = false;
                    grid[row][col].match = false;
                }
            }
        }
    }

    Task<Void> sleep() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(SLEEP_DURATION);
                } catch (InterruptedException interruptedException) {
                    interruptedException.getMessage();
                }
                return null;
            }
        };
    }

    public void updateField(PixelReader reader, int crystalNumber) {
        Random rand = new Random();
        for (row = 0; row < ROW_NUMBER; row++) {
            for (col = 0; col < COL_NUMBER; col++) {
                if (grid[row][col].transparent) {
                    int newKind = rand.nextInt(crystalNumber);
                    WritableImage crystalIMG = new WritableImage(reader, CRYSTAL_SIZE * newKind, 0, CRYSTAL_SIZE, CRYSTAL_SIZE);
                    grid[row][col].rectangle.setFill(new ImagePattern(crystalIMG));
                    grid[row][col].kind = newKind;
                    grid[row][col].transparent = false;
                    fadeCrystal(grid[row][col]);
                }
            }
        }
    }

    public void fadeCrystal(Crystal crystal) {
        FadeTransition fadeTransition = new FadeTransition(UPDATE_DURATION, crystal.rectangle);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.1);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }


}
