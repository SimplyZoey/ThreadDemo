package com.rocky.thread.fun;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: shtian
 * @Description:
 * @Date: Create in 2018/4/11 20:14
 */
public class YieldTest {

    public synchronized void printNumber(String threadName) {
        for (int i = 0; i < 10; i++) {
            System.out.println(threadName + ":[" + i + "]");
            if (i % 2 == 0) {
                System.out.println(threadName + ":start yield...");
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(6);
        YieldTest temp = new YieldTest();
        for (int i = 0; i < 10; i++) {
            pool.submit(new ThreadA(temp, "thread" + i));
        }
        pool.shutdown();
    }
}

class ThreadA implements Runnable {
    private YieldTest yieldTest;
    private String name;

    public ThreadA(YieldTest yieldTest, String name) {
        this.yieldTest = yieldTest;
        this.name = name;
    }


    public void run() {
        yieldTest.printNumber(name);
    }
}
