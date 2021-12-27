package ru.suzume.snakefx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class SettingsController {

    @FXML
    private ColorPicker backgroundFill;

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnCancel;

    @FXML
    private Slider levelSlider;

    @FXML
    private ColorPicker snakeFill;

    @FXML
    private Slider snakeLength;

    @FXML
    public void initialize(){
        backgroundFill.setValue(Color.GREEN);
        snakeFill.setValue(Color.LIGHTGREEN);
    }

    @FXML
    void okOnClick(){
        btnAccept.getScene().getWindow().hide();
        getBackgroundFill();
        getSnakeFill();
    }


    public void getBackgroundFill() {
       MainWindowController.bgColor = backgroundFill.getValue();
    }

    public void getSnakeFill(){
        MainWindowController.snakeColor = snakeFill.getValue();
    }
}
