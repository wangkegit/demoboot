package com.demoboot.study.thread;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/11/5 10:57<br/>
 * @author wangke<br />
 * @version
 * @since JDK 1.8
 */

public class ThreadWhy {
    private static int a = 0 ;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);
        for (int i = 0; i < threads.length; i++) {
            threads[i] =new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    a++;
                };
                latch.countDown();
            });
        }
        Arrays.stream(threads).forEach(t->t.start());
        latch.await();
        System.out.println(a);
    }
}
