package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Chest extends SuperObject {

    GamePanel gp;

    public Chest(GamePanel gp) {

        this.gp = gp;
        name = "Chest";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/v0/chest.png")));
            utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            System.err.println("An error occured: " + e.getMessage());
        }
        isColliding = true;
    }
}