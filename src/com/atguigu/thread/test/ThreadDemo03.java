package com.atguigu.thread.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData
{
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws Exception
    {
        lock.lock();
        try
        {
            while(number != 0)
            {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            ++number;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws Exception
    {
        lock.lock();
        try
        {
            while(number == 0)
            {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            --number;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }



    /*public synchronized void increment() throws Exception
    {
        while(number != 0)
        {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        ++number;
        this.notifyAll();
    }

    public synchronized void decrement() throws Exception
    {
        while(number == 0)
        {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        --number;
        this.notifyAll();
    }*/

}


/**
 * Created by zhouyang on 2016-11-28 12:30.
 */
public class ThreadDemo03 {
    public static void main(String[] args) {
        final ShareData sd = new ShareData();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=10; i++) {
                    try
                    {
                        Thread.sleep(200);
                        sd.increment();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "AA").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=10; i++) {
                    try
                    {
                        Thread.sleep(300);
                        sd.decrement();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "BB").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=10; i++) {
                    try
                    {
                        Thread.sleep(400);
                        sd.increment();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "CC").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=10; i++) {
                    try
                    {
                        Thread.sleep(500);
                        sd.decrement();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "DD").start();


    }
}
