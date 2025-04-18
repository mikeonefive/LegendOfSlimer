package objects;

import entities.Entity;
import main.GamePanel;


public class Gate extends Entity {

    public Gate(GamePanel gp) {
        super(gp);
        name = "Gate";
        down1 = setup("/objects/v0/gate", gp.tileSize, gp.tileSize);
        isColliding = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
