package com.hspedu.tabkgame3;

import javax.swing.*;
import java.awt.*;

public class Tank {
    Shot shot = null;
    private int x;
    private int y;
    private int direct;
    private int speed = 1;

    public void shotEnemyTank() {
        //创建shot对象，根据tank对象位置和方向创建shot
        switch (getDirect()) {
            case 0:
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1:
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2:
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3:
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }
        new Thread(shot).start();
    }

    public void moveUp() {
        if(getY()>0){
            y -= speed;
        }

    }

    public void moveRight() {
        if(getX()+60<1000){
            x += speed;
        }

    }

    public void moveDown() {
        if(getY()+60<750){
            y += speed;
        }

    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void moveLeft() {
        if(getX()>0){
            x -= speed;
        }

    }


    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
