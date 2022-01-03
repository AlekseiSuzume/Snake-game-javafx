package ru.suzume.snakefx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ru.suzume.snakefx.objects.Level;
import ru.suzume.snakefx.objects.MouseNode;
import ru.suzume.snakefx.objects.Move;
import ru.suzume.snakefx.objects.SnakeNode;

import java.io.IOException;
import java.util.*;


public class MainWindowController implements EventHandler<KeyEvent> {

    @FXML
    private Button btnDown;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnLeft;
    @FXML
    private Button btnRight;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnUp;
    @FXML
    private Canvas map;
    @FXML
    private MenuBar menuAbout;
    @FXML
    private Menu menuAboutButton;
    @FXML
    private Button restart;

    private int width = 20;
    private int height = 20;
    private int nodeSize = 20;
    private int mouseX;
    private int mouseY;
    private boolean gameOver;
    private boolean isAlive;
    static Move move;
    public static Color bgColor = Color.GREEN;
    public static Color snakeColor = Color.LIGHTGREEN;
    public static Timeline timeline = new Timeline();
    List<SnakeNode> snake = new ArrayList<>();
    private int snakeSize = 3;
    MouseNode mouse;
    private double speed = 0;
    GraphicsContext gc;

    @FXML
    public void initialize() {
        isAlive = true;
        gameOver = false;
        move = Move.RIGHT;
        speed = 0;
        gc = map.getGraphicsContext2D();
        snake.clear();
        init();
//        fill();
        tick();

//        btnSettings.setOnAction(event -> {
//            timeline.stop();
////            btnSettings.getScene().getWindow().hide();
//
//            FXMLLoader fxmlLoader = new FXMLLoader(MainWindowApplication.class.getResource("settings-view.fxml"));
//            Stage stage = new Stage();
//            Scene scene = null;
//            try {
//                scene = new Scene(fxmlLoader.load());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            stage.setScene(scene);
//            stage.show();
//        });
    }

    public void init() {
        SnakeNode snakeHead = new SnakeNode(width / 2, height / 2);
        SnakeNode snakeNode1 = new SnakeNode(width / 2, height / 2);
        SnakeNode snakeNode2 = new SnakeNode(width / 2, height / 2);

        snake.add(snakeHead);
        snake.add(snakeNode1);
        snake.add(snakeNode2);

        createMouse();

        fill();
    }

    public void fill() {
        //fill field
        gc.setFill(bgColor);
        gc.fillRect(0, 0, width * nodeSize, height * nodeSize);

        //fill mouse
        gc.setFill(Color.DARKORANGE);
        gc.fillRect(mouse.getPosX() * nodeSize, mouse.getPosY() * nodeSize, nodeSize - 1, nodeSize - 1);
        gc.setFill(Color.ORANGE);
        gc.fillRect(mouse.getPosX() * nodeSize, mouse.getPosY() * nodeSize, nodeSize - 2, nodeSize - 2);

    }

    void tick() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), ev -> {
            fill();
            move();
            snake();
            checkMouse();
            checkIntersection();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    void snake() {
        //рисует и закрашивает части змеи
        for (SnakeNode snakeNode : snake) {
            gc.setFill(Color.BLACK);
            gc.fillRect(snakeNode.getPosX() * nodeSize, snakeNode.getPosY() * nodeSize, nodeSize - 1, nodeSize - 1);
            gc.setFill(snakeColor);
            gc.fillRect(snakeNode.getPosX() * nodeSize, snakeNode.getPosY() * nodeSize, nodeSize - 2, nodeSize - 2);

            System.out.println(snakeNode.getPosX() + " " + snakeNode.getPosY());
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
            speed += 0.1;
            timeline.setRate(1 + speed);
            SnakeNode snakeNode = new SnakeNode(snake.get(snake.size() - 1).getPosX(),
                    snake.get(snake.size() - 1).getPosY());
            snake.add(snakeNode);
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

    private void gameOver() {
        isAlive = false;
        timeline.stop();
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
        timeline.pause();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowApplication.class.getResource("settings-view.fxml"));
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
}
