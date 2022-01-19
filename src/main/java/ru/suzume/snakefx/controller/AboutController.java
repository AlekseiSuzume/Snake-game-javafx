package ru.suzume.snakefx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class AboutController {

    @FXML
    private Text aboutText;

    @FXML
    private Button btnClose;

    @FXML
    private void initialize(){

    }


    public void close() {
        btnClose.getScene().getWindow().hide();
    }
}
