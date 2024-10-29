package inputs;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import main.GamePanel;


public class GamepadInput {
    private final ControllerManager controllers;
    private final GamePanel gamePanel;
    private boolean isGamepadLeft, isGamepadRight, isGamepadUp, isGamepadDown;

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

    public GamepadInput(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        controllers = new ControllerManager();
        controllers.initSDLGamepad();
    }

    public void handleGamepadInput() {
        ControllerState button = this.getButtonPressed();

        // Reset gamepad direction booleans
        isGamepadLeft = false;
        isGamepadRight = false;
        isGamepadUp = false;
        isGamepadDown = false;

        //TODO: there's something wrong here with up and down because Slimer moves up even though there's no input
        // handle directions
        if (button.dpadUp || button.leftStickY > 0.5)
            isGamepadUp = true;
        if (button.dpadDown || button.leftStickY < -0.5)
            isGamepadDown = true;

        if (button.dpadRight || button.leftStickX > 0.5)
            isGamepadRight = true;
        if (button.dpadLeft || button.leftStickX < -0.5)
            isGamepadLeft = true;
    }

    public ControllerState getButtonPressed() {
        ControllerState currentState = this.getControllers().getState(0);
        return currentState;
    }

    public ControllerManager getControllers() {
        return controllers;
    }
}