import processing.core.PApplet;

import java.util.stream.IntStream;

/**
 * Created by Neil on 2017/9/21 9:22.
 */
public class TDCA {
    int[][] board;
    int[][] newBoard;
    int width;
    int height;
    int size;
    PApplet app;

    public TDCA(int width, int height, int size, LifeCell lifeCell) {
        this.width = width;
        this.height = height;
        this.size = size;
        board = new int[width][height];
        newBoard = new int[width][height];
        //init all cells with functional interface
        IntStream.range(0, width).forEach(i -> IntStream.range(0, height)
                .filter(j -> lifeCell.test(i, j))
                .forEach(j -> board[i][j] = 1));
    }

    public void generate() {
        IntStream.range(1, width - 1).forEach(x -> IntStream.range(1, height - 1).forEach(y -> {
            int neighbors = 0;
            //count alive neighbors of the cell
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    neighbors += board[x + i][y + j];
                }
            }
            //sub the state of the cell
            neighbors -= board[x][y];

            //ca rules
            if (board[x][y] == 1 && neighbors < 2) newBoard[x][y] = 0;
            else if (board[x][y] == 1 && neighbors > 3) newBoard[x][y] = 0;
            else if (board[x][y] == 0 && neighbors == 3) newBoard[x][y] = 1;
            else newBoard[x][y] = board[x][y];
        }));
        for (int i = 0; i < width; i++) {
            System.arraycopy(newBoard[i], 0, board[i], 0, height);
        }
    }

    public void draw(PApplet app) {
        this.app = app;
        app.noStroke();
        app.fill(255, 85, 85);
        IntStream.range(0, width).forEach(i -> IntStream.range(0, height)
                .filter(j -> board[i][j] == 1)
                .forEach(j -> app.rect(i * size, j * size, size, size)));
    }

    //draw white if kill cell
    public void drawWhite(int x, int y) {
        app.noStroke();
        app.fill(255);
        app.rect(x * size, y * size, size, size);
    }

    //update cell state if mouse pressed
    public void updateCell(int mouseX, int mouseY) {
        int x = mouseX / size;
        int y = mouseY / size;
        if (x >= width || y >= height) {
            return;
        }
        if (board[x][y] == 1) {
            board[x][y] = 0;
            drawWhite(x, y);
        } else {
            board[x][y] = 1;
        }
    }

    public void clean() {
        board = new int[width][height];
    }
}
