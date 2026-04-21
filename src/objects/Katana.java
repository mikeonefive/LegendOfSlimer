package objects;

import entities.Entity;
import main.GamePanel;

public class Katana extends Entity {

    public Katana(GamePanel gp) {
        super(gp);
        name = "Katana Sword";
        down1 = setup("/objects/katanaUI", gp.tileSize, gp.tileSize);
        attackValue = 1;
    }
}
