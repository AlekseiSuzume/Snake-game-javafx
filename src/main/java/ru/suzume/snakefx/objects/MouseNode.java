package ru.suzume.snakefx.objects;

import javafx.css.Style;
import javafx.scene.layout.Pane;

public class MouseNode extends Pane {
    private int posX;
    private int posY;
    public static Style style;

    public MouseNode(int posX, int posY) {
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

}
