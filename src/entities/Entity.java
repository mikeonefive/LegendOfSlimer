package entities;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// parent class for player and all other character classes
public abstract class Entity {

    GamePanel gamePanel;

    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNumber = 1;

    // collision stuff, create invisible rectangle around player (but only for the core part of player)
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean isColliding = false;

    public int directionLockCounter = 0;

    List<String> dialogueLines = new ArrayList<>();
    int lineIndex = 0;

    // CHARACTER STATUS
    public int maxHealth;
    public int health;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setDirection() {
    }


    public void setDialogue(String filename) {
        Path dialoguePath = Paths.get("assets/dialogue/" + filename);
        try {
            String content = Files.readString(dialoguePath);
            String[] blocks = content.split("\\r?\\n\\r?\\n"); // split by empty lines, returns an array -> convert to list
            dialogueLines = Arrays.asList(blocks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void speak() {
        if (lineIndex >= dialogueLines.size()) {
            lineIndex = 0;
        }

        gamePanel.ui.currentDialogueLine = dialogueLines.get(lineIndex);
        lineIndex++;

        // make NPC face player when talking
        switch(gamePanel.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void update() {

        setDirection();

        isColliding = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        boolean isCollidingWithPlayer = gamePanel.collisionChecker.checkPlayer(this);

        // if entity is not colliding with anything it can move
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
        } // else if (isCollidingWithPlayer)
            // System.out.println("Entity: Colliding with player!");

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

    public BufferedImage setup(String imagePath) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull
                    (getClass().getResourceAsStream(imagePath + ".png")));
            image = utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        return image;
    }


    public void draw (Graphics2D graphics) {

        BufferedImage image = null;

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&  // make sure to only draw tiles around the player
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&  // +/- tilesize so we don't see the black bg
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

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

            graphics.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            // draw entity hitbox
            // graphics.setColor(Color.RED);
            // graphics.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }
}
