package com.atguigu.thread.test;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ticket
{
    private int ticketNumber = 20;
    private Lock lock = new ReentrantLock();

    public void saleTicket()
    {
        lock.lock();

        try
        {
            if (ticketNumber>0)
            {
                Thread.sleep(200);
                System.out.println(Thread.currentThread().getName()+"卖出第："+"\t"+ticketNumber--+"\t 剩下: "+ticketNumber);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }
}


/**
 * Created by zhouyang on 2016-11-28 11:57.
 */
public class ThreadDemo01 {
    public static void main(String[] args) {
        final Ticket ticket = new Ticket();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=30 ; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ticket.saleTicket();
                }
            }
        },"AA").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=30 ; i++) {
                    ticket.saleTicket();
                }
            }
        },"BB").start();
        System.out.println("111111");

    }
}
