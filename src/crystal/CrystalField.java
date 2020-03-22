package crystal;

import  javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;


public class CrystalField {
    Crystal [][] grid=new Crystal[10][10];
    boolean isSwap = false, isMoving = false;
    int x0, y0, x, y, generalScore = 0,click = 0, winScore = 7000,ts=54;
    //Vector2i pos;

    void swap(Crystal first, Crystal second){
        Crystal third=first;

        third.col=first.col;
        first.col=second.col;
        second.col=third.col;

        third.row=first.row;
        first.row=second.row;
        second.row=third.row;

        grid[first.row][first.col] = first;//надо перезаписать не только объекты,но и грид
        grid[second.row][second.col] = second;

    }

    void initial_layout(){
        for (int i = 1; i <= 8; i++)
            for (int j = 1; j <= 8; j++)
            {
              //  grid[i][j].kind = rand() % 3;//сколько эл-тов будет на первоначальном экране
                grid[i][j].col = j;
                grid[i][j].row = i;
                grid[i][j].x = j * ts;//умножаем,чтобы получить истинную координату,учитывая размеры поля
                grid[i][j].y = i * ts;
            }
    }

    void draw_field() throws MalformedURLException {
        File file=new File("assets/images/gems.png");
        String localURL=file.toURI().toURL().toString();

        Image myGems=new Image(localURL);

        for (int i = 1; i <= 8; i++)
            for (int j = 1; j <= 8; j++)
            {
                Crystal activeCrystal = grid[i][j];

            }
    }
}
