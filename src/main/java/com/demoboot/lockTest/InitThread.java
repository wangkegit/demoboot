package com.demoboot.lockTest;

public class InitThread {

    public  static void init(Runnable tr){
        Thread t1 = new Thread(tr, "窗口A");
        Thread t2 = new Thread(tr, "窗口B");
        Thread t3 = new Thread(tr, "窗口C");
        Thread t4 = new Thread(tr, "窗口D");
        Thread t5 = new Thread(tr, "窗口E");
        Thread t6 = new Thread(tr, "窗口F");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}
