package crystal;

import com.company.PollingWindow;

import java.io.IOException;

import static java.lang.Math.abs;

public class GamePredictor implements Runnable {
    Server server;
    PollingWindow pollingWindow = new PollingWindow();
    public static final int ROW_NUMBER = 8;
    public static final int COL_NUMBER = 8;
    Crystal[][] grid;
    int x0;
    int y0;
    int x;
    int y;
    boolean isActive;
    public boolean chance = false;

    public GamePredictor(Server server, Crystal[][] grid) {
        isActive = true;
        this.server = server;
        this.grid = grid;
    }

    public void disable() {
        isActive = false;
    }

    @Override
    public void run() {
        while (isActive) {
            server.handleGameLogic();
            predictMatch();
            if(!chance){
                try {
                    pollingWindow.createPollingWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("" + chance);
            chance = false;
        }
    }

    public void predictMatch() {
        for (y = 0; y < ROW_NUMBER; y++) {
            for (x = 0; x < COL_NUMBER; x++) {
                matchInColumn();
                matchInRow();
            }
        }
    }

    public void matchInRow() {
        if (x != COL_NUMBER - 1 && x != 0) {
            if (grid[y][x].kind == grid[y][x + 1].kind || grid[y][x].kind == grid[y][x - 1].kind) {
                for (y0 = 0; y0 < ROW_NUMBER; y0++) {
                    for (x0 = 0; x0 < COL_NUMBER; x0++) {
                        if (grid[y][x].kind == grid[y0][x0].kind && (abs(x - x0) + abs(y - y0) == 2) && abs(y - y0) != 2) {
                            chance = true;
                        }
                    }
                }
            }
        }
    }

    public void matchInColumn() {
        if (y != ROW_NUMBER - 1 && y != 0) {
            if (grid[y][x].kind == grid[y + 1][x].kind || grid[y][x].kind == grid[y - 1][x].kind) {
                for (y0 = 0; y0 < ROW_NUMBER; y0++) {
                    for (x0 = 0; x0 < COL_NUMBER; x0++) {
                        if (grid[y][x].kind == grid[y0][x0].kind && (abs(x - x0) + abs(y - y0) == 2) && abs(x - x0) != 2) {
                            chance = true;
                        }
                    }
                }
            }
        }
    }
}


