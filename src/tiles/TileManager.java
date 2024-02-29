package tiles;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNumber;

    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[10]; // create an array of 10 tiles (for the different tiles, grass, water, walls etc.)

        // mapTileNumber array to store the infos from .txt maps
        mapTileNumber = new int[gp.maxWorldColumn][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {

            setup(0, "grass", false);
            setup(1, "wall", true);
            setup(2, "water", true);
            setup(3, "dirt", false);
            setup(4, "tree", true);
            setup(5, "sand", false);
            setup(6, "deadtree", true);
            setup(7, "stump", true);

    }

    public void setup(int index, String fileName, boolean collision) {
        UtilityTool utilityTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull
                    (getClass().getResourceAsStream("/tiles/" + fileName + ".png")));
            tile[index].image = utilityTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }


    }

    public void loadMap(String filePathMap) {
        try {
            // input stream to import txt file, buffered reader to read content of txt file
            InputStream inputStream = getClass().getResourceAsStream(filePathMap);
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int column = 0;
            int row = 0;

            // inside of this loop we read in the txt file
            while (column < gp.maxWorldColumn && row < gp.maxWorldRow) {
                String line = bufferedReader.readLine();
                // and here we read in the numbers from the filePathMap
                while (column < gp.maxWorldColumn) {
                    String[] numbers = line.split(" "); // put numbers in array
                    int number = Integer.parseInt(numbers[column]); // convert Strings to int
                    mapTileNumber[column][row] = number;
                    column++;
                }
                if (column == gp.maxWorldColumn) {
                    column = 0;
                    row++;
                }
            }

            bufferedReader.close();

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public void draw(Graphics2D graphics) {

        for (int worldColumn = 0; worldColumn < gp.maxWorldColumn; worldColumn++) {
            for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++){
                int tileNumber = mapTileNumber[worldColumn][worldRow]; // get number on that position (e.g. 0,0)

                int worldX = worldColumn * gp.tileSize;
                int worldY = worldRow * gp.tileSize;
                // if player is at 500x500 world that would mean 0x0 world tile is drawn at -500-500
                // and we need to offset the player because he's always in the middle of the screen
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&  // make sure to only draw tiles around the player
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&  // +/- tilesize so we don't see the black bg
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
                {

                    graphics.drawImage(tile[0].image, screenX, screenY, null); // background tile is 0 (grass)
                    graphics.drawImage(tile[tileNumber].image, screenX, screenY, null);

                }
            }
        }
    }
}
