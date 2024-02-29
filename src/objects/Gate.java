package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Gate extends SuperObject {

    GamePanel gp;

    public Gate(GamePanel gp) {
        this.gp = gp;

        name = "Gate";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/gate.png")));

            utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            System.err.println("An error occured: " + e.getMessage());
        }
        isColliding = true;
    }
}
