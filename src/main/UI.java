package main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import static constants.Constants.*;

public class UI {

    GamePanel gp;
    Graphics2D graphics;
    Font arial20, arial40;
    Font pixelFont;

    public boolean showMessage = false;
    public String message = "";
    int messageTimer = 0;
    public boolean gameOver = false;

    public String currentDialogueLine = "";
    public int commandNumber = 0;


    public UI(GamePanel gp) {
        this.gp = gp;
        // arial20 = new Font("Arial", Font.BOLD, 20);
        // arial40 = new Font("Arial", Font.BOLD, 40);

        try {
            InputStream fontFile = getClass().getResourceAsStream("/fonts/PressStart2P.ttf");
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

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
            // draw playGame graphics
        }

        // PAUSE GAME STATE
        if (gp.gameState == PAUSE_GAME) {
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if (gp.gameState == DIALOGUE) {
            drawDialogueScreen();
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
