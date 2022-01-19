package ru.suzume.snakefx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PreviewController {

    @FXML
    Button close;

    public void close() {
        close.getScene().getWindow().hide();
    }

}
