package ru.suzume.snakefx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DiedWindowController {

    @FXML
    private Button ok;

    MainWindowController mwc = new MainWindowController();

    @FXML
    void okOnClick(){
        ok.getScene().getWindow().hide();
    }
}
