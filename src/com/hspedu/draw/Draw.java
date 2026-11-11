package com.hspedu.draw;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings({"all"})
public class Draw extends JFrame {//JFrame窗口
    //面板
    private MyPannel mp = null;

    public static void main(String[] args) throws Exception {
        new Draw();

    }

    public Draw() throws InterruptedException {
        //初始化面板
        MyPannel mp = new MyPannel();
        //Thread.sleep(1000);
        //面板放入画框
        this.add(mp);
        //窗口大小
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);//可以显示

    }

}

class MyPannel extends JPanel {
    //MyPannel对象是一个画板
    //Graphics g 把g理解成一只画笔
    @Override
    public void paint(Graphics g) {//绘图方法
        super.paint(g);
        System.out.println("paint方法被调用~~~");
        g.drawOval(10, 10, 100, 100);
    }
}