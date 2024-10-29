package inputs;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;


public class GamepadInput {
    private final ControllerManager controllers;
    private boolean isGamepadLeft, isGamepadRight, isGamepadUp, isGamepadDown;

    public GamepadInput() {
        controllers = new ControllerManager();
        controllers.initSDLGamepad();
    }


    public void handleGamepadInput() {

        resetDirectionBools();

        ControllerState button = this.getButtonPressed();
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