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

    public EntityType type;

    GamePanel gp;

    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public BufferedImage attackUp1, attackUp2, attackUp3, attackDown1, attackDown2, attackDown3,
            attackLeft1, attackLeft2, attackLeft3, attackRight1, attackRight2, attackRight3;

    // STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    boolean isAttacking = false;
    int attackSpriteCounter = 0;

    // COLLISION stuff, create invisible rectangle around player (but only for the core part of player)
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean isColliding = false;
    public boolean isInDamageCooldown = false;
    public int damageCooldownTimer = 0;
    public int directionLockCounter = 0;

    // DIALOGUE
    List<String> dialogueLines = new ArrayList<>();
    int lineIndex = 0;

    // CHARACTER ATTRIBUTES
    public String name;
    public int speed;
    public int maxHealth;
    public int health;
    public boolean isAlive = true;

    public Entity(GamePanel gp) {
        this.gp = gp;
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

        gp.ui.currentDialogueLine = dialogueLines.get(lineIndex);
        lineIndex++;

        // make NPC face player when talking
        switch(gp.player.direction) {
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
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this, false);
        gp.collisionChecker.checkEntity(this, gp.npcs);
        gp.collisionChecker.checkEntity(this, gp.enemies);
        boolean isCollidingWithPlayer = gp.collisionChecker.checkPlayer(this);

        if (this.type == EntityType.ENEMY && isCollidingWithPlayer) {
            if (!gp.player.isInDamageCooldown) {
                gp.player.health -= 1;
                gp.player.isInDamageCooldown = true;
            }
        }

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

        if (isInDamageCooldown) {
            damageCooldownTimer++;
            if (damageCooldownTimer > 40) {
                isInDamageCooldown = false;
                damageCooldownTimer = 0;
            }
        }

    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull
                    (getClass().getResourceAsStream(imagePath + ".png")));

            image = utilityTool.scaleImage(image, width, height);

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        return image;
    }


    public void draw (Graphics2D graphics) {

        if (!isAlive) return;

        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&  // make sure to only draw tiles around the player
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&  // +/- tilesize so we don't see the black bg
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

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

            if (isInDamageCooldown) {
                // opacity/alpha channel so we see if we're in damage cooldown
                graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }

            graphics.drawImage(image, screenX, screenY, image.getWidth(), image.getHeight(), null);

            // RESET ALPHA
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));



            //drawHitbox(graphics, screenX, screenY);
        }
    }


    public void drawHitbox(Graphics2D graphics, int screenX, int screenY) {
        graphics.setColor(Color.RED);
        graphics.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }

    public void drawAttackArea(Graphics2D graphics, int screenX, int screenY) {
        graphics.setColor(Color.YELLOW);
        graphics.drawRect(screenX + attackArea.x, screenY + attackArea.y, attackArea.width, attackArea.height);
    }
}