module ru.suzume.snakefx {
        requires javafx.controls;
        requires javafx.fxml;


        opens ru.suzume.snakefx to javafx.fxml;
        exports ru.suzume.snakefx;
        exports ru.suzume.snakefx.controller;
        opens ru.suzume.snakefx.controller to javafx.fxml;
        }