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
        resetPressedButtons();

        ControllerState button = this.getButtonPressed();

        // PLAY GAME STATE handle directions
        handleDirectionInput(button);

        // talking to NPC, getting health back from water
        if (button.a)
            isApressed = true;
        // show player stats
        if (button.rb)
            gamePanel.gameState = SHOW_CHARACTER_STATS;

        // PAUSE & UNPAUSE GAME
        if (button.start && gamePanel.gameState == PLAY_GAME)
            gamePanel.gameState = PAUSE_GAME;
        else if (button.start && gamePanel.gameState == PAUSE_GAME)
            gamePanel.gameState = PLAY_GAME;

        // DIALOGUE STATE
        if (gamePanel.gameState == DIALOGUE) {
            handleDialogueStateInput(button);
        }

        // SHOW STATS
        if (gamePanel.gameState == SHOW_CHARACTER_STATS) {
            handleStatsStateInput(button);
        }

        // TITLE SCREEN STATE
        if (gamePanel.gameState == TITLE_SCREEN) {
            handleTitleScreenInput(button);
        }
    }


    private void handleStatsStateInput(ControllerState button) {
        if (button.rb) {
            gamePanel.gameState = PLAY_GAME;
        }
    }


    private void handleDialogueStateInput(ControllerState button) {
        if (button.a) {
            gamePanel.gameState = PLAY_GAME;
        }
    }


    private void handleTitleScreenInput(ControllerState button) {
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


    private void handleDirectionInput(ControllerState button) {
        if (button.dpadUp || button.leftStickY > 0.5)
            isGamepadUp = true;
        if (button.dpadDown || button.leftStickY < -0.5)
            isGamepadDown = true;

        if (button.dpadRight || button.leftStickX > 0.5)
            isGamepadRight = true;
        if (button.dpadLeft || button.leftStickX < -0.5)
            isGamepadLeft = true;
    }


    private void resetDirectionBools() {
        // Reset gamepad direction booleans
        isGamepadLeft = false;
        isGamepadRight = false;
        isGamepadUp = false;
        isGamepadDown = false;
    }


    private void resetPressedButtons() {
        isApressed = false;
    }


    public ControllerState getButtonPressed() {
        return this.getControllers().getState(0);
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