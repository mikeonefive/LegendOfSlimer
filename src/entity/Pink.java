package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class Pink extends Entity {

    public Pink(GamePanel gamePanel) {

        super(gamePanel);

        direction = "down";
        speed = 1;
        solidArea = new Rectangle(0, 0, 48, 48);

        getImage();
    }

    public void getImage() {
        up1 = setup("/npc/up1");         // setup method scales image for us & returns it
        up2 = setup("/npc/up2");
        up3 = setup("/npc/up3");

        down1 = setup("/npc/down1");
        down2 = setup("/npc/down2");
        down3 = setup("/npc/down3");

        left1 = setup("/npc/left1");
        left2 = setup("/npc/left2");
        left3 = setup("/npc/left3");

        right1 = setup("/npc/right1");
        right2 = setup("/npc/right2");
        right3 = setup("/npc/right3");
    }

    @Override
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
