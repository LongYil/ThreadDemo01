package com.atguigu.thread.test;


import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyQueue
{
    private Object obj;
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    public void get()
    {
        readWriteLock.readLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t"+obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void set(Object obj)
    {
        readWriteLock.writeLock().lock();
        try
        {
            this.obj = obj;
            System.out.println(Thread.currentThread().getName()+"\t"+obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }




}


/**
 * Created by zhouyang on 2016-12-04 15:33.
 */
public class ThreadDemo05 {
    public static void main(String[] args) {
        final MyQueue myQueue = new MyQueue();

        new Thread(new Runnable() {
            @Override
            public void run() {
                myQueue.set(new Random().nextInt(20));
            }
        },"AA").start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= 100 ; i++)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    myQueue.get();
                }
            },String.valueOf(i)).start();
        }
    }
}
