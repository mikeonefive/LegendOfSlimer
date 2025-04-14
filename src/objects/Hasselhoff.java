package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Hasselhoff extends SuperObject {
    GamePanel gp;

    public Hasselhoff(GamePanel gp) {
        this.gp = gp;
        name = "Hasselhoff";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/v0/hasselhoff.png")));
            utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}