package objects;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

// parent class for all object classes
public class Entity {
    public BufferedImage image1, image2, image3;
    public String name;
    public boolean isColliding = false;
    public int worldX, worldY;

    // solid Area for each object
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    UtilityTool utilityTool = new UtilityTool();


    public void draw(Graphics2D graphics, GamePanel gp) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&  // make sure to only draw tiles around the player
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&  // +/- tilesize so we don't see the black bg
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            graphics.drawImage(image1, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}


