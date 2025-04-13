package inputs;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import main.GamePanel;


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

        // pause & unpause game
        if (button.start && gamePanel.gameState == gamePanel.playGame)
            gamePanel.gameState = gamePanel.pauseGame;
        else if (button.start && gamePanel.gameState == gamePanel.pauseGame)
            gamePanel.gameState = gamePanel.playGame;


        // DIALOGUE STATE
        if (gamePanel.gameState == gamePanel.dialogueState && button.a) {
            gamePanel.gameState = gamePanel.playGame;
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