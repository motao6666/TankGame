package com.hspedu.tabkgame3;

public class Bomb {
    int x,y;
    int life = 9;
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.y = y;
        this.x = x;
    }
    //生命值减少
    public void lifeDown(){
        if(life>0){
            life--;
        }else {
            isLive = false;
        }
    }
}
