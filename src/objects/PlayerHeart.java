package objects;

import entities.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;


public class PlayerHeart extends Entity {
    public BufferedImage image1, image2, image3;

    public PlayerHeart(GamePanel gp) {
        super(gp);
        name = "PlayerHealth";
        image1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/heart_blank", gp.tileSize, gp.tileSize);
    }
}
