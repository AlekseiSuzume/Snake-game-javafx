package ru.suzume.snakefx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.suzume.snakefx.objects.CactusNode;
import ru.suzume.snakefx.objects.MouseNode;
import ru.suzume.snakefx.objects.Move;
import ru.suzume.snakefx.objects.SnakeNode;

import java.io.*;
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
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu btnMenu;
    @FXML
    private MenuItem menuAbout;
    @FXML
    private MenuItem menuInfo;

    private int width = 15;
    private int height = 15;
    private int nodeSize = 30;
    private int mouseX;
    private int mouseY;
    private int cactusX;
    private int cactusY;
    private boolean gameOver;
    private boolean isAlive;
    static Move move;
    public static Color bgColor = Color.rgb(255, 204, 128);
    public static Color snakeColor = Color.TEAL;
    public static Timeline timeline = new Timeline();
    public static int startSnakeLength = 3;
    public static double difficult = 0;
    private double speed = 0;
    private int snakeSize = 3;
    private int score;
    private int tempScore;
    private int lives = 2;
    Properties properties;
    MouseNode mouse;
    CactusNode cactus;
    List<SnakeNode> snake = new ArrayList<>();
    List<CactusNode> cactusList = new ArrayList<>();
    GraphicsContext gc;
    File imageFileMouse;
    File imageFileCactus;
    Image mouseImage;
    Image cactusImage;

    @FXML
    public void initialize() {
        isLives();
        loadConfig();
        txtLive.setText("" + lives);
        imageFileMouse = new File("1175.gif");
        mouseImage = new Image(imageFileMouse.toURI().toString());
        imageFileCactus = new File("cactus30.png");
        cactusImage = new Image(imageFileCactus.toURI().toString());
        btnStart.setFocusTraversable(false);
        btnPause.setFocusTraversable(false);
        btnRestart.setFocusTraversable(false);
        btnSettings.setFocusTraversable(false);
        btnExit.setFocusTraversable(false);
        isAlive = true;
        gameOver = false;
        move = Move.RIGHT;
        speed = 0 + difficult;
        gc = map.getGraphicsContext2D();
        snake.clear();
        cactusList.clear();
        init();
        tick();
    }

    public void loadConfig() {
        properties = new Properties();
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        try {
            fileInputStream = new FileInputStream("src/main/resources/ru/suzume/snakefx/config.properties");
            properties.load(fileInputStream);
            if (Integer.parseInt(properties.getProperty("record")) < score) {
                properties.setProperty("record", "" + score);
            }
            fileOutputStream = new FileOutputStream("src/main/resources/ru/suzume/snakefx/config.properties");
            properties.store(fileOutputStream, null);
            txtRecord.setText(properties.getProperty("record"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        for (int i = startSnakeLength; i > 0; i--) {
            SnakeNode snakeNode = new SnakeNode(width / 2, height / 2);
            snake.add(snakeNode);
        }
        createCactus();
        createMouse();
        fill();
    }

    public void fill() {
        //fill field
        gc.setFill(bgColor);
        gc.fillRect(0, 0, width * nodeSize, height * nodeSize);

        //fill mouse
//        gc.setFill(Color.DARKORANGE);
//        gc.fillRect(mouse.getPosX() * nodeSize, mouse.getPosY() * nodeSize, nodeSize - 1, nodeSize - 1);
//        gc.setFill(Color.ORANGE);
//        gc.fillRect(mouse.getPosX() * nodeSize, mouse.getPosY() * nodeSize, nodeSize - 2, nodeSize - 2);

        gc.drawImage(mouseImage, mouse.getPosX() * nodeSize, mouse.getPosY() * nodeSize);
        for (CactusNode cactus : cactusList) {
            gc.drawImage(cactusImage, cactus.getPosX() * nodeSize, cactus.getPosY() * nodeSize);
        }
    }

    void tick() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), ev -> {
            fill();
            move();
            snake();
            checkMouse();
            checkCactus();
            checkIntersection();
            System.out.println(timeline.getRate());
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setRate(1 + speed);
        timeline.play();
    }

    void snake() {
        //рисует и закрашивает части змеи
//        for (SnakeNode snakeNode : snake) {
//            gc.setFill(Color.BLACK);
//            gc.fillRect(snakeNode.getPosX() * nodeSize, snakeNode.getPosY() * nodeSize, nodeSize - 2, nodeSize - 2);
//            gc.setFill(snakeColor);
//            gc.fillRect(snakeNode.getPosX() * nodeSize, snakeNode.getPosY() * nodeSize, nodeSize - 3, nodeSize - 3);
//    }
        for (int i = 0; i <= snake.size() - 1; i++) {
            gc.setFill(Color.BLACK);
            if (i == 0) {
                gc.fillRoundRect(snake.get(i).getPosX() * nodeSize, snake.get(i).getPosY() * nodeSize, nodeSize - 1, nodeSize - 1, 10, 10);
                gc.setFill(snakeColor);
                gc.fillRoundRect(snake.get(i).getPosX() * nodeSize, snake.get(i).getPosY() * nodeSize, nodeSize - 2, nodeSize - 2, 10, 10);
            } else {
                gc.fillRoundRect(snake.get(i).getPosX() * nodeSize, snake.get(i).getPosY() * nodeSize, nodeSize - 1, nodeSize - 1, 3, 3);
                gc.setFill(snakeColor);
                gc.fillRoundRect(snake.get(i).getPosX() * nodeSize, snake.get(i).getPosY() * nodeSize, nodeSize - 2, nodeSize - 2, 3, 3);
            }
        }
//            System.out.println(snakeNode.getPosX() + " " + snakeNode.getPosY());
    }

    private void createCactus() {
        cactusX = new Random().nextInt(width);
        cactusY = new Random().nextInt(height);
        for (CactusNode c : cactusList) {
            if (cactusX == c.getPosX() && cactusY == c.getPosY()) {
                createCactus();
            }
        }
        for (SnakeNode s : snake) {
            if (cactusX == s.getPosX() && cactusY == s.getPosY()) {
                createCactus();
            }
        }
        if (cactusList.size() < 5) {
            cactus = new CactusNode(cactusX, cactusY);
            cactusList.add(cactus);
        }
    }

    public void checkCactus() {
        for (CactusNode cactus : cactusList) {
            if (snake.get(0).getPosX() == cactus.getPosX() &&
                    snake.get(0).getPosY() == cactus.getPosY()) {
                gameOver();
            }
        }
    }

    private void createMouse() {
        mouseX = new Random().nextInt(width);
        mouseY = new Random().nextInt(height);
        for (SnakeNode s : snake) {
            if (mouseX == s.getPosX() && mouseY == s.getPosY()) {
                createMouse();
            }
        }
        mouse = new MouseNode(mouseX, mouseY);
    }

    private void checkMouse() {
        if (snake.get(0).getPosX() == mouse.getPosX() &&
                snake.get(0).getPosY() == mouse.getPosY()) {
            createMouse();
            score += 100;
            txtScore.setText("" + score);
            speed += 0.1;
            timeline.setRate(1 + speed);
            SnakeNode snakeNode = new SnakeNode(snake.get(snake.size() - 1).getPosX(),
                    snake.get(snake.size() - 1).getPosY());
            snake.add(snakeNode);
            createCactus();
        }
    }

    private void checkIntersection() {
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).getPosX() == snake.get(i).getPosX()
                    && snake.get(0).getPosY() == snake.get(i).getPosY()) {
                gameOver();
            }
        }
    }

    public void move() {
        if (gameOver) {
            gameOver();
            return;
        }
        for (int i = snake.size() - 1; i > 0; i--) {
            snake.get(i).setPosX(snake.get(i - 1).getPosX());
            snake.get(i).setPosY(snake.get(i - 1).getPosY());
        }
        if (move == Move.UP) {
            System.out.println("MOVE UP");
            snake.get(0).setPosY(snake.get(0).getPosY() - 1);
            if (snake.get(0).getPosY() < 0) {
                gameOver = true;
            }
        }
        if (move == Move.DOWN) {
            System.out.println("MOVE DOWN");
            snake.get(0).setPosY(snake.get(0).getPosY() + 1);
            if (snake.get(0).getPosY() == height) {
                gameOver = true;
            }
        }
        if (move == Move.LEFT) {
            System.out.println("MOVE LEFT");
            snake.get(0).setPosX(snake.get(0).getPosX() - 1);
            if (snake.get(0).getPosX() < 0) {
                System.out.println(snake.get(0).getPosX());
                gameOver = true;
            }
        }
        if (move == Move.RIGHT) {
            System.out.println("MOVE RIGHT");
            snake.get(0).setPosX(snake.get(0).getPosX() + 1);
            if (snake.get(0).getPosX() == width) {
                gameOver = true;
            }
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
        if (lives >= 0) {
            txtScore.setText("" + tempScore);
        } else {
            lives = 2;
            score = 0;
            txtScore.setText("" + score);
        }
    }

    private void gameOver() {
        if (lives >= 0) {
            tempScore = score;
            lives = lives - 1;
        }
        isAlive = false;
        timeline.stop();
        loadConfig();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowApplication.class.getResource("died-view.fxml"));
        Stage stage = new Stage();
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
    private void restart() {
        timeline.stop();
        initialize();
    }

    @FXML
    private void settings() {
        timeline.stop();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowApplication.class.getResource("settings-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Settings");
        stage.setResizable(false);
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
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void start() {
        if (isAlive) {
            timeline.play();
        }
    }

    @FXML
    public void pause() {
        if (isAlive) {
            timeline.pause();
        }
    }
}
