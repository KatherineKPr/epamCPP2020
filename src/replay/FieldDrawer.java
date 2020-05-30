package replay;

import crystal.Crystal;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import score.Score;
import java.io.FileReader;
import java.io.IOException;


public class FieldDrawer {
    public static final int ROW_NUMBER = 8;
    public static final int COL_NUMBER = 8;
    public static final Duration UPDATE_DURATION = Duration.millis(500);
    public static final int CRYSTAL_SIZE = 49;
    Crystal[][] grid = new Crystal[ROW_NUMBER][COL_NUMBER];
    Score score = new Score();
    int col;
    int row;
    int i = 1;

    public void initialLayout(Parent root,String fileName) throws IOException, ParseException, InterruptedException {

        JSONParser parser = new JSONParser();
        fileName="info\\"+fileName;
        Object obj = parser.parse(new FileReader(fileName));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray listScore = (JSONArray) jsonObject.get("Score:");
        JSONArray listArrangement = (JSONArray) jsonObject.get("Arrangement:");
        GridPane playArea = (GridPane) root.lookup("#playArea");
        playArea.setStyle("-fx-max-height: 400px; -fx-max-width: 400px");
        Image img = new Image("/assets/images/gems.png");
        PixelReader reader = img.getPixelReader();
        for (row = 0; row < ROW_NUMBER; row++) {
            for (col = 0; col < COL_NUMBER; col++) {
                int newKind = listArrangement.get(0).toString().charAt(row * 8 + col) - 48;
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
                playArea.getChildren().addAll(grid[row][col].rectangle);
                mouseClick(reader, grid[row][col].rectangle, listArrangement, listScore, root);
            }
        }
        score.updateReplayScore(root, listScore.get(0).toString());
        score.getTargetScore(root);
    }

    private void mouseClick(PixelReader reader, Rectangle rectangle, JSONArray listArrangement, JSONArray listScore, Parent root) {
        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                try {
                    if (i == listArrangement.size()) {
                        WarningMessage warningMessage = new WarningMessage();
                        warningMessage.createWarningMessage();
                        return;
                    }
                    updateField(reader, listArrangement, listScore, root);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    private void updateField(PixelReader reader, JSONArray listArrangement, JSONArray listScore, Parent root) {
        score.updateReplayScore(root, listScore.get(i).toString());
        for (row = 0; row < ROW_NUMBER; row++) {
            for (col = 0; col < COL_NUMBER; col++) {
                int newKind = listArrangement.get(i).toString().charAt(row * 8 + col) - 48;
                int oldKind=listArrangement.get(i-1).toString().charAt(row * 8 + col) - 48;
                WritableImage crystalIMG = new WritableImage(reader, CRYSTAL_SIZE * newKind, 0, CRYSTAL_SIZE, CRYSTAL_SIZE);
                grid[row][col].rectangle.setFill(new ImagePattern(crystalIMG));
                grid[row][col].kind = newKind;
                grid[row][col].transparent = false;
                if(newKind!=oldKind) {
                    fadeCrystal(grid[row][col]);
                }
            }
        }
        i++;
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