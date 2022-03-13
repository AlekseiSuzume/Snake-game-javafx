package ru.suzume.snakefx.objects;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import ru.suzume.snakefx.controller.MainWindowController;

import java.util.List;
import java.util.Random;

public class CactusNode extends Pane {
    private int posX;
    private int posY;
//    private static Image image = new Image(String.valueOf(CactusNode.class.getClassLoader().getResource("cactus30.png")));
    private static final Image image = new Image(String.valueOf(CactusNode.class.getResource("/ru/suzume/snakefx/images/cactus30.png")));

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

    public static void createCactus(MainWindowController mwc, List<CactusNode> cactusList, List<SnakeNode> snakeList, int width, int height) {
        int cactusX = new Random().nextInt(width - 2) + 1;
        int cactusY = new Random().nextInt(height - 2) + 1;
        for (CactusNode c : cactusList) {
            if (cactusX == c.getPosX() && cactusY == c.getPosY()) {
                CactusNode.createCactus(mwc, cactusList, snakeList, width, height);
            }
        }
        for (SnakeNode s : snakeList) {
            if (cactusX == s.getPosX() && cactusY == s.getPosY()) {
                createCactus(mwc, cactusList, snakeList, width, height);
            }
        }
        if (cactusList.size() < 5) {
            CactusNode cactus = new CactusNode(cactusX, cactusY);
            mwc.getCactusList().add(cactus);
        }
    }

    public static void checkCactus(MainWindowController mwc, List<CactusNode> cactusList, List<SnakeNode> snakeList) {
        for (CactusNode cactus : cactusList) {
            if (snakeList.get(0).getPosX() == cactus.getPosX() &&
                    snakeList.get(0).getPosY() == cactus.getPosY()) {
                mwc.gameOver();
            }
        }
    }
}
