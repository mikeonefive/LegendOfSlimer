package inputs;

import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static constants.Constants.*;

public class KeyboardInput implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, returnPressed;
    private final GamePanel gamePanel;

    //DEBUG
    public static boolean checkDrawingTime = false;

    public KeyboardInput(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();           // returns number of key that was pressed

        if (gamePanel.gameState == PLAY_GAME) {
            handlePlayGameInput(keyCode);
        }

        // TITLE SCREEN STATE
        else if (gamePanel.gameState == TITLE_SCREEN) {
           handleTitleScreenInput(keyCode);
        }
        // SHOW CHARACTER STATS STATE
        else if (gamePanel.gameState == SHOW_CHARACTER_STATS) {
            handleCharacterStateInput(keyCode);
        }

        // PAUSE STATE
        handlePauseStateInput(keyCode);

        // DIALOGUE STATE
        handleDialogueStateInput(keyCode);

    }

    private void handleTitleScreenInput(int keyCode) {
        switch(keyCode) {
            case KeyEvent.VK_W:
                gamePanel.ui.commandNumber--;
                if (gamePanel.ui.commandNumber < 0)
                    gamePanel.ui.commandNumber = 2;
                break;
            case KeyEvent.VK_S:
                gamePanel.ui.commandNumber++;
                if (gamePanel.ui.commandNumber > 2)
                    gamePanel.ui.commandNumber = 0;
                break;

            case KeyEvent.VK_ENTER:
                if (gamePanel.ui.commandNumber == 0) {
                    gamePanel.gameState = PLAY_GAME;
                    // gamePanel.playMusic(0);
                }
                if (gamePanel.ui.commandNumber == 1) {
                    // load game
                }
                if (gamePanel.ui.commandNumber == 2) {
                    System.exit(0);
                }
        }
    }

    private void handlePlayGameInput(int keyCode) {

        // PLAY GAME STATE
        switch (keyCode) {
            case KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_S:
                downPressed = true;
                break;
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            case KeyEvent.VK_ENTER:
                returnPressed = true;
                break;
            case KeyEvent.VK_C:
                gamePanel.gameState = SHOW_CHARACTER_STATS;
                break;
        }
    }

    private void handlePauseStateInput(int keyCode) {
        if (gamePanel.gameState == PAUSE_GAME && keyCode == KeyEvent.VK_ESCAPE)
            gamePanel.gameState = PLAY_GAME;
        else if (gamePanel.gameState == PLAY_GAME && keyCode == KeyEvent.VK_ESCAPE)
            gamePanel.gameState = PAUSE_GAME;
    }

    private void handleDialogueStateInput(int keyCode) {
        if (gamePanel.gameState == DIALOGUE && keyCode == KeyEvent.VK_ENTER) {
            gamePanel.gameState = PLAY_GAME;
        }
    }

    private void handleCharacterStateInput(int keyCode) {
        if (keyCode == KeyEvent.VK_C)
            gamePanel.gameState = PLAY_GAME;
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch(keyCode) {
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
            case KeyEvent.VK_ENTER:
                returnPressed = false;
                break;

            //DEBUG
            case KeyEvent.VK_T:
                if (checkDrawingTime == false) {
                    checkDrawingTime = true;
                }
                else {
                    checkDrawingTime = false;
                }
                break;
        }
    }
}