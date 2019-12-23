package com.demoboot.lockTest;

import org.junit.jupiter.api.Test;

public class TestLock {
    private Integer count = 100;

    @Test
    public void ticketTest() throws InterruptedException {
        TicketRunnable tr = new TicketRunnable();
        InitThread.init(tr);
        Thread.currentThread().join();
    }

    public class TicketRunnable implements Runnable {
        @Override
        public void run() {
            while (count > 0) {
                if (count > 0) {
                    System.out.println(Thread.currentThread().getName() + "售出第" + (count--) + "张票");
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
