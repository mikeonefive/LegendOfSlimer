package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Key extends SuperObject {

    GamePanel gp;

    public Key(GamePanel gp) {
        this.gp = gp;
        this.name = "Key";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));
            utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}