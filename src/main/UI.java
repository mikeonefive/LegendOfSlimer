package main;

import objects.PlayerHeart;
import objects.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static constants.Constants.*;

public class UI {

    GamePanel gp;
    Graphics2D graphics;
    Font pixelFont;
    BufferedImage heartFull, heartHalf, heartBlank;

    public boolean showMessage = false;
    public String message = "";
    int messageTimer = 0;
    public boolean gameOver = false;

    public String currentDialogueLine = "";
    public int commandNumber = 0;


    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream fontFile = getClass().getResourceAsStream("/fonts/PressStart2P.ttf");
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        // CREATE HEART OBJECT
        SuperObject heart = new PlayerHeart(gp);
        heartFull = heart.image1;
        heartHalf = heart.image2;
        heartBlank = heart.image3;
    }

    public void setMessage(String text) {
        message = text;
        showMessage = true;
    }

    public void draw (Graphics2D graphics) {
        this.graphics = graphics;
        // graphics.setFont(arial40);
        graphics.setColor(Color.WHITE);

        // TITLE SCREEN STATE
        if (gp.gameState == TITLE_SCREEN) {
            drawTitleScreen();
        }

        // PLAY GAME STATE
        if (gp.gameState == PLAY_GAME) {
            drawPlayerHealth();
        }

        // PAUSE GAME STATE
        if (gp.gameState == PAUSE_GAME) {
            drawPauseScreen();
            drawPlayerHealth();
        }

        // DIALOGUE STATE
        if (gp.gameState == DIALOGUE) {
            drawPlayerHealth();
            drawDialogueScreen();
        }
    }

    public void drawPlayerHealth() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // DRAW MAX HEALTH
        while (i < gp.player.maxHealth / 2) {                // 2 lives = 1 heart
            graphics.drawImage(heartBlank, x, y, 30, 30, null);
            i++;
            x += (int) (gp.tileSize / 1.5);
        }

        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;
        while (i < gp.player.health) {                // 2 lives = 1 heart
            graphics.drawImage(heartHalf, x, y, 30, 30, null);
            i++;
            if (i < gp.player.health) {
                graphics.drawImage(heartFull, x, y, 30, 30, null);
            }
            i++;
            x += (int) (gp.tileSize / 1.5);
        }
    }

    public void drawTitleScreen() {
        graphics.setFont(pixelFont.deriveFont(Font.PLAIN, 45));
        String text = "The Legend of Slimer";
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 3;

        // DROP SHADOW
        graphics.setColor(Color.GREEN);
        graphics.drawString(text, x + 3, y + 3);

        // MAIN COLOR
        graphics.setColor(Color.WHITE);
        graphics.drawString(text, x, y);

        // SLIMER IMAGE
        x = gp.screenWidth / 2 - (int)(gp.tileSize * 1.5) / 2; // center
        y += gp.tileSize * 2;
        graphics.drawImage(gp.player.down2, x, y, (int)(gp.tileSize * 1.5), (int)(gp.tileSize * 1.5), null);

        // MENU
        graphics.setFont(pixelFont.deriveFont(Font.PLAIN, 30));
        text = "New Game";
        x = getXForCenteredText(text);
        y += gp.tileSize * 4;
        graphics.drawString(text, x, y);
        if (commandNumber == 0) {
            graphics.drawString(">", x - gp.tileSize, y);
        }

        text = "Load Game";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        graphics.drawString(text, x, y);
        if (commandNumber == 1) {
            graphics.drawString(">", x - gp.tileSize, y);
        }

        text = "Quit";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        graphics.drawString(text, x, y);
        if (commandNumber == 2) {
            graphics.drawString(">", x - gp.tileSize, y);
        }
    }


    public void drawDialogueScreen() {
        // WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int dialogueBoxWidth = gp.screenWidth - (gp.tileSize * 4);
        int dialogueBoxHeight = gp.tileSize * 4;

        drawDialogueWindow(x, y, dialogueBoxWidth, dialogueBoxHeight);

        // DIALOGUE LINE
        graphics.setFont(pixelFont.deriveFont(Font.PLAIN, 17));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogueLine.split("\n")) {
            graphics.drawString(line, x, y);
            y += 40;
        }
    }


    public void drawDialogueWindow(int x, int y, int width, int height) {
        Color color = new Color(255, 255, 255, 200);
        graphics.setColor(color);
        graphics.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(0, 0, 0);
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(4));
        graphics.drawRoundRect(x + 4, y + 4, width - 10, height - 10, 25, 25);
    }


    public void drawPauseScreen() {
        Color shadowColor = Color.BLACK;
        Color textColor = Color.WHITE;
        int shadowOffset = 3;

        String message = "PAUSED";

        graphics.setFont(pixelFont.deriveFont(Font.PLAIN, 30));

        int x = getXForCenteredText(message);
        int y = gp.screenHeight / 2;

        graphics.setColor(shadowColor);
        graphics.drawString(message, x + shadowOffset, y + shadowOffset);
        graphics.setColor(textColor);
        graphics.drawString(message, x, y);
    }


    public int getXForCenteredText (String text) {
        int messageLength = (int)graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
        int x = gp.screenWidth / 2 - messageLength / 2;
        return x;
    }
}
