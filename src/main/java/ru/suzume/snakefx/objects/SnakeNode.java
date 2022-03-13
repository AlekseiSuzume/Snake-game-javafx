package ru.suzume.snakefx.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ru.suzume.snakefx.controller.MainWindowController;

import java.util.List;

public class SnakeNode extends Pane {
    private int posX;
    private int posY;

    public SnakeNode(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public static void snakeFill(GraphicsContext gc, List<SnakeNode> snakeList, int BLOCK_SIZE, Color snakeColor) {
        for (int i = 0; i <= snakeList.size() - 1; i++) {
            gc.setFill(Color.BLACK);
            if (i == 0) {
                gc.fillRoundRect(snakeList.get(i).getPosX() * BLOCK_SIZE, snakeList.get(i).getPosY() * BLOCK_SIZE, BLOCK_SIZE - 1, BLOCK_SIZE - 1, 10, 10);
                gc.setFill(snakeColor);
                gc.fillRoundRect(snakeList.get(i).getPosX() * BLOCK_SIZE, snakeList.get(i).getPosY() * BLOCK_SIZE, BLOCK_SIZE - 2, BLOCK_SIZE - 2, 10, 10);
            } else {
                gc.fillRoundRect(snakeList.get(i).getPosX() * BLOCK_SIZE, snakeList.get(i).getPosY() * BLOCK_SIZE, BLOCK_SIZE - 1, BLOCK_SIZE - 1, 3, 3);
                gc.setFill(snakeColor);
                gc.fillRoundRect(snakeList.get(i).getPosX() * BLOCK_SIZE, snakeList.get(i).getPosY() * BLOCK_SIZE, BLOCK_SIZE - 2, BLOCK_SIZE - 2, 3, 3);
            }
        }
    }

    public static void move(List<SnakeNode> snakeList, Dicrection dicrection) {
        for (int i = snakeList.size() - 1; i > 0; i--) {
            snakeList.get(i).setPosX(snakeList.get(i - 1).getPosX());
            snakeList.get(i).setPosY(snakeList.get(i - 1).getPosY());
        }
        switch (dicrection) {
            case UP -> snakeList.get(0).setPosY(snakeList.get(0).getPosY() - 1);
            case DOWN -> snakeList.get(0).setPosY(snakeList.get(0).getPosY() + 1);
            case LEFT -> snakeList.get(0).setPosX(snakeList.get(0).getPosX() - 1);
            case RIGHT -> snakeList.get(0).setPosX(snakeList.get(0).getPosX() + 1);
        }
    }

    public static void wallCollision(MainWindowController mwc, List<SnakeNode> snakeList, int height, int width) {

        if (snakeList.get(0).getPosY() < 0) {
            mwc.gameOver();
        }

        if (snakeList.get(0).getPosY() == height) {
            mwc.gameOver();
        }

        if (snakeList.get(0).getPosX() < 0) {
            mwc.gameOver();
        }

        if (snakeList.get(0).getPosX() == width) {
            mwc.gameOver();
        }
    }

    public static void checkIntersection(MainWindowController mwc, List<SnakeNode> snakeList) {
        for (int i = 1; i < snakeList.size(); i++) {
            if (snakeList.get(0).getPosX() == snakeList.get(i).getPosX()
                    && snakeList.get(0).getPosY() == snakeList.get(i).getPosY()) {
                mwc.gameOver();
            }
        }
    }
}
