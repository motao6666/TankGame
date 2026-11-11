package com.hspedu.tankgame5;

import javax.swing.*;
import java.io.*;
import java.util.Vector;

//用于记录相关信息
public class Recorder {
    //定义变量，记录我方击毁坦克数量
    private static int allEnemyTankNum = 0;
    //定义IO对象,写数据到文件中
    private static FileWriter fw = null;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    public static String recordFile = "src\\myRecord.txt";
    //定义Vector，指向Mypanel对象的敌人坦克Vector
    private static Vector<EnemyTank> enemyTanks = null;
    //定义一个Node的Vector，用于保存敌人信息
    private static Vector<Node> nodes = new Vector<>();

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static String getRecordFile() {
        return recordFile;
    }

    //增加一个方法，用于读取recordFile，恢复相关信息
    //该方法，在继续上局的时候调用即可
    public static Vector<Node> getNodesAndEnemyTanRec(){
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //循环读取文件，生成nodes集合
            String line = "";
            while ((line = br.readLine())!=null){
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]),
                        Integer.parseInt(xyd[1]),
                        Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br!=null){
                br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nodes;
    }
    //增加一个方法，当游戏退出时，我们将allEnemyTankNum保存到recordFile
    //保存敌人坦克的坐标和方向

    public static void keepRecord(){
        try {
             bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum+"\r\n");
            //遍历敌人坦克的Vector，然后根据情况情况保存即可
            //oop，定义一个属性，然后通过setXxx得到敌人坦克的集合
            for (int i = 0; i < enemyTanks.size(); i++) {
                //取出
                EnemyTank enemyTank = enemyTanks.get(i);
                if(enemyTank.isLive){
                    //保存enemyTank的信息
                    String record = enemyTank.getX()+" "+enemyTank.getY()+" "+enemyTank.getDirect();
                    //写入文件
                    bw.write(record+"\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static int getAllEnemyTank() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTank(int allEnemyTank) {
        Recorder.allEnemyTankNum = allEnemyTank;
    }
    //当我方坦克击毁一个敌人坦克，就应当allEnemyTankNum++
    public static void addAllEnemyTankNum(){
    Recorder.allEnemyTankNum++;
    }
}
