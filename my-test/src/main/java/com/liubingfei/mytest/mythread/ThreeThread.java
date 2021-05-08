package com.liubingfei.mytest.mythread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: LiuBingFei
 * @Date: 2021-05-08 16:17
 * @Description:
 */
public class ThreeThread implements Callable {
    @Override
    public Integer call() {
        return null;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new ThreeThread());
        Thread thread = new Thread(futureTask, "t3");
        thread.start();
        futureTask.get();
    }
}
