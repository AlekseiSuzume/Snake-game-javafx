module ru.suzume.snakefx {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.suzume.snakefx to javafx.fxml;
    exports ru.suzume.snakefx;
}