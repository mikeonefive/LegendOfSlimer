package objects;

import entities.Entity;
import main.GamePanel;


public class Skull extends Entity {

    public Skull(GamePanel gp) {
        super(gp);
        name = "Skull";
        down1 = setup("/objects/v0/skull", gp.tileSize, gp.tileSize);
    }
}
