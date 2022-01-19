package ru.suzume.snakefx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;

import static ru.suzume.snakefx.MainWindowApplication.mwc;

public class SettingsController {

    @FXML
    private ColorPicker backgroundFill;

    @FXML
    private Button btnAccept;

    @FXML
    private Slider difficultSlider;

    @FXML
    private ColorPicker snakeFill;

    @FXML
    private Slider snakeLength;

    @FXML
    private void initialize() {
        backgroundFill.setValue(mwc.getBgColor());
        snakeFill.setValue(mwc.getSnakeColor());
        snakeLength.setValue(mwc.getStartSnakeLength());

        //устанавливает видимость числовых меток на шкале
        snakeLength.setShowTickLabels(true);
        //позволяет переходить ровно по делениям
        snakeLength.setSnapToTicks(true);
        //устанавливает количество делений между двумя числовыми отметками на шкале
        snakeLength.setMinorTickCount(0);
        //устанавливает минимальное значение на шкале
        snakeLength.minProperty().set(3.0);
        //устанавливает максимальное значение на шкале
        snakeLength.maxProperty().set(8.0);
        //устанавливает расстояниие между двумя числовыми отметками на шкале
        snakeLength.setMajorTickUnit(1.0);

        difficultSlider.setShowTickLabels(true);
        difficultSlider.setSnapToTicks(true);
        difficultSlider.minProperty().set(1);
        difficultSlider.maxProperty().set(5);
        difficultSlider.setMajorTickUnit(1);
        difficultSlider.setMinorTickCount(0);
        difficultSlider.setValue(0);

    }

    @FXML
    void okOnClick() {
        mwc.setStartSnakeLength(getSnakeLength());
        mwc.setDifficult(getDifficult());
        getBackgroundFill();
        getSnakeFill();
        btnAccept.getScene().getWindow().hide();
    }

    @FXML
    public void cancelOnClick() {
        btnAccept.getScene().getWindow().hide();
        mwc.getTimeline().play();
    }

    public void getBackgroundFill() {
        mwc.setBgColor(backgroundFill.getValue());
    }

    public void getSnakeFill() {
        mwc.setSnakeColor(snakeFill.getValue());
    }

    public int getSnakeLength() {
        return (int) snakeLength.getValue();
    }

    public double getDifficult() {
        return difficultSlider.getValue()/10;
    }
}
