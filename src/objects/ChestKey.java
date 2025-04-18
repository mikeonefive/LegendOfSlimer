package objects;

import entities.Entity;
import main.GamePanel;

public class ChestKey extends Entity {

    public ChestKey(GamePanel gp) {
        super(gp);
        name = "ChestKey";
        down1 = setup("/objects/v0/chestkey", gp.tileSize, gp.tileSize);
    }
}