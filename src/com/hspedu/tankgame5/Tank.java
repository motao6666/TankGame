package com.hspedu.tankgame5;

import java.util.Vector;

public class Tank {
    boolean isLive = true;
    private int x;
    private int y;
    private int direct;
    private int speed = 1;
    //
    //定义一个Shot对象，表示一个射击
    Shot shot = null;
    //可以发射多颗子弹
    Vector<Shot> shots = new Vector<>();
    public void shotEnemyTank() {
        //Shot shot = null;
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
        //把新建的shot放到shots集合
        shots.add(shot);
        //启动线程
        //for (int i = 0; i < shots.size(); i++) {
            new Thread(shot).start();
        //}

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
