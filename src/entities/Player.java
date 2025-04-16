package entities;

import constants.Constants;
import inputs.GamepadInput;
import main.GamePanel;
import inputs.KeyboardInput;

import java.awt.*;
import java.awt.image.BufferedImage;

import static constants.Constants.*;


public class Player extends Entity {

    KeyboardInput keyboardInput;
    GamepadInput gamepadInput;

    public final int screenX;       // these indicate where we draw our player on the screen (and we move the background)
    public final int screenY;

    int restingCounter = 0;


    public Player(GamePanel gamePanel, KeyboardInput keyboardInput, GamepadInput gamepadInput) {
        super(gamePanel);  // call constructor of superclass

        this.keyboardInput = keyboardInput;
        this.gamepadInput = gamepadInput;

        screenX = gamePanel.screenWidth/2 - gamePanel.tileSize/2;     // because screenwidth/2 would be the upper left of the char
        screenY = gamePanel.screenHeight/2 - gamePanel.tileSize/2;    // this puts player in the middle of screen

        solidArea = new Rectangle(8, 20, 25, 30); // collision area for player

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;       // player's position on the world map (map is bigger than our window)
        worldY = gamePanel.tileSize * 21;       // tilesize * 20 because the coordinates in our maps are all tiles (25px)
        speed = 4;                       // and so we can use the coordinates from our map
        direction = "rest";

        // HEALTH
        maxHealth = 6;                          // 6 means 3 hearts (1 life = 1/2 heart)
        health = maxHealth;
    }

    public void getPlayerImage() {
        up1 = setup("/player/up1");         // setup method scales image for us & returns it
        up2 = setup("/player/up2");
        up3 = setup("/player/up3");

        down1 = setup("/player/down1");
        down2 = setup("/player/down2");
        down3 = setup("/player/down3");

        left1 = setup("/player/left1");
        left2 = setup("/player/left2");
        left3 = setup("/player/left3");

        right1 = setup("/player/right1");
        right2 = setup("/player/right2");
        right3 = setup("/player/right3");
    }

    public void update() {

        gamepadInput.handleGamepadInput();

        if (keyboardInput.upPressed || keyboardInput.downPressed
        || keyboardInput.leftPressed || keyboardInput.rightPressed
        || gamepadInput.getIsGamepadUp() || gamepadInput.getIsGamepadDown()
        || gamepadInput.getIsGamepadLeft() || gamepadInput.getIsGamepadRight())
        {
            if (keyboardInput.upPressed || gamepadInput.getIsGamepadUp()) {
                direction = "up";
            }
            if (keyboardInput.downPressed || gamepadInput.getIsGamepadDown()) {
                direction = "down";
            }
            if (keyboardInput.leftPressed || gamepadInput.getIsGamepadLeft()) {
                direction = "left";
            }
            if (keyboardInput.rightPressed || gamepadInput.getIsGamepadRight()) {
                direction = "right";
            }

            // CHECK TILE COLLISIONS (if player runs into solid tile)
            isColliding = false;
            gamePanel.collisionChecker.checkTile(this);

            // CHECK OBJECT COLLISIONS (if player runs into an object like a key or door)
            int objectIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickupObject(objectIndex);

            // CHECK COLLISION WITH NPCs
            int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npcs);
            interactWithNpc(npcIndex);

            // if isColliding is still false, the player can move in that direction
            if (!isColliding) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter ++;
            if (spriteCounter > 10) {   // every 10 frames the player image will change
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                }
                else if (spriteNumber == 2) {
                    spriteNumber = 3;
                }
                else {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        }
        else { // if not pressing any keys sprite is 2 (resting position)
            restingCounter ++;

            if (restingCounter == 20) { // 20 frames time buffer for getting back to resting position
                spriteNumber = 2;
                restingCounter = 0;
            }
        }
    }

    public void interactWithNpc(int npcIndex) {
        if (npcIndex != Constants.EMPTY_AREA) {                 // if player collides with NPC
            gamePanel.gameState = DIALOGUE;
            gamePanel.npcs[npcIndex].speak();
        }
    }

    public void pickupObject(int index) {

        if (index != Constants.EMPTY_AREA) {                 // if player didn't touch any object 999 is not used in our objects array
        }
    }

    public void draw(Graphics2D graphics) {

        BufferedImage image = null;

        switch (direction) {
            case "rest":
                image = down2;
                break;

            case "up":
                if (spriteNumber == 1) {
                    image = up1;
                }
                if (spriteNumber == 2) {
                    image = up2;
                }
                if (spriteNumber == 3) {
                    image = up3;
                }
                break;

            case "down":
                if (spriteNumber == 1) {
                    image = down1;
                }
                if (spriteNumber == 2) {
                    image = down2;
                }
                if (spriteNumber == 3) {
                    image = down3;
                }
                break;

            case "left":
                if (spriteNumber == 1) {
                    image = left1;
                }
                if (spriteNumber == 2) {
                    image = left2;
                }
                if (spriteNumber == 3) {
                    image = left3;
                }
                break;

            case "right":
                if (spriteNumber == 1) {
                    image = right1;
                }
                if (spriteNumber == 2) {
                    image = right2;
                }
                if (spriteNumber == 3) {
                    image = right3;
                }
                break;
        }

        graphics.drawImage(image, screenX, screenY, null);

        // draw player hitbox
        graphics.setColor(Color.RED);
        graphics.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        // screenX & screenY don't change but the background changes
    }
}
