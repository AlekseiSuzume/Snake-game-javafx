package ru.suzume.snakefx.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ru.suzume.snakefx.MainWindowApplication;
import ru.suzume.snakefx.objects.CactusNode;
import ru.suzume.snakefx.objects.MouseNode;
import ru.suzume.snakefx.objects.Move;
import ru.suzume.snakefx.objects.SnakeNode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MainWindowController implements EventHandler<KeyEvent> {

    @FXML
    private Label txtRecord;
    @FXML
    private Label txtScore;
    @FXML
    private Label txtLive;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnPause;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnRestart;
    @FXML
    private Canvas map;

    private final int width = 15;
    private final int height = 15;
    private final int BLOCK_SIZE = 30;
    private boolean gameStop;
    private boolean gameOver;
    private boolean isAlive;
    private Move move;
    private Color bgColor = Color.rgb(255, 204, 128);
    private Color snakeColor = Color.TEAL;
    private Timeline timeline = new Timeline();
    private int startSnakeLength = 3;
    private double difficult = 0;
    private double speed = 0;
    private int snakeSize = 3;
    private int score = 0;
    private int record = 0;
    private int tempScore = 0;
    private int lives = 3;
    private MouseNode mouse;
    private List<SnakeNode> snakeList = new ArrayList<>();
    private List<CactusNode> cactusList = new ArrayList<>();
    private GraphicsContext gc;


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public Color getSnakeColor() {
        return snakeColor;
    }

    public void setSnakeColor(Color snakeColor) {
        this.snakeColor = snakeColor;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public int getStartSnakeLength() {
        return startSnakeLength;
    }

    public void setStartSnakeLength(int startSnakeLength) {
        this.startSnakeLength = startSnakeLength;
    }

    public void setDifficult(double difficult) {
        this.difficult = difficult;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed += speed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }


    public void setMouse(MouseNode mouse) {
        this.mouse = mouse;
    }

    public List<SnakeNode> getSnakeList() {
        return snakeList;
    }

    public List<CactusNode> getCactusList() {
        return cactusList;
    }


    @FXML
    public void initialize() {
        isLives();
        loadConfig();
        txtLive.setText("" + lives);
        btnStart.setFocusTraversable(false);
        btnPause.setFocusTraversable(false);
        btnRestart.setFocusTraversable(false);
        btnSettings.setFocusTraversable(false);
        btnExit.setFocusTraversable(false);
        isAlive = true;
        gameStop = false;
        gameOver = false;
        move = Move.RIGHT;
        speed = 0 + difficult;
        gc = map.getGraphicsContext2D();
        snakeList.clear();
        cactusList.clear();
        initGame();
        tick();
    }

    public void loadConfig() {
        Path path = Paths.get("record.bin");
        int tempRecord;
        if (Files.exists(path)) {
            try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(path.toString()))) {
                tempRecord = dataInputStream.readInt();
                txtRecord.setText("" + tempRecord);
                if (tempRecord < tempScore) {
                    txtRecord.setText("" + tempScore);
                    DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(path.toString()));
                    dataOutputStream.writeInt(tempScore);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(path.toString()))) {
                dataOutputStream.writeInt(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void initGame() {
        for (int i = startSnakeLength; i > 0; i--) {
            SnakeNode snakeNode = new SnakeNode(width / 2, height / 2);
            snakeList.add(snakeNode);
        }
        mouse = new MouseNode(new Random().nextInt(width), new Random().nextInt(height));
        fill();
    }

    public void fill() {
        gc.setFill(bgColor);
        gc.fillRect(0, 0, width * BLOCK_SIZE, height * BLOCK_SIZE);
        gc.drawImage(MouseNode.getImage(), mouse.getPosX() * BLOCK_SIZE, mouse.getPosY() * BLOCK_SIZE);
        for (CactusNode cactus : cactusList) {
            gc.drawImage(CactusNode.getImage(), cactus.getPosX() * BLOCK_SIZE, cactus.getPosY() * BLOCK_SIZE);
        }
    }

    void tick() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), ev -> {
            fill();
            SnakeNode.move(snakeList, move, height, width);
            SnakeNode.snake(gc, snakeList, BLOCK_SIZE, snakeColor);
            MouseNode.checkMouse(snakeList, cactusList, mouse, txtScore, timeline);
            CactusNode.checkCactus(cactusList, snakeList);
            SnakeNode.checkIntersection(snakeList);
            checkRate();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setRate(1 + speed);
        timeline.play();
    }

    public void checkRate() {
        if (timeline.getRate() > 1.3 && cactusList.size() == 0) {
            CactusNode.createCactus(cactusList, snakeList, width, height);
        } else if (timeline.getRate() > 1.6 && cactusList.size() == 1) {
            CactusNode.createCactus(cactusList, snakeList, width, height);
        } else if (timeline.getRate() > 2.0 && cactusList.size() == 2) {
            CactusNode.createCactus(cactusList, snakeList, width, height);
        } else if (timeline.getRate() > 2.5 && cactusList.size() == 3) {
            CactusNode.createCactus(cactusList, snakeList, width, height);
        } else if (timeline.getRate() > 3.0 && cactusList.size() == 4) {
            CactusNode.createCactus(cactusList, snakeList, width, height);
        }
    }

    @Override
    public void handle(KeyEvent KeyEvent) {
        if (KeyEvent.getCode() == KeyCode.UP) {
            move = Move.UP;
        }
        if (KeyEvent.getCode() == KeyCode.DOWN) {
            move = Move.DOWN;
        }
        if (KeyEvent.getCode() == KeyCode.LEFT) {
            move = Move.LEFT;
        }
        if (KeyEvent.getCode() == KeyCode.RIGHT) {
            move = Move.RIGHT;
        }
    }

    private void isLives() {
        if (lives > 0) {
            txtScore.setText("" + tempScore);
        } else {
            lives = 3;
            score = 0;
            txtScore.setText("" + score);
        }
    }

    public void gameOver() {
        timeline.stop();
        if (lives > 0) {
            lives = lives - 1;
            tempScore = score;
        }
        isAlive = false;
        loadConfig();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowApplication.class.getResource("view/lose-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Вы проиграли");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void restart() {
        timeline.stop();
        lives = 0;
        initialize();
    }

    @FXML
    private void settings() {
        timeline.stop();
        gameStop = true;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowApplication.class.getResource("/ru/suzume/snakefx/view/settings-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Settings");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void exit() {
        System.exit(0);
    }

    @FXML
    public void start() {
        if (gameStop) {
            restart();
        }
        if (isAlive) {
            timeline.play();

        } else {
            initialize();
        }

    }

    @FXML
    public void pause() {
        if (isAlive) {
            timeline.pause();
        }
    }

    public void aboutViewOpen() {
        timeline.stop();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowApplication.class.getResource("/ru/suzume/snakefx/view/about-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    public void authorViewOpen() {
        timeline.stop();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowApplication.class.getResource("/ru/suzume/snakefx/view/author-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    public void preview() {
        timeline.stop();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowApplication.class.getResource("/ru/suzume/snakefx/view/preview-view.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.showAndWait();
    }
}
