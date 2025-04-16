package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class PlayerHeart extends SuperObject {
    GamePanel gp;

    public PlayerHeart(GamePanel gp) {
        this.gp = gp;
        this.name = "PlayerHealth";

        try {
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_full.png")));
            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_half.png")));
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_blank.png")));
            image1 = utilityTool.scaleImage(image1, gp.tileSize, gp.tileSize);
            image2 = utilityTool.scaleImage(image2, gp.tileSize, gp.tileSize);
            image3 = utilityTool.scaleImage(image3, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
