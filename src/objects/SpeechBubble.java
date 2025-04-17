package objects;

import entities.Entity;
import main.GamePanel;

public class SpeechBubble extends Entity {

    public SpeechBubble(GamePanel gp) {
        super(gp);
        name = "Speech Bubble";
        down1 = setup("/objects/v0/speechbubble");
    }
}