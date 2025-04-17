package main;

import entities.Player;
import inputs.GamepadInput;
import inputs.KeyboardInput;
import objects.AssetManager;
import entities.Entity;
import tiles.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

import static constants.Constants.*;

// game panel = game screen
public class GamePanel extends JPanel implements Runnable { // GamePanel is now a subclass of JPanel inherits all the functions
    // SCREEN & TILE SETTINGS
    final int originalTileSize = 25;                        // 16 would be the original size for old pixel games
    final int scale = 2;

    public final int tileSize = originalTileSize * scale;   // 50x50
    public final int maxScreenColumn = 16;                  // was 16!
    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenColumn;   // 50 (tilesize) * 20 = 1000
    public final int screenHeight = tileSize * maxScreenRow;     // 600

    // WORLD SETTINGS
    public final int maxWorldColumn = 50;
    public final int maxWorldRow = 50;

    // FRAMES/SECOND
    int FPS = 60;

    //SYSTEM
    TileManager tileManager = new TileManager(this);
    public EventHandler eventHandler = new EventHandler(this);

    //KEY HANDLER, create instance of our keyboardInput class
    public KeyboardInput keyboardInput = new KeyboardInput(this);
    public GamepadInput gamepadInput = new GamepadInput(this);

    //SOUND
    Sound music = new Sound();
    Sound soundEffect = new Sound();

    //COLLISIONCHECKER instance that receives this gamepanel
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetManager assetManager = new AssetManager(this);

    public UI ui = new UI(this);

    //time (start a thread and keep it running)
    Thread gameThread;

    //PLAYER ENTITY & OBJECTS
    //create instance of player class so we can use gamepanel and keyboard input from player class
    public Player player = new Player(this, keyboardInput, gamepadInput);

    //objects array contains the different objects like keys, chests etc.
    public Entity[] objects = new Entity[15];
    public Entity[] npcs = new Entity[10];
    public Entity[] enemies = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    //GAME STATES
    public int gameState;


    public GamePanel() {
        // set size of game panel
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyboardInput);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetManager.setObjects();
        assetManager.setNpc();
        assetManager.setEnemies();
        // playMusic(0); // 0 is the index of our background music (see Sound Class)
        gameState = TITLE_SCREEN;
    }

    public void startGameThread() {
        gameThread = new Thread(this);      // this means this class, we pass GamePanel to the thread
        gameThread.start();                 // run method is automatically called when we start the thread
    }

    // the run method will contain the game loop and this will run as long as thread is active (!= null)
    @Override
    public void run() {
        double drawInterval = 1000.0 / FPS;    // divide 1 sec (1 billion nanosecs) by the FPS rate
        double nextDrawTime = System.currentTimeMillis() + drawInterval;

        while (gameThread != null) {
            // 1 - UPDATE update info (e.g. char position)
            update();

            // 2 - DRAW draw screen with updated info
            repaint();  // for some reason this is how we call the paintComponent method

            try {
                double remainingTime = nextDrawTime - System.currentTimeMillis();

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);      // sleep pauses the game loop won't do anything until next draw
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {

        gamepadInput.handleGamepadInput();

        if (gameState == PLAY_GAME)
            player.update();

        // NPCs
        for (entities.Entity npc : npcs) {
            if (npc != null)
                npc.update();
        }

        // enemies
        for (entities.Entity enemy : enemies) {
            if (enemy != null)
                enemy.update();
        }

        if (gameState == PAUSE_GAME) {
            // do the updates for paused game
        }
    }

    public void paintComponent(Graphics g) {    // paintComponent is a method that's already there in Java
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D)g;

        //DEBUG pt1 how long it takes to draw stuff
        long startTimeDrawing = 0;
        if (KeyboardInput.checkDrawingTime) {
            startTimeDrawing = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == TITLE_SCREEN) {
            ui.draw(graphics);

        // OTHER GAME STATES (PLAY)
        } else {
            //TILES
            tileManager.draw(graphics);

            // ADD ALL OBJECTS AND ENTITIES TO 1 LIST
            entityList.add(player);

            for (Entity npc : npcs) {
                if (npc != null) {
                    entityList.add(npc);
                }
            }

            for (Entity enemy : enemies) {
                if (enemy != null) {
                    entityList.add(enemy);
                }
            }

            for (Entity object : objects) {
                if (object != null) {
                    entityList.add(object);
                }
            }

            // SORT LIST so we know what has to be drawn on top of each other
            entityList.sort(new Comparator<Entity>() {
                @Override
                public int compare(Entity entity1, Entity entity2) {
                    int result = Integer.compare(entity1.worldY, entity2.worldY);
                    return result;
                }
            });

            //DRAW ENTITIES
            for (Entity entity : entityList) {
                entity.draw(graphics);
            }

            //CLEAR ENTITY LIST
            for (int i = 0; i < entityList.size(); i++) {
                entityList.remove(i);
            }

            //DRAW UI
            ui.draw(graphics);
        }

        //DEBUG pt2
        if (KeyboardInput.checkDrawingTime) {
            long endTimeDrawing = System.nanoTime();
            double timePassed = endTimeDrawing - startTimeDrawing;
            timePassed = timePassed / 1_000_000_000; // convert to seconds
            System.out.println("Drawing time: " + timePassed);
            graphics.setColor(Color.WHITE);
            graphics.drawString("Drawing time: " + timePassed, 10, 400);
        }
        graphics.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }


    public void stopMusic() {
        music.stop();
    }


    public void playSoundEffect(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }
}