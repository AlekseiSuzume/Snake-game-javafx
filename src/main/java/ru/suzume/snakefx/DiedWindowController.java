package ru.suzume.snakefx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DiedWindowController {

    @FXML
    private Button ok;

    MainWindowController mwc = new MainWindowController();

    @FXML
    void okOnClick(){
        ok.getScene().getWindow().hide();
    }
}
