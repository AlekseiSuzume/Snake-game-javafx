package ru.suzume.snakefx.objects;

import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Random;

import static ru.suzume.snakefx.MainWindowApplication.mwc;

public class MouseNode extends Pane {
    private static int posX;
    private static int posY;
    private static final Image mouseImage = new Image(String.valueOf(MouseNode.class.getClassLoader().getResource("1175.gif")));

    public MouseNode(int posX, int posY) {
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
        return mouseImage;
    }

    public static void createMouse(List<SnakeNode> snakeList, List<CactusNode> cactusList) {
        posX = new Random().nextInt(mwc.getWidth());
        posY = new Random().nextInt(mwc.getHeight());
        for (CactusNode c : cactusList) {
            if (posX == c.getPosX() && posY == c.getPosY()) {
                createMouse(snakeList, cactusList);
            }
        }
        for (SnakeNode s : snakeList) {
            if (posX == s.getPosX() && posY == s.getPosY()) {
                createMouse(snakeList, cactusList);
            }
        }
        mwc.setMouse(new MouseNode(posX, posY));
    }

    public static void checkMouse(List<SnakeNode> snakeList, List<CactusNode> cactusList, MouseNode mouse, Label textScore, Timeline timeline) {
        if (snakeList.get(0).getPosX() == mouse.getPosX() &&
                snakeList.get(0).getPosY() == mouse.getPosY()) {
            createMouse(snakeList, cactusList);
            mwc.setScore(100);
            textScore.setText("" + mwc.getScore());
            mwc.setSpeed(0.1);
            timeline.setRate(1 + mwc.getSpeed());
            SnakeNode snakeNode = new SnakeNode(snakeList.get(snakeList.size() - 1).getPosX(),
                    snakeList.get(snakeList.size() - 1).getPosY());
            mwc.getSnakeList().add(snakeNode);
        }
    }
}
