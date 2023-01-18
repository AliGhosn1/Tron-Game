//Tron.java
//Ali Ghosn
//Simple tron light cycles game
package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        new GameFrame();
    }
}
class GameFrame extends JFrame {
    GameFrame() {
        setTitle("Tron Light");
        add(new GamePanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
