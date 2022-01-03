package ru.suzume.snakefx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;

public class SettingsController {

    @FXML
    private ColorPicker backgroundFill;

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnCancel;

    @FXML
    private Slider difficultSlider;

    @FXML
    private ColorPicker snakeFill;

    @FXML
    private Slider snakeLength;

    @FXML
    public void initialize(){
        backgroundFill.setValue(MainWindowController.bgColor);
        snakeFill.setValue(MainWindowController.snakeColor);
        snakeLength.setValue(MainWindowController.startSnakeLength);

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
        difficultSlider.minProperty().set(0.1);
        difficultSlider.maxProperty().set(0.5);
        difficultSlider.setMajorTickUnit(0.1);
        difficultSlider.setMinorTickCount(0);
        difficultSlider.setValue(0.1);

    }

    @FXML
    void okOnClick(){
        btnAccept.getScene().getWindow().hide();
        getBackgroundFill();
        getSnakeFill();
        MainWindowController.startSnakeLength = getSnakeLength();
        MainWindowController.difficult = getDifficult();
        MainWindowController.timeline.play();

    }

    public void cancelOnClick() {
        btnAccept.getScene().getWindow().hide();
        MainWindowController.timeline.play();
    }

    public void getBackgroundFill() {
       MainWindowController.bgColor = backgroundFill.getValue();
    }

    public void getSnakeFill(){
        MainWindowController.snakeColor = snakeFill.getValue();
    }

    public int getSnakeLength(){
        return (int) snakeLength.getValue();
    }

    public double getDifficult(){
        return difficultSlider.getValue();
    }
}
