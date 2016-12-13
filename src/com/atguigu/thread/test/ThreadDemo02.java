package com.atguigu.thread.test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyThread implements Callable<Integer>
{
    @Override
    public Integer call() throws Exception
    {
        System.out.println("-----call() method");
        return new Random().nextInt(100);
    }
}


/**
 * Created by zhouyang on 2016-11-28 12:08.
 */
public class ThreadDemo02 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {


        FutureTask<Integer> ft = new FutureTask<Integer>(new MyThread());

        new Thread(ft,"AA").start();

        while(!ft.isDone())
        {
            System.out.println("FutureTask正在计算结果");
        }


        System.out.println("------"+ft.get());
    }
}
