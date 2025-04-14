package inputs;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import main.GamePanel;

import static constants.Constants.*;


public class GamepadInput {
    private final GamePanel gamePanel;
    private final ControllerManager controllers;
    private boolean isGamepadLeft, isGamepadRight, isGamepadUp, isGamepadDown;
    public boolean isApressed;

    public GamepadInput(GamePanel gamePanel) {
        controllers = new ControllerManager();
        controllers.initSDLGamepad();
        this.gamePanel = gamePanel;
    }


    public void handleGamepadInput() {

        resetDirectionBools();

        ControllerState button = this.getButtonPressed();
        // System.out.println("Start button state: " + button.start);


        // handle directions
        if (button.dpadUp || button.leftStickY > 0.5)
            isGamepadUp = true;
        if (button.dpadDown || button.leftStickY < -0.5)
            isGamepadDown = true;

        if (button.dpadRight || button.leftStickX > 0.5)
            isGamepadRight = true;
        if (button.dpadLeft || button.leftStickX < -0.5)
            isGamepadLeft = true;

        // talking to NPC
        if (button.a)
            isApressed = true;

        // PAUSE & UNPAUSE GAME
        if (button.start && gamePanel.gameState == PLAY_GAME)
            gamePanel.gameState = PAUSE_GAME;
        else if (button.start && gamePanel.gameState == PAUSE_GAME)
            gamePanel.gameState = PLAY_GAME;

        // DIALOGUE STATE
        if (gamePanel.gameState == DIALOGUE && button.a) {
            gamePanel.gameState = PLAY_GAME;
        }

        // TITLE SCREEN STATE
        if (gamePanel.gameState == TITLE_SCREEN) {
            if (isGamepadUp) {
                gamePanel.ui.commandNumber--;
                if (gamePanel.ui.commandNumber < 0)
                    gamePanel.ui.commandNumber = 2;
            }
            if (isGamepadDown) {
                gamePanel.ui.commandNumber++;
                if (gamePanel.ui.commandNumber > 2)
                    gamePanel.ui.commandNumber = 0;
            }

            if (button.a && gamePanel.ui.commandNumber == 0) {
                gamePanel.gameState = PLAY_GAME;
                // gamePanel.playMusic(0);
            }
            if (button.a && gamePanel.ui.commandNumber == 1) {
                // load game
            }
            if (button.a && gamePanel.ui.commandNumber == 2) {
                System.exit(0);
            }
        }
    }

    private void resetDirectionBools() {
        // Reset gamepad direction booleans
        isGamepadLeft = false;
        isGamepadRight = false;
        isGamepadUp = false;
        isGamepadDown = false;
    }

    public ControllerState getButtonPressed() {
        ControllerState currentState = this.getControllers().getState(0);
        return currentState;
    }

    public ControllerManager getControllers() {
        return controllers;
    }

    public boolean getIsGamepadLeft() {
        return isGamepadLeft;
    }

    public boolean getIsGamepadRight() {
        return isGamepadRight;
    }

    public boolean getIsGamepadUp() {
        return isGamepadUp;
    }

    public boolean getIsGamepadDown() {
        return isGamepadDown;
    }
}