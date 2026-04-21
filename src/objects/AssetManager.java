package objects;

import entities.Flamer;
import entities.Purple;
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
        gp.npcs[0] = new Purple(gp);
        gp.npcs[0].worldX = gp.tileSize * 21;
        gp.npcs[0].worldY = gp.tileSize * 21;
    }

    public void setEnemies() {
        int i = 0;

        gp.enemies[i] = new Flamer(gp);
        gp.enemies[i].worldX = 23 * gp.tileSize;
        gp.enemies[i].worldY = 36 * gp.tileSize;
        i++;

        gp.enemies[i] = new Flamer(gp);
        gp.enemies[i].worldX = 23 * gp.tileSize;
        gp.enemies[i].worldY = 37 * gp.tileSize;
        i++;

        gp.enemies[i] = new Flamer(gp);
        gp.enemies[i].worldX = 24 * gp.tileSize;
        gp.enemies[i].worldY = 40 * gp.tileSize;
        i++;

        gp.enemies[i] = new Flamer(gp);
        gp.enemies[i].worldX = 34 * gp.tileSize;
        gp.enemies[i].worldY = 42 * gp.tileSize;
        i++;

        gp.enemies[i] = new Flamer(gp);
        gp.enemies[i].worldX = 38 * gp.tileSize;
        gp.enemies[i].worldY = 42 * gp.tileSize;
        i++;
    }
}