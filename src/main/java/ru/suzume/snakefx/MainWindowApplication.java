package ru.suzume.snakefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowApplication extends Application {

    MainWindowController mwc = new MainWindowController();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        scene.setOnKeyPressed(mwc);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, mwc);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() {
    }

    public static void main(String[] args) {
        launch();
    }
}