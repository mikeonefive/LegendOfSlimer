package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Skull extends SuperObject {
    GamePanel gp;

    public Skull(GamePanel gp) {
        this.gp = gp;

        name = "Skull";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/skull.png")));

            utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
