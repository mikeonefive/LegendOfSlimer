package entities;
import main.GamePanel;

import java.util.Random;

public class Flamer extends Entity {

    public Flamer(GamePanel gamePanel) {
        super(gamePanel);
        this.name = "Flamer";
        this.speed = 3;
        this.maxHealth = 10;
        this.health = maxHealth;
        this.type = EntityType.ENEMY;
        this.attack = 2;
        this.defense = 0;
        this.experience = 2;

        this.solidArea.x = 9;
        this.solidArea.y = 5;
        this.solidArea.width = 30;
        this.solidArea.height = 40;
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;

        this.getImages();
    }

    public void getImages() {

        int customSize = (int)(gp.tileSize * 1.4);
        this.up1 = setup("/flamer/up1", customSize, customSize);
        this.up2 = setup("/flamer/up2", customSize, customSize);
        this.up3 = setup("/flamer/up3", customSize,customSize);
        this.down1 = setup("/flamer/up3", customSize,customSize);
        this.down2 = setup("/flamer/up1", customSize,customSize);
        this.down3 = setup("/flamer/up2", customSize,customSize);
        this.left1 = setup("/flamer/left1", customSize,customSize);
        this.left2 = setup("/flamer/left2", customSize,customSize);
        this.left3 = setup("/flamer/left3", customSize,customSize);
        this.right1 = setup("/flamer/right1", customSize,customSize);
        this.right2 = setup("/flamer/right2", customSize,customSize);
        this.right3 = setup("/flamer/right3", customSize,customSize);
    }


    @Override
    public void setDirection() {
        this.directionLockCounter++;

        // if a direction is picked it won't be changed in the next 180 frames (3 secs)
        if (this.directionLockCounter == 180) {
            Random random = new Random();
            int randomNumber = random.nextInt(1, 101);

            if (randomNumber <= 25)
                this.direction = "up";
            if (randomNumber > 25 && randomNumber <= 50)
                this.direction = "down";
            if (randomNumber > 50 && randomNumber <= 75)
                this.direction = "left";
            if (randomNumber > 75 && randomNumber <= 100)
                this.direction = "right";

            this.directionLockCounter = 0;
        }
    }

    @Override
    public void reactToAttack() {

        // if it receives damage, starts moving away from player
        this.directionLockCounter = 0;
        this.direction = gp.player.direction;

    }
}
