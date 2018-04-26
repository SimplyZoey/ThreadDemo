package com.rocky.thread.threadlocal;

import java.util.concurrent.atomic.AtomicInteger;

public class CountThread implements Runnable {

//    private volatile int counter = 0;
    private volatile AtomicInteger counter = new AtomicInteger();

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
//            counter++;
            int result = counter.incrementAndGet();
            System.out.println(Thread.currentThread().getName() + ":" + result);
        }

    }

}
