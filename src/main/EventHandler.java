package main;

import java.awt.*;

import static constants.Constants.DIALOGUE;

public class EventHandler {

    GamePanel gp;
    Rectangle eventRectangle;
    int eventRectDefaultX, eventRectDefaultY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRectangle = new Rectangle();
        eventRectangle.x = 23;
        eventRectangle.y = 23;
        eventRectangle.width = 2;
        eventRectangle.height = 2;

        eventRectDefaultX = eventRectangle.x;
        eventRectDefaultY = eventRectangle.y;
    }

    public void checkEvent() {
        if (isColliding(27, 16, "right")) {
            receiveDamageFromPit(DIALOGUE);
        }

        if (isColliding(23, 12, "up")) {
            increaseHealthDrinkingWater(DIALOGUE);
        }
    }

    public boolean isColliding(int eventCol, int eventRow, String requiredDirection) {
        boolean isHit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRectangle.x = eventCol * gp.tileSize + eventRectangle.x;
        eventRectangle.y = eventRow * gp.tileSize + eventRectangle.y;

        if (gp.player.solidArea.intersects(eventRectangle)) {
            if (gp.player.direction.contentEquals(requiredDirection) || requiredDirection.contentEquals("any")) {
                isHit = true;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRectangle.x = eventRectDefaultX;
        eventRectangle.y = eventRectDefaultY;

        return isHit;
    }

    public void receiveDamageFromPit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogueLine = "Aaaaaaahhhhh! I fell into that pit!";
        gp.player.health -= 1;
    }

    public void increaseHealthDrinkingWater(int gameState) {
        if (gp.keyboardInput.returnPressed || gp.gamepadInput.isApressed) {
            gp.gameState = gameState;
            gp.ui.currentDialogueLine = "Wow!\nThat water is refreshing\nand I already feel so much better!";
            if (gp.player.health != gp.player.maxHealth)
                gp.player.health += 1;
        }
    }
}
