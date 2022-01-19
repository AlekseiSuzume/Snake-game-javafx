package ru.suzume.snakefx.objects;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Random;

import static ru.suzume.snakefx.MainWindowApplication.mwc;

public class CactusNode extends Pane {
    private int posX;
    private int posY;
    private static Image image = new Image(String.valueOf(CactusNode.class.getClassLoader().getResource("cactus30.png")));

    public CactusNode(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public static Image getImage() {
        return image;
    }

    public static void createCactus(List<CactusNode> cactusList, List<SnakeNode> snakeList, int width, int height) {
        int cactusX = new Random().nextInt(width - 2) + 1;
        int cactusY = new Random().nextInt(height - 2) + 1;
        for (CactusNode c : cactusList) {
            if (cactusX == c.getPosX() && cactusY == c.getPosY()) {
               CactusNode.createCactus(cactusList,snakeList,width,height);
            }
        }
        for (SnakeNode s : snakeList) {
            if (cactusX == s.getPosX() && cactusY == s.getPosY()) {
                createCactus(cactusList,snakeList,width,height);
            }
        }
        if (cactusList.size() < 5) {
            CactusNode cactus = new CactusNode(cactusX, cactusY);
           mwc.getCactusList().add(cactus);
        }
    }
    public static void checkCactus(List<CactusNode> cactusList, List<SnakeNode> snakeList) {
        for (CactusNode cactus : cactusList) {
            if (snakeList.get(0).getPosX() == cactus.getPosX() &&
                    snakeList.get(0).getPosY() == cactus.getPosY()) {
                mwc.gameOver();
            }
        }
    }
}
