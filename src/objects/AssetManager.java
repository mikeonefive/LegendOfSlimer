package objects;

import entities.Flamer;
import entities.Pink;
import main.GamePanel;

public class AssetManager {
    GamePanel gp;

    public AssetManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjects() {
//        gp.objects[0] = new Hasselhoff(gp);
//        gp.objects[0].worldX = 21 * gp.tileSize;
//        gp.objects[0].worldY = 22 * gp.tileSize;
//
//        gp.objects[1] = new Plant(gp);
//        gp.objects[1].worldX = 23 * gp.tileSize;
//        gp.objects[1].worldY = 25 * gp.tileSize;
    }

    public void setNpc() {
        gp.npcs[0] = new Pink(gp);
        gp.npcs[0].worldX = gp.tileSize * 21;
        gp.npcs[0].worldY = gp.tileSize * 21;
    }

    public void setEnemies() {
        gp.enemies[0] = new Flamer(gp);
        gp.enemies[0].worldX = 23 * gp.tileSize;
        gp.enemies[0].worldY = 36 * gp.tileSize;

        gp.enemies[1] = new Flamer(gp);
        gp.enemies[1].worldX = 23 * gp.tileSize;
        gp.enemies[1].worldY = 37 * gp.tileSize;
    }
}