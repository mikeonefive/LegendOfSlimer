package main;

import objects.ChestKey;
import objects.Key;
import objects.SpeechBubble;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Font arial20, arial40;

    BufferedImage keySymbol;
    BufferedImage chestKeySymbol;
    BufferedImage speechBubble;

    public boolean showMessage = false;
    public String message = "";
    int messageTimer = 0;
    public boolean gameOver = false;

    double gameTime;
    DecimalFormat timeFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;
        arial20 = new Font("Arial", Font.BOLD, 20);
        arial40 = new Font("Arial", Font.BOLD, 40);

        Key key = new Key(gp); // just create new key instance and access its image
        keySymbol = key.image;

        ChestKey chestKey = new ChestKey(gp);
        chestKeySymbol = chestKey.image;

        SpeechBubble speechBubble1 = new SpeechBubble();
        speechBubble = speechBubble1.image;
    }

    public void setMessage(String text) {
        message = text;
        showMessage = true;
    }

    public void draw (Graphics2D g2) {

        // DRAW GAME OVER SCREEN
        if (gameOver) {

            String text;
            int textLength;
            int x, y;

            g2.setFont(arial20);
            // draw speech bubble and let player say something
            g2.setColor(Color.black);
            text = "I found me some treasure!";
            g2.drawImage(speechBubble, gp.player.screenX / 2, gp.player.screenY / 2,
                    (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() + 25, gp.tileSize * 2, null);
            g2.setFont(g2.getFont().deriveFont(16F)); // casting to float with F
            g2.drawString(text, gp.player.screenX - gp.tileSize * 3, gp.player.screenY - 90);

            // End message
            g2.setFont(arial40);
            text = "Game Over";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // returns length of the text
            x = (gp.screenWidth / 2) - (textLength / 2);
            y = gp.screenHeight / 2 + gp.tileSize;

            g2.setColor(Color.black); // drop shadow text
            g2.drawString(text, x + 3, y + 3);

            g2.setColor(Color.white); // original text
            g2.drawString(text, x, y);

            g2.setFont(arial20);
            text = "Your time: " + timeFormat.format(gameTime) + " seconds";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = (gp.screenWidth / 2) - (textLength / 2);
            y = (gp.screenHeight / 2) + (gp.tileSize * 2);

            g2.setColor(Color.black); // drop shadow text
            g2.drawString(text, x + 3, y + 3);

            g2.setColor(Color.white); // original text
            g2.drawString(text, x, y);


            gp.gameThread = null; // stop the game thread

        } else {

            g2.setFont(arial20);

            g2.drawImage(keySymbol, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawImage(chestKeySymbol, gp.tileSize / 2, gp.tileSize / 2 + gp.tileSize, gp.tileSize, gp.tileSize, null);

            g2.setColor(Color.black); // this is the drop shadow of the text
            int symbol1X = 76, symbol1Y = 56;
            g2.drawString("x " + gp.player.numberOfKeysPickedUp, symbol1X, symbol1Y);
            g2.drawString("x " + Boolean.compare(gp.player.hasChestKey, false), symbol1X, symbol1Y + gp.tileSize);

            g2.setColor(Color.white);
            g2.drawString("x " + gp.player.numberOfKeysPickedUp, symbol1X - 2, symbol1Y - 1);
            g2.drawString("x " + Boolean.compare(gp.player.hasChestKey, false), symbol1X - 2, symbol1Y - 1 + gp.tileSize);

            // TIME
            gameTime += (double) 1/60;  // draw method gets called 60 time/second, so we add 1/60 in every iteration
            g2.setColor(Color.black);
            g2.drawString(timeFormat.format(gameTime), gp.tileSize * 14, symbol1Y); // drop shadow
            g2.setColor(Color.white);
            g2.drawString(timeFormat.format(gameTime), gp.tileSize * 14 - 2, symbol1Y - 1);

            // draw message
            if (showMessage) {
                g2.setColor(Color.black);
                g2.drawImage(speechBubble, gp.player.screenX / 2 - 10, gp.player.screenY / 2, message.length() * 15, gp.tileSize * 2, null);
                g2.setFont(g2.getFont().deriveFont(16F)); // casting to float with F
                g2.drawString(message, gp.player.screenX - gp.tileSize * 3, gp.player.screenY - 90);

                messageTimer++; // every time draw is called we increase that variable
                if (messageTimer > 120) { // 120 (60 fps) = text disappears after 2 secs
                    messageTimer = 0;
                    showMessage = false;
                }
            }
        }
    }
}
