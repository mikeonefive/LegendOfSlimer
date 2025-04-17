package objects;

import entities.Entity;
import main.GamePanel;


public class PlayerHeart extends Entity {

    public PlayerHeart(GamePanel gp) {
        super(gp);
        name = "PlayerHealth";
        image1 = setup("/objects/heart_full");
        image2 = setup("/objects/heart_half");
        image3 = setup("/objects/heart_blank");
    }
}
