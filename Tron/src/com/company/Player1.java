package com.company;
class Player1 {
    public static int lives = 3;
    public static int section = 0;//parts to him right now
    public static String direction = "RIGHT";//which way hes going
    static int amount = (1260 * 770) / (GamePanel.size * GamePanel.size);
    public static int[] x = new int[amount];//x grid
    public static int[] y = new int[amount];//y grid
    public static void death(){//everytime someone dies this is run
        GamePanel.score -= 1000;
        lives--;
        if(lives != 0) {
            section = 0;
            AI.section = 0;
            for(int i = 0;i < amount;i++){
                x[i] = 0;
                y[i] = 0;
                AI.x[i] = 0;
                AI.y[i] = 0;
            }
            start();
            AI.start();
        }
        else{
            GamePanel.running = false;
        }
    }
    public static void start(){
        direction = "RIGHT";
        x[0] = 100;
        y[0] = 440;
    }
    public static void move() {//movement
        GamePanel.score += 10;
        for (int i = section; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case "LEFT" -> {
                x[0] = x[0] - GamePanel.size;
                section += 1;
            }
            case "RIGHT" -> {
                x[0] = x[0] + GamePanel.size;
                section += 1;
            }
            case "UP" -> {
                y[0] = y[0] - GamePanel.size;
                section += 1;
            }
            case "DOWN" -> {
                y[0] = y[0] + GamePanel.size;
                section += 1;
            }
        }
    }

    public static void checkCollisions() {//checks to see if something is hit
        for(int i = Player1.section; i > 0; i--) {//itself
            if((x[0] == x[i]) && (y[0] == y[i])) {
                death();
            }
            else if((x[0] == AI.x[i]) && (y[0] == AI.y[i])) {//ai
                death();
            }
        }
        if(x[0] < 30) {//borders
            death();
        }
        else if(x[0] > 1220) {
            death();
        }
        else if(y[0] < 140) {
            death();
        }
        else if(y[0] > 730) {
            death();
        }
    }
}



