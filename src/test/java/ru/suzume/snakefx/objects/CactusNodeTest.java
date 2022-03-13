package ru.suzume.snakefx.objects;

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

import static org.mockito.Mockito.mock;

@ExtendWith(ApplicationExtension.class)
class CactusNodeTest {

    private CactusNode cactusNode;

    private SnakeNode snakeNodeHead;

    private List<SnakeNode> snakeNodeList;
    private List<CactusNode> cactusNodeList;

    @Mock
    private static MainWindowController mwc;


    @BeforeEach
    void beforeEachTest() {
        mwc = mock(MainWindowController.class);
        cactusNode = new CactusNode(1, 1);
        snakeNodeHead = new SnakeNode(1, 1);
        snakeNodeList = new ArrayList<>();
        cactusNodeList = new ArrayList<>();
        snakeNodeList.add(snakeNodeHead);
        cactusNodeList.add(cactusNode);
    }

    @Test
    @DisplayName("Check for add a new cactus")
    void createCactus() {
        CactusNode.createCactus(mwc,cactusNodeList,snakeNodeList,15,15);
        Mockito.verify(mwc, Mockito.times(1)).getCactusList();
    }

    @Test
    @DisplayName("Check for cactus colission")
    void checkCactus() {
        CactusNode.checkCactus(mwc, cactusNodeList, snakeNodeList);
        Mockito.verify(mwc, Mockito.times(1)).gameOver();
    }
}