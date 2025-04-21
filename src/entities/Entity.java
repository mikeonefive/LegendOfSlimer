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
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
            attackLeft1, attackLeft2, attackRight1, attackRight2;

    // STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    boolean isAttacking = false;
    int attackSpriteCounter = 0;
    public boolean isAlive = true;
    public boolean isDying = false;
    int dyingCounter = 0;
    boolean healthBarOn = false;
    int healthBarCounter = 0;

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



    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setDirection() {}

    public void reactToAttack() {}


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

        // deal damage to player
        if (this.type == EntityType.ENEMY && isCollidingWithPlayer) {
            if (!gp.player.isInDamageCooldown) {
                gp.playSoundEffect(7);
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

         if (!isAlive && !isDying)
             return;

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

            // draw entity health bar if ON
            if (type == EntityType.ENEMY && healthBarOn) {
                drawEntityHealthBar(graphics, screenX, screenY);
                healthBarCounter++;

                if (healthBarCounter > 300) {       // 5 secs
                    healthBarCounter = 0;
                    healthBarOn = false;
                }
            }

            if (isInDamageCooldown) {
                healthBarOn = true;
                healthBarCounter = 0;
                // opacity/alpha channel so we see if we're in damage cooldown
                changeOpacity(graphics, 0.4f);
            }

            if (isDying) {
                startDyingAnimation(graphics);
            }

            graphics.drawImage(image, screenX, screenY, image.getWidth(), image.getHeight(), null);

            // RESET ALPHA
            changeOpacity(graphics, 1);

            //drawHitbox(graphics, screenX, screenY);
        }
    }


    private void changeOpacity(Graphics2D g, float value) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, value));
    }


    private void drawEntityHealthBar(Graphics2D graphics, int screenX, int screenY) {
            double oneHealthPoint = (double) gp.tileSize / maxHealth;      // divide bar's length by maxHealth of entity -> length of 1 HP
            int healthbarValue = (int)(oneHealthPoint * health);

            graphics.setColor(Color.BLACK);
            graphics.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);
            graphics.setColor(Color.RED);
            graphics.fillRect(screenX, screenY - 15, healthbarValue, 10);


    }

    public void startDyingAnimation(Graphics2D graphics) {
        dyingCounter++;
        float alphaValue = 0;

        if (dyingCounter <= 40) {
            int phase = (dyingCounter / 5) % 2;         // current 5-frame block (e.g., 0–4 = 0, 5–9 = 1, etc.)

            if (phase == 0)
                alphaValue = 0;
            else
                alphaValue = 1;

            changeOpacity(graphics, alphaValue);

        } else {
            isDying = false;
            isAlive = false;
        }

        changeOpacity(graphics, alphaValue);
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