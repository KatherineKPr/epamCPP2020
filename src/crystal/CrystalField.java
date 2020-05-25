package crystal;

import com.company.PollingWindow;
import javafx.animation.*;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import replay.replayHandler;
import score.Score;

import java.io.IOException;
import java.util.Random;

import static java.lang.Math.abs;


public class CrystalField {

    public static final int ROW_NUMBER = 8;
    public static final int COL_NUMBER = 8;

    public static final Duration UPDATE_DURATION = Duration.millis(500);
    public static final Duration ROTATE_DURATION = Duration.millis(250);
    public static final int SLEEP_DURATION = 40;

    public static final int CRYSTAL_SIZE = 49;
    public static final int ANGLE = 360;

    Crystal[][] grid = new Crystal[ROW_NUMBER][COL_NUMBER];
    Score score = new Score();
    PollingWindow pollingWindow = new PollingWindow();
    int x0;
    int y0;
    int x;
    int y;
    int click = 0;
    int col;
    int row;
    replayHandler handler=new replayHandler();
    String crystalArrangement="";


    public void initialLayout(Parent root, int crystalNumber) {

        Random rand = new Random();

        GridPane playArea = (GridPane) root.lookup("#playArea");
        playArea.setStyle("-fx-max-height: 400px; -fx-max-width: 400px");

        Image img = new Image("/assets/images/gems.png");
        PixelReader reader = img.getPixelReader();

        for (row = 0; row < ROW_NUMBER; row++) {
            for (col = 0; col < COL_NUMBER; col++) {

                int newKind = rand.nextInt(crystalNumber);

                grid[row][col] = new Crystal();
                grid[row][col].rectangle = new Rectangle();


                grid[row][col].rectangle.setWidth(CRYSTAL_SIZE);
                grid[row][col].rectangle.setHeight(CRYSTAL_SIZE);

                WritableImage crystalIMG = new WritableImage(reader, CRYSTAL_SIZE * newKind, 0, CRYSTAL_SIZE, CRYSTAL_SIZE);

                grid[row][col].rectangle.setFill(new ImagePattern(crystalIMG));

                GridPane.setRowIndex(grid[row][col].rectangle, row);
                GridPane.setColumnIndex(grid[row][col].rectangle, col);

                grid[row][col].rectangle.setX(col);
                grid[row][col].rectangle.setY(row);


                grid[row][col].kind = newKind;
                grid[row][col].transparent = false;
                crystalArrangement+=grid[row][col].kind;

                playArea.getChildren().addAll(grid[row][col].rectangle);

                mouseClick(reader, grid[row][col].rectangle, root);


            }
        }
        handler.writeInList(crystalArrangement, score.getStringScore());
        crystalArrangement="";
        score.getTargetScore(root);
        constantFieldUpdate(reader, root, crystalNumber);


    }

    private void constantFieldUpdate(PixelReader reader, Parent root, int crystalNumber) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        score.updateScore(root);
                        findMatch();
                        deleteMatch();
                        updateField(reader, crystalNumber);
                    }
                }),
                new KeyFrame(
                        UPDATE_DURATION
                )
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void mouseClick(PixelReader reader, Rectangle rectangle, Parent root) {
        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent e) {

                click++;

                clickOnce(rectangle);

                clickTwice(reader, rectangle);

                findMatch();

                deleteMatch();

                secondSwap(reader);

                if (score.getScore() >= Score.getTargetScore()) {
                    try {
                        pollingWindow.createPollingWindow();

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }


            }


        });
    }

    private void clickOnce(Rectangle rectangle) {
        if (click == 1) {
            x0 = (int) rectangle.getX();
            y0 = (int) rectangle.getY();
            grid[y0][x0].rectangle.setEffect(new DropShadow());
        }
    }

    private void clickTwice(PixelReader reader, Rectangle rectangle) {
        if (click == 2) {

            x = (int) rectangle.getX();
            y = (int) rectangle.getY();

            if (abs(x - x0) + abs(y - y0) == 1) {

                swap(reader, grid[y0][x0], grid[y][x]);

                grid[y][x].swap = true;
                grid[y0][x0].swap = true;

            }
            click = 0;
            grid[y0][x0].rectangle.setEffect(null);
        }
    }

    private void findMatch() {
        for (row = 0; row < ROW_NUMBER; row++) {
            for (col = 0; col < COL_NUMBER; col++) {

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
        }
    }

    private void deleteMatch() {
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

    private void secondSwap(PixelReader reader) {
        if (grid[y][x].swap && grid[y0][x0].swap) {

            rotateCrystal(grid[y0][x0]);
            rotateCrystal(grid[y][x]);

            swap(reader, grid[y0][x0], grid[y][x]);

            grid[y0][x0].swap = false;
            grid[y][x].swap = false;
        }
    }

    private void swap(PixelReader reader, Crystal first, Crystal second) {
        int temp;

        WritableImage crystalIMG = new WritableImage(reader, CRYSTAL_SIZE * first.kind, 0, CRYSTAL_SIZE, CRYSTAL_SIZE);
        second.rectangle.setFill(new ImagePattern(crystalIMG));

        crystalIMG = new WritableImage(reader, CRYSTAL_SIZE * second.kind, 0, CRYSTAL_SIZE, CRYSTAL_SIZE);
        first.rectangle.setFill(new ImagePattern(crystalIMG));

        temp = first.kind;
        first.kind = second.kind;
        second.kind = temp;


    }

    private void rotateCrystal(Crystal crystal) {

        RotateTransition rotateTransition = new RotateTransition(ROTATE_DURATION, crystal.rectangle);
        rotateTransition.setByAngle(ANGLE);
        rotateTransition.setCycleCount(2);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();

    }

    private void updateField(PixelReader reader, int crystalNumber) {
        Random rand = new Random();
        Boolean updateFlag=false;//разделить на 2 функции

        for (row = 0; row < ROW_NUMBER; row++) {
            for (col = 0; col < COL_NUMBER; col++) {
                if (grid[row][col].transparent) {
                    updateFlag=true;
                }
            }
        }

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
                if(updateFlag){
                    crystalArrangement+=grid[row][col].kind;
                }

            }
        }

        if(updateFlag){
            handler.writeInList(crystalArrangement, score.getStringScore());
            crystalArrangement="";
        }

    }

    private void fadeCrystal(Crystal crystal) {

        FadeTransition fadeTransition = new FadeTransition(UPDATE_DURATION, crystal.rectangle);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.1);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();

    }


}