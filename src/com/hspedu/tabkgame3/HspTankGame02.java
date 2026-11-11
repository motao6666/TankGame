package com.hspedu.tabkgame3;

import javax.swing.*;

public class HspTankGame02 extends JFrame {
    MyPanel mp = null;
    public static void main(String[] args) {
        new HspTankGame02();
    }

    public HspTankGame02() {
         mp = new MyPanel();
         //把mp放入到Thread，并启动
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//把面板（游戏绘图区域）
         this.setSize(1000,750);
         this.addKeyListener(mp);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.setVisible(true);
    }
}
