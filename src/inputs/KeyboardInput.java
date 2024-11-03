package inputs;

import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
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
        int keyCode = e.getKeyCode();   // returns number of key that was pressed

        switch(keyCode) {
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

            // pause ESC
            case KeyEvent.VK_ESCAPE:
                if (gamePanel.gameState == gamePanel.playGame)
                    gamePanel.gameState = gamePanel.pauseGame;
                else if (gamePanel.gameState == gamePanel.pauseGame)
                    gamePanel.gameState = gamePanel.playGame;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int keyCode = e.getKeyCode();

        switch(keyCode)
        {
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