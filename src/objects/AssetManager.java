package objects;

import entities.Pink;
import main.GamePanel;

public class AssetManager {
    GamePanel gp;

    public AssetManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjects() {
        gp.objects[0] = new Gate(gp);
        gp.objects[0].worldX = 21 * gp.tileSize;
        gp.objects[0].worldY = 22 * gp.tileSize;

        gp.objects[1] = new Gate(gp);
        gp.objects[1].worldX = 23 * gp.tileSize;
        gp.objects[1].worldY = 25 * gp.tileSize;
    }

    public void setNpc() {
        gp.npcs[0] = new Pink(gp);
        gp.npcs[0].worldX = gp.tileSize * 21;
        gp.npcs[0].worldY = gp.tileSize * 21;
    }
}