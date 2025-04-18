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


    public Player(GamePanel gp, KeyboardInput keyboardInput, GamepadInput gamepadInput) {
        super(gp);  // call constructor of superclass

        this.keyboardInput = keyboardInput;
        this.gamepadInput = gamepadInput;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;     // because screenwidth/2 would be the upper left of the char
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;    // this puts player in the middle of screen

        solidArea = new Rectangle(8, 20, 25, 30); // collision area for player
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.x = - 6;
        attackArea.y = - 6;
        attackArea.width = 65;
        attackArea.height = 65;

        setDefaultValues();
        getPlayerImages();
        getPlayerAttackImages();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;       // player's position on the world map (map is bigger than our window)
        worldY = gp.tileSize * 21;       // tilesize * 20 because the coordinates in our maps are all tiles (25px)
        speed = 4;                       // and so we can use the coordinates from our map
        direction = "rest";

        // HEALTH
        maxHealth = 6;                          // 6 means 3 hearts (1 life = 1/2 heart)
        health = maxHealth;
    }

    public void getPlayerImages() {
        up1 = setup("/player/walk/up1", gp.tileSize, gp.tileSize);         // setup method scales image for us & returns it
        up2 = setup("/player/walk/up2", gp.tileSize, gp.tileSize);
        up3 = setup("/player/walk/up3", gp.tileSize, gp.tileSize);

        down1 = setup("/player/walk/down1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/walk/down2", gp.tileSize, gp.tileSize);
        down3 = setup("/player/walk/down3", gp.tileSize, gp.tileSize);

        left1 = setup("/player/walk/left1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/walk/left2", gp.tileSize, gp.tileSize);
        left3 = setup("/player/walk/left3", gp.tileSize, gp.tileSize);

        right1 = setup("/player/walk/right1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/walk/right2", gp.tileSize, gp.tileSize);
        right3 = setup("/player/walk/right3", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImages() {                                            // image sizes double the original width/height
        attackUp1 = setup("/player/attack/up1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/player/attack/up2", gp.tileSize, gp.tileSize * 2);

        attackDown1 = setup("/player/attack/down1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/player/attack/down2", gp.tileSize, gp.tileSize * 2);

        attackLeft1 = setup("/player/attack/left1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/player/attack/left2", gp.tileSize * 2, gp.tileSize);

        attackRight1 = setup("/player/attack/right1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/player/attack/right2", gp.tileSize * 2, gp.tileSize);
    }

    public void update() {

        gamepadInput.handleGamepadInput();

        // Check if player is starting an attack (this must go outside the movement block)
        if (!isAttacking && (keyboardInput.returnPressed || gamepadInput.isApressed)) {
            isAttacking = true;
        }

        // If currently attacking, run the attack and return early
        if (isAttacking) {
            spriteCounter = 0;
            attack();
            return; // Skip movement logic while attacking
        }

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

            // CHECK COLLISION WITH NPCs
            int npcIndex = gp.collisionChecker.checkEntity(this, gp.npcs);
            interactWithNpc(npcIndex);

            // CHECK COLLISION WITH ENEMIES
            int enemyIndex = gp.collisionChecker.checkEntity(this, gp.enemies);
            applyDamageFromEnemy(enemyIndex);

            // CHECK EVENT
            gp.eventHandler.checkEvent();

            // gamePanel.keyboardInput.returnPressed = false;

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

        if (isInDamageCooldown) {
            damageCooldownTimer++;
            if (damageCooldownTimer > 60) {
                isInDamageCooldown = false;
                damageCooldownTimer = 0;
            }
        }
    }

    private void attack() {
        attackSpriteCounter++;
        if (attackSpriteCounter <= 5) {
            spriteNumber = 1;
        }
        if (attackSpriteCounter > 5 && attackSpriteCounter <= 25) {
            spriteNumber = 2;

            // we temporarily modify these because the attackBox is different from hitbox
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // offset playerX and Y by their attackbox
            switch(direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // check enemy collision with updated worldX & worldY & solidArea
            int enemyIndex = gp.collisionChecker.checkEntity(this, gp.enemies);
            dealDamageToEnemy(enemyIndex);

            // after checking the attack collision set the coordinates back to where they were
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }

        if (attackSpriteCounter > 25) {
            spriteNumber = 1;
            attackSpriteCounter = 0;
            isAttacking = false;
        }
    }

    public void interactWithNpc(int npcIndex) {
        if (npcIndex != Constants.EMPTY_AREA) {                 // if player collides with NPC
            gp.gameState = DIALOGUE;
            gp.npcs[npcIndex].speak();
        }
    }

    private void applyDamageFromEnemy(int enemyIndex) {
        if (enemyIndex != EMPTY_AREA) {
            if (!isInDamageCooldown) {
                health -= 1;
                isInDamageCooldown = true;
            }
        }
    }


    public void dealDamageToEnemy(int index) {
        if (index != EMPTY_AREA) {
            if (!gp.enemies[index].isInDamageCooldown) {
                gp.enemies[index].health -= 1;
                gp.enemies[index].isInDamageCooldown = true;

                if (gp.enemies[index].health <= 0) {
                    gp.enemies[index].isAlive = false;
                    gp.enemies[index] = null;
                }
            }
        }
    }


    public void pickupObject(int index) {

        if (index != EMPTY_AREA) {                 // if player didn't touch any object 999 is not used in our objects array
        }
    }


    public void draw(Graphics2D graphics) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "rest":
                image = down2;
                break;

            case "up":
                if (!isAttacking) {
                    if (spriteNumber == 1) {
                        image = up1;
                    }
                    if (spriteNumber == 2) {
                        image = up2;
                    }
                    if (spriteNumber == 3) {
                        image = up3;
                    }
                }
                if (isAttacking) {
                    tempScreenY = screenY - gp.tileSize;    // for the offset in these attack images
                    if (spriteNumber == 1)
                        image = attackUp1;

                    if (spriteNumber == 2)
                        image = attackUp2;
                }
                break;

            case "down":
                if (!isAttacking) {
                    if (spriteNumber == 1) {
                        image = down1;
                    }
                    if (spriteNumber == 2) {
                        image = down2;
                    }
                    if (spriteNumber == 3) {
                        image = down3;
                    }
                }
                if (isAttacking) {
                    if (spriteNumber == 1)
                        image = attackDown1;

                    if (spriteNumber == 2)
                        image = attackDown2;
                }
                break;

            case "left":
                if (!isAttacking) {
                    if (spriteNumber == 1) {
                        image = left1;
                    }
                    if (spriteNumber == 2) {
                        image = left2;
                    }
                    if (spriteNumber == 3) {
                        image = left3;
                    }
                }
                if (isAttacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNumber == 1)
                        image = attackLeft1;

                    if (spriteNumber == 2)
                        image = attackLeft2;
                }
                break;

            case "right":
                if (!isAttacking) {
                    if (spriteNumber == 1) {
                        image = right1;
                    }
                    if (spriteNumber == 2) {
                        image = right2;
                    }
                    if (spriteNumber == 3) {
                        image = right3;
                    }
                }
                if (isAttacking) {
                    if (spriteNumber == 1)
                        image = attackRight1;

                    if (spriteNumber == 2)
                        image = attackRight2;
                }
                break;
        }

        if (isInDamageCooldown) {
            // opacity/alpha channel so we see if we're in damage cooldown
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        graphics.drawImage(image, tempScreenX, tempScreenY, null);

        // RESET ALPHA
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // DEBUG
        // drawHitbox(graphics, screenX, screenY);
        // screenX & screenY don't change but the background changes
        drawAttackArea(graphics, screenX, screenY);
        // graphics.setFont(new Font("Arial", Font.PLAIN, 25));
        // graphics.setColor(Color.YELLOW);
        // graphics.drawString("damage cooldown: " + damageCooldownTimer, 20, 400);
    }
}
