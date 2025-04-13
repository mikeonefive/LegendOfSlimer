package main;

import inputs.GamepadInput;

import java.awt.*;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Graphics2D graphics;
    Font arial20, arial40;

    public boolean showMessage = false;
    public String message = "";
    int messageTimer = 0;
    public boolean gameOver = false;

    public String currentDialogueLine = "";


    public UI(GamePanel gp) {
        this.gp = gp;
        arial20 = new Font("Arial", Font.BOLD, 20);
        arial40 = new Font("Arial", Font.BOLD, 40);
    }

    public void setMessage(String text) {
        message = text;
        showMessage = true;
    }

    public void draw (Graphics2D graphics) {
        this.graphics = graphics;
        graphics.setFont(arial40);
        graphics.setColor(Color.WHITE);

        // PLAY GAME STATE
        if (gp.gameState == gp.playGame) {
            // draw playGame graphics
        }

        // PAUSE GAME STATE
        if (gp.gameState == gp.pauseGame) {
            drawPauseScreen();

        }

        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();

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
        graphics.setFont(arial20);
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
