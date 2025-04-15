package objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class SpeechBubble extends SuperObject {

    public SpeechBubble() {

        name = "Speech Bubble";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/v0/speechbubble.png")));
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}