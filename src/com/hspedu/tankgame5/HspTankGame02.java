package com.hspedu.tankgame5;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class HspTankGame02 extends JFrame {
    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        new HspTankGame02();
    }

    public HspTankGame02() {
        System.out.println("请输入选择 1：新游戏 2 ：继续上局");
        String key = scanner.next();
        mp = new MyPanel(key);
        //把mp放入到Thread，并启动
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//把面板（游戏绘图区域）
        this.setSize(1350, 750);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //在JFrame中增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}
