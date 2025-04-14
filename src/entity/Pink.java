package entity;

import main.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

public class Pink extends Entity {

    public Pink(GamePanel gamePanel) {

        super(gamePanel);

        direction = "down";
        speed = 1;
        solidArea = new Rectangle(0, 0, 48, 48);

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/npc/pink/up1");         // setup method scales image for us & returns it
        up2 = setup("/npc/pink/up2");
        up3 = setup("/npc/pink/up3");

        down1 = setup("/npc/pink/down1");
        down2 = setup("/npc/pink/down2");
        down3 = setup("/npc/pink/down3");

        left1 = setup("/npc/pink/left1");
        left2 = setup("/npc/pink/left2");
        left3 = setup("/npc/pink/left3");

        right1 = setup("/npc/pink/right1");
        right2 = setup("/npc/pink/right2");
        right3 = setup("/npc/pink/right3");
    }


    public void setDialogue() {
        Path dialoguePath = Paths.get("assets/dialogue/pink.txt");
        try {
            String content = Files.readString(dialoguePath);
            String[] blocks = content.split("\\r?\\n\\r?\\n"); // split by empty lines, returns an array -> convert to list
            dialogueLines = Arrays.asList(blocks);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
