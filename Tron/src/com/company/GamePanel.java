package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.JFrame;

public class GamePanel extends JPanel implements ActionListener {
    Random random;
    public static Timer timer;
    int WIDTH = 1260;
    int HEIGHT = 770;
    public static int size = 10;
    static int time = 125;
    public static boolean running = false;
    boolean titleScreen = true;//used to determine if the game has started
    int blink = 0;//used for flashing press start
    public static int score;//used to save total score
    Image title;//images
    Image main;
    Image space;
    Image blueUp;
    Image blueDown;
    Image blueLeft;
    Image blueRight;
    Image redUp;
    Image redDown;
    Image redLeft;
    Image redRight;
    Image heart;
    Image win;
    Image lose;


    GamePanel() {//Starts the game
        random = new Random();
        title = new ImageIcon("title2.png").getImage();//All Photos
        main = new ImageIcon("Screen1.jpg").getImage();
        space = new ImageIcon("SPACE2.png").getImage();
        blueUp = new ImageIcon("blue up.png").getImage();
        blueDown = new ImageIcon("blue down.png").getImage();
        blueLeft = new ImageIcon("blue left.png").getImage();
        blueRight = new ImageIcon("blue right.png").getImage();
        redUp = new ImageIcon("red up.png").getImage();
        redDown = new ImageIcon("red down.png").getImage();
        redLeft = new ImageIcon("red left.png").getImage();
        redRight = new ImageIcon("red right.png").getImage();
        heart = new ImageIcon("Heart.png").getImage();
        win= new ImageIcon("WINNER.png").getImage();
        lose= new ImageIcon("game over.png").getImage();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));//Window settings
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {//Game start
        timer = new Timer(time, this);
        Player1.start();
        AI.start();
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            Player1.move();
            Player1.checkCollisions();
            AI.move();
            AI.checkCollisions();
            AI.turn();
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE://used for start screen
                    if(titleScreen){
                        running = true;
                        titleScreen = false;
                    }
                case KeyEvent.VK_LEFT://checks user input for direction change
                    if (Player1.direction != "RIGHT") {
                        Player1.direction = "LEFT";
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (Player1.direction != "LEFT") {
                        Player1.direction = "RIGHT";
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (Player1.direction != "DOWN") {
                        Player1.direction = "UP";
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (Player1.direction != "UP") {
                        Player1.direction = "DOWN";
                    }
                    break;
            }
        }
    }
    public void draw(Graphics g) {
        if (titleScreen) {//Title screen
            g.drawImage(main, 0, 0, this);
            if (blink <= 15) {
                g.drawImage(space, 530, 700, this);
            } else if (blink == 28)
                blink = 0;
            blink += 4;
        }
        else if (running) {//Game
            g.setColor(new Color(110, 146, 144));//Makes the tron field
            g.fillRect(0, 0, 1260, 770);
            g.setColor(new Color(0, 0, 36));
            g.fillRect(30, 140, 1200, 600);
            g.fillRect(0, 0, 1260, 110);
            g.setColor(Color.black);
            for (int i = 80; i < 1230; i += 50) {//lines in the field
                g.fillRect(i, 140, 10, 600);
            }
            for (int i = 190; i < 740; i += 50) {
                g.fillRect(30, i, 1200, 10);
            }
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));//used for score and lives
            FontMetrics Ink = getFontMetrics(g.getFont());
            g.drawString("Player: ", 20, 40);
            g.drawString("Enemy: ", 20, 80);
            g.drawString("Score", 980, 60);
            g.drawString(Integer.toString(score), 1075, 60);


            for (int i = 0; i < Player1.lives; i++) {//prints the lives remaining
                g.drawImage(heart, 25 * i + 120, 25, this);
            }
            for (int i = 0; i < AI.lives; i++) {
                g.drawImage(heart, 25 * i + 120, 65, this);
            }
            g.drawImage(title, 400, 0, this);
            for (int i = 0; i < Player1.section; i++) {//prints the characters
                if (i == 0) {
                    if (Player1.direction == "UP")
                        g.drawImage(blueUp, Player1.x[i], Player1.y[i] - 7, this);
                    else if (Player1.direction == "DOWN") {
                        g.drawImage(blueDown, Player1.x[i], Player1.y[i], this);
                    } else if (Player1.direction == "LEFT") {
                        g.drawImage(blueLeft, Player1.x[i] - 7, Player1.y[i], this);
                    } else if (Player1.direction == "RIGHT") {
                        g.drawImage(blueRight, Player1.x[i], Player1.y[i], this);
                    }
                } else {
                    g.setColor(new Color(0, 0, 255));
                    g.fillRect(Player1.x[i], Player1.y[i], size, size);
                }
            }
            for (int i = 0; i < AI.section; i++) {
                if (i == 0) {
                    if (AI.direction == "UP")
                        g.drawImage(redUp, AI.x[i], AI.y[i] - 7, this);
                    else if (AI.direction == "DOWN") {
                        g.drawImage(redDown, AI.x[i], AI.y[i], this);
                    } else if (AI.direction == "LEFT") {
                        g.drawImage(redLeft, AI.x[i] - 7, AI.y[i], this);
                    } else if (AI.direction == "RIGHT") {
                        g.drawImage(redRight, AI.x[i], AI.y[i], this);
                    }
                } else {
                    g.setColor(new Color(255, 0, 0));
                    g.fillRect(AI.x[i], AI.y[i], size, size);
                }
            }

        }
        else {
            if (AI.lives != 0) {//loser screen
                g.drawImage(lose, 0, 0, this);
            }
            else {//winner screen also has a final score
                g.drawImage(win, 0, 0, this);
                g.setColor(Color.white);
                g.setFont(new Font("Ink Free", Font.BOLD, 60));
                FontMetrics Ink = getFontMetrics(g.getFont());
                g.drawString("SCORE:", 20, 700);
                g.drawString(Integer.toString(score), 240, 700);
            }

        }
    }
}
