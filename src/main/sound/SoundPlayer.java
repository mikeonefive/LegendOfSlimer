package main.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {
    private Clip clip;
    // old approach would be to have an array
    // URL[] soundURLs = new URL[30];
    private final Map<SoundEffect, URL> soundUrlsMap = new HashMap<>();

    public SoundPlayer() {
        soundUrlsMap.put(SoundEffect.MUSIC, getClass().getResource("/sounds/music.wav"));
        soundUrlsMap.put(SoundEffect.COIN, getClass().getResource("/sounds/coin.wav"));
        soundUrlsMap.put(SoundEffect.POWERUP, getClass().getResource("/sounds/powerup.wav"));
        soundUrlsMap.put(SoundEffect.OPEN, getClass().getResource("/sounds/open.wav"));
        soundUrlsMap.put(SoundEffect.FANFARE, getClass().getResource("/sounds/fanfare.wav"));
        soundUrlsMap.put(SoundEffect.BAD_SOUND,getClass().getResource("/sounds/badsound.wav"));

        soundUrlsMap.put(SoundEffect.HIT, getClass().getResource("/sounds/hit.wav"));
        soundUrlsMap.put(SoundEffect.RECEIVE_DAMAGE, getClass().getResource("/sounds/receivedamage.wav"));
        soundUrlsMap.put(SoundEffect.FALL, getClass().getResource("/sounds/fall.wav"));
        soundUrlsMap.put(SoundEffect.RESTORE_HEALTH, getClass().getResource("/sounds/restorehealth.wav"));
    }

    public void setFile(SoundEffect soundName) {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrlsMap.get(soundName));
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
