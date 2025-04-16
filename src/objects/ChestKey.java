package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ChestKey extends SuperObject {
    GamePanel gp;

    public ChestKey(GamePanel gp) {
        this.gp = gp;
        name = "ChestKey";

        try {
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/v0/chestkey.png")));
            utilityTool.scaleImage(image1, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}