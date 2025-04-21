package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // create window and set properties
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Bestest 2D adventure ever!: The Legend of Slimer");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();                      // to see the added JPanel/gamePanel

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();              // puts objects in place
        gamePanel.startGameThread();
    } 
}
