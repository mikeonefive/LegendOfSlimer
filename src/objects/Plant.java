package objects;

import entities.Entity;
import main.GamePanel;


public class Plant extends Entity {

    public Plant(GamePanel gp) {
        super(gp);
        name = "Plant";
        down1 = setup("/objects/v0/plant", gp.tileSize, gp.tileSize);
    }
}