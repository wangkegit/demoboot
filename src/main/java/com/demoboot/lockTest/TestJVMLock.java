package com.demoboot.lockTest;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestJVMLock {
    private AtomicInteger count  = new AtomicInteger(100);

    private Lock lock = new ReentrantLock();

    @Test
    public void ticketTest() throws InterruptedException {
        TicketRunnable tr = new TicketRunnable();
        InitThread.init(tr);
        Thread.currentThread().join();
    }

    public class TicketRunnable implements Runnable {

        @Override
        // synchronized
        public void run() {
            while (count.get() > 0) {
                synchronized (this){
                        if (count.get() > 0) {
                            System.out.println(Thread.currentThread().getName() + "售出第" + "张票");
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
            }
        }

      /*  @Override
        public void run() {
            try{  lock.lock();
            while (count > 0) {

                    if (count > 0) {
                        System.out.println(Thread.currentThread().getName() + "售出第" + (count--) + "张票");
                    }
                     Thread.sleep(50);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }

                }
        }*/


    /*    @Override
        public void run() {
            while (count > 0) {
                lock.lock();
                try {
                    if (count > 0) {
                        System.out.println(Thread.currentThread().getName() + "售出第" + (count--) + "张票");
                    }
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println("b = " + b);
                }
            }
        }*/
    }
}
