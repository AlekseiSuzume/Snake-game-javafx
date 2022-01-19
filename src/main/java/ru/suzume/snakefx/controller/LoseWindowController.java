package ru.suzume.snakefx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import static ru.suzume.snakefx.MainWindowApplication.mwc;

public class LoseWindowController {

    @FXML
    public Text txtScore;
    @FXML
    private Button btnContinue;

    @FXML
    private Button btnNewGame;

    @FXML
    private void initialize() {
//        mwc.setAlive(true);
        txtScore.setText("" + (mwc.getLives()));
        if (mwc.getLives() == 0) {
            btnContinue.setDisable(true);
        }
    }

    @FXML
    private void NewGameOnClick() {
        if (mwc.getLives() >= 0) {
            mwc.setLives(0);
        }
        btnNewGame.getScene().getWindow().hide();
    }

    @FXML
    private void ContinueOnClick() {
//        mwc.setAlive(true);
        btnNewGame.getScene().getWindow().hide();

    }
}
