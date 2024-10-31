package entity;

import inputs.GamepadInput;
import main.GamePanel;
import inputs.KeyboardInput;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gp;
    KeyboardInput keyboardInput;
    GamepadInput gamepadInput;

    public final int screenX;       // these indicate where we draw our player on the screen (and we move the background)
    public final int screenY;
    public int numberOfKeysPickedUp = 0;
    public boolean hasChestKey = false;
    int restingCounter = 0;


    public Player(GamePanel gp, KeyboardInput keyboardInput, GamepadInput gamepadInput) {
        this.gp = gp;
        this.keyboardInput = keyboardInput;
        this.gamepadInput = gamepadInput;

        screenX = gp.screenWidth/2 - gp.tileSize/2;     // because screenwidth/2 would be the upper left of the char
        screenY = gp.screenHeight/2 - gp.tileSize/2;    // this puts player in the middle of screen

        solidArea = new Rectangle(8, 20, 25, 30); // collision area for player

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;       // player's position on the world map (map is bigger than our window)
        worldY = gp.tileSize * 21;       // tilesize * 20 because the coordinates in our maps are all tiles (25px)
        speed = 4;                       // and so we can use the coordinates from our map
        direction = "rest";
    }

    public void getPlayerImage() {
        up1 = setup("up1");         // setup method scales image for us & returns it
        up2 = setup("up2");
        up3 = setup("up3");

        down1 = setup("down1");
        down2 = setup("down2");
        down3 = setup("down3");

        left1 = setup("left1");
        left2 = setup("left2");
        left3 = setup("left3");

        right1 = setup("right1");
        right2 = setup("right2");
        right3 = setup("right3");
    }

    public BufferedImage setup(String fileName) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull
                    (getClass().getResourceAsStream("/player/" + fileName + ".png")));
            image = utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        return image;
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
            gp.collisionChecker.checkTile(this);

            // CHECK OBJECT COLLISIONS (if player runs into an object like a key or door)
            int objectIndex = gp.collisionChecker.checkObject(this, true);
            pickupObject(objectIndex);

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

    public void pickupObject(int index) {

        if (index != 999) {                 // if player didn't touch any object 999 is not used in our objects array
            String objectName = gp.objects[index].name;

            switch (objectName) {
                case "Key":
                    gp.playSoundEffect(1);
                    numberOfKeysPickedUp += 1;
                    gp.objects[index] = null;   // deletes object we just touched -> we picked it up
                    gp.ui.setMessage("I found a key!");
                    break;

                case "Gate":
                    if (numberOfKeysPickedUp > 0) {
                        gp.playSoundEffect(3);
                        gp.objects[index] = null;
                        numberOfKeysPickedUp -= 1;
                    } else {
                        gp.ui.setMessage("I don't have a key!");
                    }
                    break;

                case "Skull":
                    gp.playSoundEffect(2);
                    speed += 2;
                    gp.objects[index] = null;
                    gp.ui.setMessage("Yay! Speed!");
                    break;

                case "Plant":
                    gp.playSoundEffect(5);
                    speed -= 3;
                    gp.objects[index] = null;
                    gp.ui.setMessage("Oh no! Slowing down!");
                    break;

                case "Hasselhoff":
                    gp.playSoundEffect(2);
                    speed += 3;
                    gp.objects[index] = null;
                    gp.ui.setMessage("I found Hasselhoff!");
                    break;

                case "ChestKey":
                    gp.playSoundEffect(1);
                    hasChestKey = true;
                    gp.objects[index] = null;
                    gp.ui.setMessage("I found a key!");
                    break;

                case "Chest":
                    if (hasChestKey) {
                        gp.ui.gameOver = true;
                        // gp.stopMusic();
                        gp.playSoundEffect(4);
                    } else {
                        gp.ui.setMessage("I don't have a key!");
                    }
                    break;
            }
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
        // graphics.setColor(Color.RED);
        // graphics.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        // screenX & screenY don't change but the background changes
    }
}
