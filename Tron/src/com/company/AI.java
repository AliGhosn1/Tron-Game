package com.company;

import java.util.Random;

public class AI {
    public static int lives = 3;
    public static int section = 0;
    public static String direction = "LEFT";
    static int amount = (1260 * 770) / (GamePanel.size * GamePanel.size);
    public static int[] x = new int[amount];
    public static int[] y = new int[amount];
    public static void start(){
        direction = "LEFT";
        x[0] = x[0] + 1160;
        y[0] = y[0] + 440;
    }
    public static void death(){//runs when someone dies
        GamePanel.score += 1000;
        lives--;
        if(lives != 0) {
            section = 0;
            Player1.section = 0;
            for(int i = 0;i < amount;i++){
                x[i] = 0;
                y[i] = 0;
                Player1.x[i] = 0;
                Player1.y[i] = 0;
            }
            start();
            Player1.start();
        }
        else{
            GamePanel.running = false;
        }
    }
    public static void move() {
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
    public static void checkCollisions() {
        for (int i = AI.section; i > 0; i--) {

            if ((x[0] == x[i]) && (y[0] == y[i])) {
                death();
            }
            if ((x[0] == Player1.x[i]) && (y[0] == Player1.y[i])) {
                death();
            }
        }
        if(x[0] < 30) {
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
    public static boolean clear() {//checks to see if the next spot is clear
       int tempx = 0;//used to simulate next movement
       int tempy = 0;
       switch (direction) {
           case "LEFT" -> {
               tempx = x[0] - GamePanel.size;
               tempy = y[0];
           }
           case "RIGHT" -> {
               tempx = x[0] + GamePanel.size;
               tempy = y[0];
           }
            case "UP" -> {
                tempy = y[0] - GamePanel.size;
                tempx = x[0];
            }
            case "DOWN" -> {
                tempy = y[0] + GamePanel.size;
                tempx = x[0];
            }

        }
        for (int i = AI.section; i > 0; i--) {
            if((tempx == x[i]) && (tempy == y[i])) {
                return false;
            }
            else if((tempx == Player1.x[i]) && (tempy == Player1.y[i])) {
                return false;
            }
        }
        if(tempx < 30) {
            return false;
        }
        else if(tempx > 1220) {
            return false;
        }
        else if(tempy < 140) {
            return false;
        }
        else if(tempy > 730) {
            return false;
        }
       return true;
    }
    public static void turn() {
        String oldDirection = direction;
        if (AI.clear()){
            Random r=new Random();
            int randomNumber=r.nextInt(25);//4% chance to turn when not gonna hit
            if(randomNumber == 1){
                if (AI.direction == "UP" || AI.direction == "DOWN"){
                    String[] Fifty={"1", "2"};
                    randomNumber=r.nextInt(Fifty.length);
                    if(Fifty[randomNumber] == "1"){
                        direction = "LEFT";
                        if(!AI.clear())
                            direction = oldDirection;
                    }
                    else{
                        direction = "RIGHT";
                        if(!AI.clear())
                            direction = oldDirection;
                    }
                }
                else{
                    String[] Fifty={"1", "2"};
                    randomNumber=r.nextInt(Fifty.length);
                    if(Fifty[randomNumber] == "1"){
                        direction = "UP";
                        if(!AI.clear())
                            direction = oldDirection;
                    }
                    else{
                        direction = "DOWN";
                        if(!AI.clear())
                            direction = oldDirection;
                    }
                }
            }
        }
        else {//if going to hit something
            Random r = new Random();
            int randomNumber;
            if (AI.direction == "UP" || AI.direction == "DOWN"){
                    String[] Fifty={"1", "2"};
                    randomNumber=r.nextInt(Fifty.length);
                    if(Fifty[randomNumber] == "1"){
                        direction = "LEFT";
                        if(!AI.clear()){
                            direction = "RIGHT";
                        }
                    }
                    else{
                        direction = "RIGHT";
                    }
                }
                else{
                    String[] Fifty={"1", "2"};
                    randomNumber=r.nextInt(Fifty.length);
                    if(Fifty[randomNumber] == "1"){
                        direction = "UP";
                    }
                    else{
                        direction = "DOWN";
                    }
                }
            }
        }
    }

