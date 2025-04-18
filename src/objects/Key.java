package objects;

import entities.Entity;
import main.GamePanel;

public class Key extends Entity {

    public Key(GamePanel gp) {
        super(gp);
        name = "Key";
        down1 = setup("/objects/v0/key", gp.tileSize, gp.tileSize);
    }
}