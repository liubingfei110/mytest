package com.liubingfei.mytest.mythread;

/**
 * @Author: LiuBingFei
 * @Date: 2021-05-08 16:10
 * @Description: 测试线程2
 */
public class TwoThread implements Runnable{
    @Override
    public void run() {

    }

    public static void main(String[] args) {
        TwoThread twoThread = new TwoThread();
        Thread thread = new Thread(twoThread, "t2");
        thread.start();

        Thread thread2 = new Thread(() -> System.out.println("1"), "t22");
        thread2.start();
    }
}
