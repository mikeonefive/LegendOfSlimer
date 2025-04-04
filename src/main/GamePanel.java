package main;

import entity.Entity;
import entity.Player;
import inputs.GamepadInput;
import inputs.KeyboardInput;
import objects.AssetManager;
import objects.SuperObject;
import tiles.TileManager;

import javax.swing.*;
import java.awt.*;

// game panel = game screen
public class GamePanel extends JPanel implements Runnable {  // GamePanel is now a subclass of JPanel inherits all the functions
    // SCREEN & TILE SETTINGS
    final int originalTileSize = 25;                // 16 would be the original size for old pixel games
    final int scale = 2;

    public final int tileSize = originalTileSize * scale;  // 50x50
    public final int maxScreenColumn = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenColumn;   // 768px
    public final int screenHeight = tileSize * maxScreenRow;     // 576px

    // WORLD SETTINGS
    public final int maxWorldColumn = 50;
    public final int maxWorldRow = 50;

    // FRAMES/SECOND
    int FPS = 60;

    //SYSTEM
    TileManager tileManager = new TileManager(this);

    //KEY HANDLER, create instance of our keyboardInput class
    KeyboardInput keyboardInput = new KeyboardInput(this);
    GamepadInput gamepadInput = new GamepadInput(this);

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
    public SuperObject[] objects = new SuperObject[15];
    public Entity[] npc = new Entity[10];

    //GAME STATES
    public int gameState;
    public final int playGame = 1;
    public final int pauseGame = 2;

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
        // playMusic(0); // 0 is the index of our background music (see Sound Class)
        gameState = playGame;
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
        if (gameState == playGame)
            player.update();

        // NPCs
        for (Entity entity : npc) {
            if (entity != null)
                entity.update();
        }

        if (gameState == pauseGame) {
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


        //TILES
        tileManager.draw(graphics);

        //OBJECTS check if any objects are in the array and draw them if not null
        for (SuperObject object : objects) {
            if (object != null) {
                object.draw(graphics, this);
            }
        }

        //NPC
        for (Entity entity : npc) {
            if (entity != null) {
                entity.draw(graphics);
            }
        }

        //PLAYER
        player.draw(graphics);

        //UI
        ui.draw(graphics);

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