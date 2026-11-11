package com.hspedu.event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 */
public class BallMove extends JFrame{
    /**
     * 在这里声明这个成员引用变量
     * 是为了扩大它的作用域
     * 让本类中所有方法都能访问到这个面板对象。
     */
    Mypanel mp = null;
    public static void main(String[] args) {
        BallMove ballMove = new BallMove();
    }
    //构造器
    public BallMove(){
        mp = new Mypanel();
        this.add(mp);
        this.setSize(400,300);
        //窗口JFrame 对象可以监听键盘事件，监听到面板发生的键盘事件
        this.addKeyListener(mp);//让main静态方法知道要监听哪儿个对象
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
//画板，画出小球
//KeyListener监听器，监听键盘事件
class Mypanel extends JPanel implements KeyListener {
    int x = 10;
    int y = 10;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillOval(x,y,20,20);//默认黑色

    }
    //有字符输出时，该方法被触发
    @Override
    public void keyTyped(KeyEvent e) {

    }
    //当某个键按下，该方法被触发
    @Override
    public void keyPressed(KeyEvent e) {
        //在java中，会给每一个键，分配一个值（int）
        if(e.getKeyCode()==KeyEvent.VK_DOWN){
            y++;
        }else if(e.getKeyCode()==KeyEvent.VK_UP){
            y--;
        } else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
            x--;
        } else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
            x++;
        }
        //面板重绘
        this.repaint();
    }
    //当某个键释放，该方法被触发
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println((char) e.getKeyCode()+"按下");
    }
}
