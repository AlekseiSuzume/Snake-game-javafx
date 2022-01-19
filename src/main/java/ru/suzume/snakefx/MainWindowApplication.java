package ru.suzume.snakefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ru.suzume.snakefx.controller.MainWindowController;

import java.io.IOException;

public class MainWindowApplication extends Application {

//    public static MainWindowController mwc = new MainWindowController();
   public static MainWindowController mwc;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowApplication.class.getResource("/ru/suzume/snakefx/view/main-view.fxml"));
        Parent root = fxmlLoader.load();
        mwc = fxmlLoader.getController();
        Scene scene = new Scene(root);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, mwc);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.setResizable(false);
        mwc.preview();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}