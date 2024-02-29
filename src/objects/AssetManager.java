package objects;

import main.GamePanel;

public class AssetManager {
    GamePanel gp;

    public AssetManager(GamePanel gp) {
        this.gp = gp;

    }

    public void setObject() {
        gp.objects[0] = new Key(gp);
        gp.objects[0].worldX = 23 * gp.tileSize;
        gp.objects[0].worldY = 7 * gp.tileSize;

        gp.objects[1] = new Key(gp);
        gp.objects[1].worldX = 23 * gp.tileSize;
        gp.objects[1].worldY = 40 * gp.tileSize;

        gp.objects[2] = new Key(gp);
        gp.objects[2].worldX = 38 * gp.tileSize;
        gp.objects[2].worldY = 8 * gp.tileSize;

        gp.objects[3] = new Gate(gp);
        gp.objects[3].worldX = 10 * gp.tileSize;
        gp.objects[3].worldY = 11 * gp.tileSize;

        gp.objects[4] = new Gate(gp);
        gp.objects[4].worldX = 8 * gp.tileSize;
        gp.objects[4].worldY = 28 * gp.tileSize;

        gp.objects[5] = new Gate(gp);
        gp.objects[5].worldX = 12 * gp.tileSize;
        gp.objects[5].worldY = 22 * gp.tileSize;

        gp.objects[6] = new Chest(gp);
        gp.objects[6].worldX = 10 * gp.tileSize;
        gp.objects[6].worldY = 7 * gp.tileSize;

        gp.objects[7] = new Skull(gp);
        gp.objects[7].worldX = 37 * gp.tileSize;
        gp.objects[7].worldY = 42 * gp.tileSize;

        gp.objects[8] = new Plant(gp);
        gp.objects[8].worldX = 35 * gp.tileSize;
        gp.objects[8].worldY = 35 * gp.tileSize;

        gp.objects[9] = new Hasselhoff(gp);
        gp.objects[9].worldX = 26 * gp.tileSize;
        gp.objects[9].worldY = 36 * gp.tileSize;

        gp.objects[10] = new ChestKey(gp);
        gp.objects[10].worldX = 40 * gp.tileSize;
        gp.objects[10].worldY = 41 * gp.tileSize;

    }
}
