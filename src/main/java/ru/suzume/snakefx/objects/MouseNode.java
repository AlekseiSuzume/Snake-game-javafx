package ru.suzume.snakefx.objects;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import ru.suzume.snakefx.controller.MainWindowController;

import java.util.List;
import java.util.Random;

public class MouseNode extends Pane {
    private static int posX;
    private static int posY;
    private static final Image mouseImage = new Image(String.valueOf(MouseNode.class.getResource("/ru/suzume/snakefx/images/1175.gif")));
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

    public static void createMouse(MainWindowController mwc, List<SnakeNode> snakeList, List<CactusNode> cactusList) {
        posX = new Random().nextInt(15);
        posY = new Random().nextInt(15);
        for (CactusNode c : cactusList) {
            if (posX == c.getPosX() && posY == c.getPosY()) {
                createMouse(mwc, snakeList, cactusList);
            }
        }
        for (SnakeNode s : snakeList) {
            if (posX == s.getPosX() && posY == s.getPosY()) {
                createMouse(mwc, snakeList, cactusList);
            }
        }
        mwc.setMouse(new MouseNode(posX, posY));
    }

    public static void checkMouse(MainWindowController mwc,List<SnakeNode> snakeList, List<CactusNode> cactusList, MouseNode mouse, Label textScore, Timeline timeline) {
        if (snakeList.get(0).getPosX() == mouse.getPosX() &&
                snakeList.get(0).getPosY() == mouse.getPosY()) {
            createMouse(mwc, snakeList, cactusList);
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
