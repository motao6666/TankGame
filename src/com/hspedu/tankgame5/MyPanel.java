package com.hspedu.tankgame5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

//绘图区域
//为了让Panel不停的绘制子弹，需要将MyPannel实现Runnable，当做一个线程使用
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义击败敌人坦克数量
    int count = 0;
    //定义我的坦克
    Hero hero = null;
    //定义敌人坦克
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个存放Node对象的Vector，用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes= new Vector<>();
    //定义一个Vector，用于存放bomb
    //子弹击中坦克时，加入一个Bomb对象到bombs
    Vector<Bomb> bombs = new Vector<>();

    int enemyTankSize = 3;

    //定义三张炸弹图片，显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;


    public MyPanel(String key) {
        //判断我们的记录文件是否存在
        //存在就正常执行，不存在就开新游戏，key = 1
        File file = new File(Recorder.getRecordFile());
        if(file.exists()){
            nodes = Recorder.getNodesAndEnemyTanRec();
        }else {
            System.out.println("文件不存在，开启新游戏");
            key = "1";
        }
        //讲Mypanel对象的enemyTanks设置给Recorder的enemyTanks
        Recorder.setEnemyTanks(enemyTanks);
        hero = new Hero(500, 100);//初始化坦克
        switch (key){
            case "1":
                //初始化敌人坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(2);
                    //启动敌人坦克线程
                    new Thread(enemyTank).start();
                    //给该ennemyTank加入一颗子弹
                    //Shot shot1 = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //加入enemyTank的Vector成员
                    // enemyTank.shots.add(shot1);
                    //启动enemyTank的shot对象
                    //new Thread(shot1).start();

                    //enemyTank.shots.remove(shot1);
                    //加入
                    enemyTanks.add(enemyTank);

                }
                break;
            case "2"://继续上局游戏
                //初始化敌人坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(node.getDirect());
                    //启动敌人坦克线程
                    new Thread(enemyTank).start();
                    //给该ennemyTank加入一颗子弹
                    //Shot shot1 = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //加入enemyTank的Vector成员
                    // enemyTank.shots.add(shot1);
                    //启动enemyTank的shot对象
                    //new Thread(shot1).start();

                    //enemyTank.shots.remove(shot1);
                    //加入
                    enemyTanks.add(enemyTank);

                }
                break;
            default:
                System.out.println("你的输入有误");
        }

        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb3.gif"));
        hero.setSpeed(8);
    }
    //编写方法，提示我方击毁敌方坦克消息
    public void  showInfo(Graphics g){
        //画出玩家的总成绩
        g.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("累计击中毁敌方坦克",1020,30);
        drawTank(1020,60,g,0,1);
        g.setColor(Color.black);
        g.drawString(Recorder.getAllEnemyTank()+"",1080,100);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.fillRect(0, 0, 1000, 750);//填充矩形颜色
        showInfo(g);
        //画出坦克——封装
        if (hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
        }
        //画出hero射击的子弹
//        if (hero.shot != null && hero.shot.isLive != false) {
//            g.setColor(Color.RED);
//            g.fill3DRect(hero.shot.x, hero.shot.y, 3, 3, false);
//        }
        //将hero的子弹集合shots遍历取出绘制
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive != false) {
                //g.setColor(Color.RED);
                g.fill3DRect(shot.x, shot.y, 3, 3, false);
            } else {//如果该shot对象已经无效，就从shots集合中拿掉
                hero.shots.remove(shot);
            }
        }
        //如果bombs集合中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);

            if (i == 0) {   //根据当前这个bomb对象的life值去画出对应的图片
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            //让这个炸弹的生命值减少
            bomb.lifeDown();
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }

        }
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
                //画出ennemyTank的所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.isLive) {
                        g.fill3DRect(shot.x, shot.y, 3, 3, false);
                    } else {
                        //移除边界外的子弹
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
    }

    /**
     * @param x      坦克左上角x坐标
     * @param y      坦克左上角y坐标
     * @param g      画笔
     * @param direct 方向
     * @param type   类型
     */
    //编写坦克
    public void drawTank(int x, int y, Graphics g, int direct, int type) {

        switch (type) {
            case 0://我们的坦克
                g.setColor(Color.cyan);
                break;
            case 1://敌人的坦克
                g.setColor(Color.yellow);
                break;
        }

        //根据坦克方向，绘制对应形状的坦克
        //direct 表示方向（0：向上 1：向右 2：向下 3：向左）
        //监听键盘事件
        switch (direct) {
            case 0://向上
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y);
                break;
            case 1:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2:
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            default:
                System.out.println("暂时没有处理");
        }

    }

    //所有的子弹，都取出和敌人的所有坦克，进行判断
    public void hitEnemyTank() {
        // 遍历所有敌人坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            // 遍历英雄所有子弹
            for (int j = 0; j < hero.shots.size(); j++) {
                Shot shot = hero.shots.get(j);
                // 单独判断当前这颗子弹是否存活
                if (shot.isLive) {
                    hitTank(shot, enemyTank);
                }
            }
        }
    }

    public void hitHeroTank() {
        //遍历所有敌人坦克和他们的子弹
        //遍历所有敌人坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            //遍历坦克的子弹
            for (int j = 0; j < enemyTanks.get(i).shots.size(); j++) {
                Shot shot = enemyTanks.get(i).shots.get(j);
                if (shot.isLive) {
                    hitHeroTank(shot, hero);
                }
            }
        }
    }

    //方法：判断敌方子弹是否击中我方坦克
    public void hitHeroTank(Shot s, Hero hero) {
        switch (hero.getDirect()) {
            case 0:
            case 2:
                if (s.x > hero.getX() && s.x < hero.getX() + 40 && s.y > hero.getY() && s.y < hero.getY() + 60) {
                    //创建一个Bmob对象，加入到bombs集合
                    Bomb bomb = new Bomb(hero.getX(), hero.getY());
                    if (hero.isLive == true) {
                        bombs.add(bomb);
                        s.isLive = false;
                    }
                    hero.isLive = false;
                    //删除这个坦克对象
                    hero = null;
                }
                break;
            case 1:
            case 3:
                if (s.x > hero.getX() && s.x < hero.getX() + 60 && s.y > hero.getY() && s.y < hero.getY() + 40) {
                    //创建一个Bmob对象，加入到bombs集合
                    Bomb bomb = new Bomb(hero.getX(), hero.getY());
                    if (hero.isLive == true) {
                        bombs.add(bomb);
                        s.isLive = false;
                    }
                    hero.isLive = false;
                    //删除这个坦克对象
                    hero = null;
                }
                break;

        }
    }

    //方法：判断我方子弹是否集中敌人坦克
    public void hitTank(Shot s, Tank enemyTank) {
        switch (enemyTank.getDirect()) {
            case 0:
            case 2:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40 && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //创建一个Bmob对象，加入到bombs集合
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                    //删除这个坦克对象
                    enemyTanks.remove(enemyTank);
                    //击败一个敌人时，allEnemyTankNum++
                    if(enemyTank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                }
                break;
            case 1:
            case 3:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60 && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //创建一个Bmob对象，加入到bombs集合
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                    //删除这个坦克对象
                    enemyTanks.remove(enemyTank);
                    //击败一个敌人时，allEnemyTankNum++
                    if(enemyTank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                }
                break;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //在java中，会给每一个键，分配一个值（int）
        if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(2);
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0);
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(3);
            hero.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(1);
            hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            //判断hero的子弹是否销毁，发射一颗子弹
            //if(hero.shot==null||!hero.shot.isLive){
            //hero.shotEnemyTank();
            //System.out.println("我方坦克按下时,shot是null或者shot已经销毁");
            //}
            //发射多颗子弹
            if (hero.shots.size() < 5) {
                hero.shotEnemyTank();
            }
        }
        //面板重绘
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每100mm重绘一次
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hitEnemyTank();
            hitHeroTank();
            this.repaint();
        }
    }
}
