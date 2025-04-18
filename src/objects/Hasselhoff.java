package objects;

import entities.Entity;
import main.GamePanel;


public class Hasselhoff extends Entity {

    public Hasselhoff(GamePanel gp) {
        super(gp);
        name = "Hasselhoff";
        down1 = setup("/objects/v0/hasselhoff", gp.tileSize, gp.tileSize);
    }
}