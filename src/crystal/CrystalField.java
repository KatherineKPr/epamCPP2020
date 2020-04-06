package crystal;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;

import static java.lang.Math.abs;


public class CrystalField {

    Crystal[][] grid = new Crystal[8][8];
    int x0;
    int y0;
    int x;
    int y;
    int click = 0;
    int col;
    int row;


    public void initialLayout(Parent root) {

        Random rand = new Random();

        GridPane playArea = (GridPane) root.lookup("#playArea");
        playArea.setStyle("-fx-max-height: 400px; -fx-max-width: 400px");

        Image img = new Image("/assets/images/gems.png");
        PixelReader reader = img.getPixelReader();


        for (row = 0; row < 8; row++) {
            for (col = 0; col < 8; col++) {

                int newKind = rand.nextInt(7);

                Rectangle rec = new Rectangle();
                rec.setWidth(49);
                rec.setHeight(49);

                WritableImage crystalIMG = new WritableImage(reader, 49 * newKind, 0, 49, 49);

                rec.setFill(new ImagePattern(crystalIMG));

                GridPane.setRowIndex(rec, row);
                GridPane.setColumnIndex(rec, col);

                rec.setX(col);
                rec.setY(row);

                grid[row][col] = new Crystal();
                grid[row][col].rectangle = rec;
                grid[row][col].kind = newKind;

                System.out.println("" + grid[row][col].rectangle + "" + grid[y0][x0].kind);

                playArea.getChildren().addAll(rec);

                mouseClick(reader, rec);

            }
        }

    }

    private void mouseClick(PixelReader reader, Rectangle rec) {
        rec.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent e) {

                // rec.setFill(Color.BLACK);
                click++;
                System.out.println("" + click);

                clickOnce(rec);

                clickTwice(reader, rec);

                findMatch();

                for (row = 0; row < 8; row++) {
                    for (col = 0; col < 8; col++) {
                        System.out.println("" + grid[row][col].match);
                    }
                }

                deleteMatch();


                //second swap
                for (row = 0; row < 8; row++) {
                    for (col = 0; col < 8; col++) {//здесь i,j-как col,row,они свапнулись
                        System.out.println("" + col + row + " " + grid[row][col].swap);
                    }
                }

                secondSwap(reader);

                //update
                       /* for (int i = 7; i >= 0; i--)
                            for (int j = 0; j < 8; j++)
                                if (grid[i][j].match == 1)
                                    for (int n = i; n >= 0; n--)
                                        if (grid[n][j].match == 0) {
                                            // swap(grid[n][j], grid[i][j]);
                                            int temp;
                                            WritableImage crystalIMG = new WritableImage(reader, 49 * grid[i][j].kind, 0, 49, 49);
                                            grid[n][j].rectangle.setFill(new ImagePattern(crystalIMG));

                                            crystalIMG = new WritableImage(reader, 49 * grid[n][j].kind, 0, 49, 49);
                                            grid[i][j].rectangle.setFill(new ImagePattern(crystalIMG));

                                            temp = grid[i][j].kind;
                                            grid[i][j].kind = grid[n][j].kind;
                                            grid[n][j].kind = temp;
                                            break;
                                        }
                        ;//перемещение на место нижнего эл-та
                        // int n = 0;
                        for (int j = 0; j < 8; j++)
                            for (int i = 7, n = 0; i >= 0; i--)
                                if (grid[i][j].match == 1) {
                                    grid[i][j].rectangle.setOpacity(1.0);
                                    grid[i][j].kind = rand.nextInt(7);
                                    WritableImage crystalIMG = new WritableImage(reader, 49 * grid[i][j].kind, 0, 49, 49);
                                    grid[i][j].rectangle.setFill(new ImagePattern(crystalIMG));
                                    // grid[row][col].y = -ts * n++;
                                    grid[i][j].match = 0;
                                    //  grid[row][col].alpha = 255;//возвращение прозрачности
                                    //  grid[i][j].rectangle.setOpacity(1.0);
                                }*/

            }


        });
    }

    private void clickOnce(Rectangle rec) {
        if (click == 1) {
            x0 = (int) rec.getX();
            y0 = (int) rec.getY();
            System.out.println("" + rec.getX() + " " + rec.getY());
        }
    }

    private void clickTwice(PixelReader reader, Rectangle rec) {
        if (click == 2) {
            int temp;
            x = (int) rec.getX();
            y = (int) rec.getY();
            System.out.println("" + rec.getX() + " " + rec.getY());

            if (abs(x - x0) + abs(y - y0) == 1) {//только одна координата может отличаться на 1

                swap(reader, grid[y0][x0], grid[y][x]);

                grid[y][x].swap = true; //флажок
                grid[y0][x0].swap = true;
                //click = 0;//нельзя выбрать более двух эл-тов

            }
            click = 0;
        }
    }

    private void findMatch() {
        for (row = 0; row < 8; row++) {
            for (col = 0; col < 8; col++) {//здесь i,j-как col,row,они свапнулись

                if (row != 7 && row != 0) {
                    if (grid[row][col].kind == grid[row + 1][col].kind) {//совпадение в столбце
                        if (grid[row][col].kind == grid[row - 1][col].kind) {
                            for (int n = -1; n <= 1; n++) {//можно было и без цикла присвоить i-1,i,i+1 значения, так красиивее
                                if (!grid[row + n][col].match) {
                                    grid[row + n][col].match=true;//для каждого совпадения
                                }
                            }
                        }
                    }
                }

                if (col != 7 && col != 0) {
                    if (grid[row][col].kind == grid[row][col + 1].kind) {//совпадение в строке
                        if (grid[row][col].kind == grid[row][col - 1].kind) {
                            for (int n = -1; n <= 1; n++) {
                                if (!grid[row][col + n].match) {
                                    grid[row][col + n].match=true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void deleteMatch() {
        for (row = 0; row < 8; row++) {
            for (col = 0; col < 8; col++) {//здесь i,j-как col,row,они свапнулись
                if (grid[row][col].match) {
                    grid[row][col].rectangle.setOpacity(0.0);
                    grid[row][col].swap = false;
                    //grid[row][col].match=0;
                }
            }
        }
    }

    private void secondSwap(PixelReader reader) {
        if (grid[y][x].swap && grid[y0][x0].swap) {

            swap(reader, grid[y0][x0], grid[y][x]);

            grid[y0][x0].swap = false;//если нет совпадения меняем обратно,скидываем свапы
            grid[y][x].swap = false;//если нет совпадения меняем обратно,скидываем свапы
        }
    }

    private void swap(PixelReader reader, Crystal first, Crystal second) {
        int temp;
        WritableImage crystalIMG = new WritableImage(reader, 49 * first.kind, 0, 49, 49);
        second.rectangle.setFill(new ImagePattern(crystalIMG));

        crystalIMG = new WritableImage(reader, 49 * second.kind, 0, 49, 49);
        first.rectangle.setFill(new ImagePattern(crystalIMG));

        temp = first.kind;
        first.kind = second.kind;
        second.kind = temp;
    }


}


