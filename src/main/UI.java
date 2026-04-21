package main;

import inputs.KeyboardInput;
import objects.PlayerHeart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static constants.Constants.*;

public class UI {

    GamePanel gp;
    Graphics2D graphics;
    Font pixelFont;
    BufferedImage heartFull, heartHalf, heartBlank;

    public boolean showMessage = false;
//    public String message = "";
//    int messageTimer = 0;
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<Integer> messageTimer = new ArrayList<>();
    public boolean gameOver = false;

    public String currentDialogueLine = "";
    public int commandNumber = 0;

    int titleFontSize = 38;
    int menuFontSize = 27;
    int pauseFontSize = 30;
    int dialogueFontSize = 15;


    public UI(GamePanel gp) {
        this.gp = gp;

        this.pixelFont = UtilityTool.loadPixelFontFromFile();

        // CREATE HEART OBJECT FOR PLAYER HEALTH
        PlayerHeart heart = new PlayerHeart(gp);
        heartFull = heart.image1;
        heartHalf = heart.image2;
        heartBlank = heart.image3;
    }

    public void addMessage(String text) {
        messages.add(text);
        messageTimer.add(0);
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
            this.drawMessages();
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

        // CHARACTER STATS
        if (gp.gameState == SHOW_CHARACTER_STATS) {
            drawCharacterStatsScreen();
        }
    }

    private void drawCharacterStatsScreen() {
        // CREATE FRAME
        final int frameX = 2 * gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = 5 * gp.tileSize;
        final int frameHeight = 7 * gp.tileSize;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SETTING FONT ATTRIBUTES
        graphics.setColor(Color.BLACK);
        graphics.setFont(pixelFont.deriveFont(Font.PLAIN, dialogueFontSize));

        int textX = frameX + gp.tileSize / 2;
        int textY = frameY + gp.tileSize;
        final int lineHeight = dialogueFontSize + 10;

        // STATS
        List<String> statNames = List.of(
                "Level",
                "Health",
                "Strength",
                "Dexterity",
                "Attack",
                "Defense",
                "Experience",
                "Next Level",
                "Coins",
                "Weapon",
                "Shield");

        for (int i = 0; i < statNames.size(); i++) {
            graphics.drawString(statNames.get(i), textX, textY + i * lineHeight);
        }
        // VAlUES right-aligned
        int tailX = (frameX + frameWidth) - 20;
        List<String> values = List.of(
                String.valueOf(gp.player.level),
                String.valueOf(gp.player.health) + "/" + gp.player.maxHealth,
                String.valueOf(gp.player.strength),
                String.valueOf(gp.player.dexterity),
                String.valueOf(gp.player.attack),
                String.valueOf(gp.player.defense),
                String.valueOf(gp.player.experience),
                String.valueOf(gp.player.nextLevelExperience),
                String.valueOf(gp.player.coins));

        for (int i = 0; i < values.size(); i++) {
            textX = getXforRightAlignedText(values.get(i), tailX);
            graphics.drawString(values.get(i), textX, textY + i * lineHeight);
        }

        graphics.drawImage(gp.player.currentWeapon.down1, tailX - 60, 310, 64, 15, null);
        graphics.drawImage(gp.player.currentShield.down1, tailX - 20, 325, 24, 31, null);
    }

    private int getXforRightAlignedText(String text, int tailX) {
        int length = (int)graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
        int x = tailX - length;
        return x;
    }

    private void drawPlayerHealth() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // DRAW MAX HEALTH
        while (i < gp.player.maxHealth / 2) {                // 2 healthpoints = 1 heart, player max health = 6
            graphics.drawImage(heartBlank, x, y, 30, 30, null);
            i++;
            x += (int) (gp.tileSize / 1.5);
        }

        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;
        while (i < gp.player.health) {
            graphics.drawImage(heartHalf, x, y, 30, 30, null);
            i++;
            if (i < gp.player.health) {
                graphics.drawImage(heartFull, x, y, 30, 30, null);
            }
            i++;
            x += (int) (gp.tileSize / 1.5);
        }
    }

    private void drawMessages() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        graphics.setFont(graphics.getFont().deriveFont(pixelFont.BOLD, 32));

        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i) != null) {

                graphics.setColor(Color.black);
                graphics.drawString(messages.get(i), messageX + 2, messageY + 2);
                graphics.setColor(Color.white);
                graphics.drawString(messages.get(i), messageX, messageY);

                int counter = messageTimer.get(i) + 1;
                messageTimer.set(i, counter);
                messageY += 50;

                if (messageTimer.get(i) > 180) {
                    messages.remove(i);
                    messageTimer.remove(i);
                }
            }
        }
    }

    private void drawTitleScreen() {
        graphics.setFont(pixelFont.deriveFont(Font.PLAIN, titleFontSize));
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
        graphics.setFont(pixelFont.deriveFont(Font.PLAIN, menuFontSize));
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


    private void drawDialogueScreen() {
        // WINDOW
        int x = (int)(gp.tileSize * 1.5);
        int y = gp.tileSize / 2;
        int dialogueBoxWidth = gp.screenWidth - (gp.tileSize * 3);
        int dialogueBoxHeight = gp.tileSize * 4;

        drawSubWindow(x, y, dialogueBoxWidth, dialogueBoxHeight);

        // DIALOGUE LINE
        graphics.setFont(pixelFont.deriveFont(Font.PLAIN, dialogueFontSize));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogueLine.split("\n")) {
            graphics.drawString(line, x, y);
            y += 40;
        }
    }


    private void drawSubWindow(int x, int y, int width, int height) {
        Color color = new Color(255, 255, 255, 200);
        graphics.setColor(color);
        graphics.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(0, 0, 0);
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(4));
        graphics.drawRoundRect(x + 4, y + 4, width - 10, height - 10, 25, 25);
    }


    private void drawPauseScreen() {
        Color shadowColor = Color.BLACK;
        Color textColor = Color.WHITE;
        int shadowOffset = 3;

        String message = "PAUSED";

        graphics.setFont(pixelFont.deriveFont(Font.PLAIN, pauseFontSize));

        int x = getXForCenteredText(message);
        int y = gp.screenHeight / 2;

        graphics.setColor(shadowColor);
        graphics.drawString(message, x + shadowOffset, y + shadowOffset);
        graphics.setColor(textColor);
        graphics.drawString(message, x, y);
    }


    private int getXForCenteredText (String text) {
        int messageLength = (int)graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
        int x = gp.screenWidth / 2 - messageLength / 2;
        return x;
    }

    public void drawDebug(Graphics2D g, double timePassed) {
        if (!gp.keyboardInput.showDebugText) {
            return;
        }

        g.setFont(pixelFont.deriveFont(Font.PLAIN, this.dialogueFontSize));
        g.setColor(Color.WHITE);

        g.drawString("drawing time: " + timePassed, 10, 520);
    }

    public void drawDebug(Graphics2D g) {

        if (!KeyboardInput.showDebugText) return;

        g.setFont(pixelFont.deriveFont(Font.PLAIN, this.dialogueFontSize));
        g.setColor(Color.YELLOW);

        int x = 10;
        int y = 400;
        int lineHeight = 30;

        g.drawString("player worldX " + gp.player.worldX, x, y);
        y += lineHeight;
        g.drawString("player worldY " + gp.player.worldY, x, y);
        y += lineHeight;
        g.drawString("col " + (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize, x, y);
        y += lineHeight;
        g.drawString("row " + (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize, x, y);
    }
}
