package entities;

import main.GamePanel;

import java.util.Random;

public class Purple extends Entity {

    public Purple(GamePanel gamePanel) {

        super(gamePanel);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/npc/pink/up1", gp.tileSize, gp.tileSize);         // setup method scales image for us & returns it
        up2 = setup("/npc/pink/up2", gp.tileSize, gp.tileSize);
        up3 = setup("/npc/pink/up3", gp.tileSize, gp.tileSize);

        down1 = setup("/npc/pink/down1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/pink/down2", gp.tileSize, gp.tileSize);
        down3 = setup("/npc/pink/down3", gp.tileSize, gp.tileSize);

        left1 = setup("/npc/pink/left1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/pink/left2", gp.tileSize, gp.tileSize);
        left3 = setup("/npc/pink/left3", gp.tileSize, gp.tileSize);

        right1 = setup("/npc/pink/right1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/pink/right2", gp.tileSize, gp.tileSize);
        right3 = setup("/npc/pink/right3", gp.tileSize, gp.tileSize);
    }


    public void setDialogue() {
        super.setDialogue("pink.txt");
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

    public void speak() {
        // here it can also speak character-specific stuff
        super.speak();
    }
}
