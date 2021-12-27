package ru.suzume.snakefx.objects;

import javafx.css.Style;
import javafx.scene.layout.Pane;

public class SnakeNode {
    private int posX;
    private int posY;
    public static Style style;


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

    public void setStyle(Style style) {
        this.style = style;
    }

}
