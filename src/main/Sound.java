package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURLs = new URL[30];

    public Sound() {
        soundURLs[0] = getClass().getResource("/sounds/music.wav");
        soundURLs[1] = getClass().getResource("/sounds/coin.wav");
        soundURLs[2] = getClass().getResource("/sounds/powerup.wav");
        soundURLs[3] = getClass().getResource("/sounds/open.wav");
        soundURLs[4] = getClass().getResource("/sounds/fanfare.wav");
        soundURLs[5] = getClass().getResource("/sounds/badsound.wav");

        soundURLs[6] = getClass().getResource("/sounds/hit.wav");
        soundURLs[7] = getClass().getResource("/sounds/receivedamage.wav");
        soundURLs[8] = getClass().getResource("/sounds/fall.wav");
        soundURLs[9] = getClass().getResource("/sounds/restorehealth.wav");
    }

    public void setFile(int i) {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURLs[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
