package ru.suzume.snakefx.objects;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.suzume.snakefx.controller.MainWindowController;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class SnakeNodeTest {

    @Mock
    private static MainWindowController mwc;

    private List<SnakeNode> snakeNodeList;

    private Dicrection dicrection;


    @BeforeEach
    public void createObjects() {
        mwc = mock(MainWindowController.class);
        snakeNodeList = new ArrayList<>();
        snakeNodeList.add(new SnakeNode(10, 10));
        snakeNodeList.add(new SnakeNode(10, 11));
        snakeNodeList.add(new SnakeNode(10, 12));
        snakeNodeList.add(new SnakeNode(10, 13));
    }

    // Имитируем нажатие кнопки вверх
    //Вызываем метод move(), координата Y головы змеи должна изменится на значение -1
    //сравнение координаты головы змеи с новой созданной головой с координатой Y-1
    @Test
    @DisplayName("Test for change direction to UP")
    void testMoveUp() {
        dicrection = Dicrection.UP;
        SnakeNode.move(snakeNodeList, dicrection);
        assertEquals(snakeNodeList.get(0).getPosY(), new SnakeNode(10, 9).getPosY());
    }

    @Test
    @DisplayName("Test for change direction to DOWN")
    void testMoveDown() {
        dicrection = Dicrection.DOWN;
        SnakeNode.move(snakeNodeList, dicrection);
        assertEquals(snakeNodeList.get(0).getPosY(), new SnakeNode(10, 11).getPosY());
    }

    @Test
    @DisplayName("Test for change direction to LEFT")
    void testMoveLeft() {
        dicrection = Dicrection.LEFT;
        SnakeNode.move(snakeNodeList, dicrection);
        assertEquals(snakeNodeList.get(0).getPosX(), new SnakeNode(9, 10).getPosX());
    }

    @Test
    @DisplayName("Test for change direction to RIGHT")
    void testMoveRight() {
        dicrection = Dicrection.RIGHT;
        SnakeNode.move(snakeNodeList, dicrection);
        assertEquals(snakeNodeList.get(0).getPosX(), new SnakeNode(11, 10).getPosX());
    }

    @Test
    @DisplayName("Test for wall collision")
    void testWallCollision() {
        snakeNodeList.get(0).setPosX(15);
        SnakeNode.wallCollision(mwc,snakeNodeList, 15, 15);
        Mockito.verify(mwc, Mockito.times(1)).gameOver();
    }

    @Test
    @DisplayName("Test for check intersection")
    void CheckIntersection() {
        snakeNodeList.get(0).setPosX(10);
        snakeNodeList.get(0).setPosY(13);
        SnakeNode.checkIntersection(mwc,snakeNodeList);
        Mockito.verify(mwc, Mockito.times(1)).gameOver();
    }
}