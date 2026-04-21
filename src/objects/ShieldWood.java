package objects;

import entities.Entity;
import main.GamePanel;

public class ShieldWood extends Entity {
    public ShieldWood(GamePanel gp) {
        super(gp);

        name = "Wooden Shield";
        down1 = setup("/objects/wood_kite_shield", gp.tileSize, gp.tileSize);
        defenseValue = 1;
    }
}
