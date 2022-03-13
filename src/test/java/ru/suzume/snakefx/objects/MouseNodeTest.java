package ru.suzume.snakefx.objects;

import javafx.animation.Timeline;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import ru.suzume.snakefx.controller.MainWindowController;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@ExtendWith(ApplicationExtension.class)
class MouseNodeTest {

    private CactusNode cactusNode;

    private SnakeNode snakeNodeHead;

    private List<SnakeNode> snakeNodeList;
    private List<CactusNode> cactusNodeList;

    @Mock
    private static MainWindowController mwc = mock(MainWindowController.class);

    @BeforeEach
    void beforeEachTest() {
        cactusNode = new CactusNode(10, 10);
        snakeNodeHead = new SnakeNode(1, 1);
        snakeNodeList = new ArrayList<>();
        cactusNodeList = new ArrayList<>();
        snakeNodeList.add(snakeNodeHead);
        cactusNodeList.add(cactusNode);

    }

    @Test
    @DisplayName("Test for create new mouse")
    void createMouse() {
        MouseNode.createMouse(mwc, snakeNodeList, cactusNodeList);
        Mockito.verify(mwc, Mockito.atLeastOnce()).setMouse(any());
    }

    @Test
    @DisplayName("Test for snake eat mouse")
    void checkMouse() {
        MouseNode mouse = new MouseNode(1,1);
        Label textScore = new Label("0");
        Timeline timeline = new Timeline();
        MouseNode.checkMouse(mwc,snakeNodeList,cactusNodeList,mouse,textScore,timeline);
        Mockito.verify(mwc).setScore(100);
    }
}