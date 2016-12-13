package com.atguigu.thread.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResource
{
    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void loopA(int totalLoop) throws InterruptedException
    {
        lock.lock();
        try
        {
            //1,判断
            while (number != 1)
            {
                condition1.await();
            }
            //2,打印
            for (int i = 1; i <=5 ; i++)
            {
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t totalLoop:"+totalLoop);

            }
            //3,唤醒
            number = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopB(int totalLoop) throws InterruptedException
    {
        lock.lock();
        try
        {
            //1,判断
            while (number != 2)
            {
                condition2.await();
            }
            //2,打印
            for (int i = 1; i <=10 ; i++)
            {
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t totalLoop:"+totalLoop);

            }
            //3,唤醒
            number = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopC(int totalLoop) throws InterruptedException
    {
        lock.lock();
        try
        {
            //1,判断
            while (number != 3)
            {
                condition3.await();
            }
            //2,打印
            for (int i = 1; i <=15 ; i++)
            {
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t totalLoop:"+totalLoop);

            }
            //3,唤醒
            number = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}




/**
 * Created by zhouyang on 2016-11-28 16:35.
 */
public class ThreadDemo04 {
    public static void main(String[] args) {
        final ShareResource sr = new ShareResource();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=20; i++) {
                    try
                    {
                        sr.loopA(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"AA").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=20; i++) {
                    try
                    {
                        sr.loopB(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"BB").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=20; i++) {
                    try
                    {
                        sr.loopC(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println();
                }
            }
        },"CC").start();




    }
}
