package com.atguigu.thread.test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by zhouyang on 2016-12-04 16:24.
 */
public class ThreadDemo06 {
    public static void main(String[] args) {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5);
        ScheduledFuture<Integer> result = null;
        try
        {
            for (int i = 1; i <=20 ; i++)
            {
                result = threadPool.schedule(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        Thread.sleep(300);
                        System.out.print(Thread.currentThread().getName());
                        return new Random().nextInt(100);
                    }
                },2,TimeUnit.SECONDS);

                System.out.println("*****"+result.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }

    private static void MyThreadPoolTest() {
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Future<Integer> result = null;

        try
        {
            for (int i = 1; i <=20 ; i++)
            {
                result = threadPool.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        Thread.sleep(300);
                        System.out.print(Thread.currentThread().getName());
                        return new Random().nextInt(100);
                    }
                });
                System.out.println("-----"+result.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
