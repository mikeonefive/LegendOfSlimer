package objects;

import entities.Pink;
import main.GamePanel;

public class AssetManager {
    GamePanel gp;

    public AssetManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjects() {

    }

    public void setNpc() {
        gp.npcs[0] = new Pink(gp);
        gp.npcs[0].worldX = gp.tileSize * 21;
        gp.npcs[0].worldY = gp.tileSize * 21;
    }
}