package objects;

import entities.Entity;
import main.GamePanel;

public class Chest extends Entity {

    public Chest(GamePanel gp) {
        super(gp);
        name = "Chest";
        down1 = setup("/objects/v0/chest");
        isColliding = true;
    }
}