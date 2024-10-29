package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Plant extends SuperObject {
    GamePanel gp;

    public Plant(GamePanel gp) {
        this.gp = gp;
        name = "Plant";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/plant.png")));
            utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}