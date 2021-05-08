package com.liubingfei.mytest.mythread;

/**
 * @Author: LiuBingFei
 * @Date: 2021-05-08 16:06
 * @Description: 测试多线程 集成thread类
 */
public class OneThread extends Thread {
    @Override
    public void run(){

    }

    public static void main(String[] args) {
        Thread thread = new OneThread();
        thread.start();
    }
}
