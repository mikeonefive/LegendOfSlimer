package entities;
import main.GamePanel;

import java.util.Random;

public class Flamer extends Entity {

    public Flamer(GamePanel gamePanel) {
        super(gamePanel);
        name = "Flamer";
        speed = 3;
        maxHealth = 4;
        health = maxHealth;
        type = EntityType.ENEMY;

        solidArea.x = 9;
        solidArea.y = 5;
        solidArea.width = 30;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImages();
    }

    public void getImages() {

        int customSize = (int)(gp.tileSize * 1.4);
        up1 = setup("/flamer/up1", customSize, customSize);
        up2 = setup("/flamer/up2", customSize, customSize);
        up3 = setup("/flamer/up3", customSize,customSize);
        down1 = setup("/flamer/up3", customSize,customSize);
        down2 = setup("/flamer/up1", customSize,customSize);
        down3 = setup("/flamer/up2", customSize,customSize);
        left1 = setup("/flamer/left1", customSize,customSize);
        left2 = setup("/flamer/left2", customSize,customSize);
        left3 = setup("/flamer/left3", customSize,customSize);
        right1 = setup("/flamer/right1", customSize,customSize);
        right2 = setup("/flamer/right2", customSize,customSize);
        right3 = setup("/flamer/right3", customSize,customSize);
    }


    public void setDirection() {
        directionLockCounter++;

        // if a direction is picked it won't be changed in the next 180 frames (3 secs)
        if (directionLockCounter == 180) {
            Random random = new Random();
            int randomNumber = random.nextInt(1, 101);

            if (randomNumber <= 25)
                direction = "up";
            if (randomNumber > 25 && randomNumber <= 50)
                direction = "down";
            if (randomNumber > 50 && randomNumber <= 75)
                direction = "left";
            if (randomNumber > 75 && randomNumber <= 100)
                direction = "right";

            directionLockCounter = 0;
        }
    }
}
