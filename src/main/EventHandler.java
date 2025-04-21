package main;

import static constants.Constants.DIALOGUE;

public class EventHandler {

    GamePanel gp;
    EventRectangle[][] eventRectangle;
    int previousEventX, previousEventY;
    boolean canTriggerEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRectangle = new EventRectangle[gp.maxWorldColumn][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldColumn && row < gp.maxWorldRow) {
            eventRectangle[col][row] = new EventRectangle();
            eventRectangle[col][row].x = 23;
            eventRectangle[col][row].y = 23;
            eventRectangle[col][row].width = 2;
            eventRectangle[col][row].height = 2;

            eventRectangle[col][row].defaultX = eventRectangle[col][row].x;
            eventRectangle[col][row].defaultY = eventRectangle[col][row].y;

            col++;

            if (col == gp.maxWorldColumn) {
                col = 0;
                row++;
            }

        }
    }

    public void checkEvent() {

        // check if player more than 1 tile away from last event (then it can be triggered again otherwise not)
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTriggerEvent = true;
        }

        if (canTriggerEvent) {
            if (isCollidingWithEvent(27, 16, "right")) {
                receiveDamageFromPit(DIALOGUE);
            }

            if (isCollidingWithEvent(23, 12, "up")) {
                increaseHealthDrinkingWater(DIALOGUE);
            }
        }
    }

    public boolean isCollidingWithEvent(int col, int row, String requiredDirection) {
        boolean isColliding = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRectangle[col][row].x = col * gp.tileSize + eventRectangle[col][row].x;
        eventRectangle[col][row].y = row * gp.tileSize + eventRectangle[col][row].y;

        if (gp.player.solidArea.intersects(eventRectangle[col][row]) && !eventRectangle[col][row].isEventOver) {
            if (gp.player.direction.contentEquals(requiredDirection) || requiredDirection.contentEquals("any")) {
                isColliding = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRectangle[col][row].x = eventRectangle[col][row].defaultX;
        eventRectangle[col][row].y = eventRectangle[col][row].defaultY;

        return isColliding;
    }

    public void receiveDamageFromPit(int gameState) {
        gp.gameState = gameState;
        gp.playSoundEffect(8);
        gp.ui.currentDialogueLine = "Aaaaaaahhhhh! I fell into that pit!";
        gp.player.health -= 1;

        canTriggerEvent = false;
    }

    public void increaseHealthDrinkingWater(int gameState) {
        if (gp.keyboardInput.returnPressed || gp.gamepadInput.isApressed) {
            gp.gameState = gameState;
            gp.playSoundEffect(9);


            gp.ui.currentDialogueLine = "Wow!\nThat water is refreshing and\nI already feel so much better!";
            if (gp.player.health != gp.player.maxHealth)
                gp.player.health += 1;
        }
        gp.player.preventAttackFromTriggering = true;
    }
}
