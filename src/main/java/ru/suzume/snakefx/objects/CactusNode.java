package ru.suzume.snakefx.objects;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;

import java.io.File;

public class CactusNode extends Pane {
    private int posX;
    private int posY;
//    private static File file = new File("cactus40.png");
//    private static Image image = new Image(file.toURI().toString());
//    private static BackgroundImage backgroundImage = new BackgroundImage(image,null,null,null,null);
//    private static Background background = new Background(backgroundImage);

    public CactusNode(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
//        this.setBackground(background);
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

//    public static Image getImage() {
//        return image;
//    }
}
