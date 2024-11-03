package main;

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

    double gameTime;
    DecimalFormat timeFormat = new DecimalFormat("#0.00");

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

        if (gp.gameState == gp.playGame) {
            // draw playGame graphics
        }

        if (gp.gameState == gp.pauseGame) {
            drawPauseScreen();
        }
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
