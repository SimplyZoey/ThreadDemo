package com.rocky.thread.fun;

import java.util.concurrent.ExecutorService;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author: shtian
 * @Description:
 * @Date: Create in 2018/4/11 21:56
 */
public class FutureCancel {
    static ExecutorService exec = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
//        test(new SleepBlocked());
//        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
    }

    public static void test(Runnable r) throws Exception {
        Future<?> f = exec.submit(r);
        System.out.println("prepare to cancel:" + r.getClass().getName());
        f.cancel(true);
        //io阻塞通过关闭io流来抛异常
//        System.in.close();
        System.out.println("Interrupt sent to :" + r.getClass().getName());
        exec.shutdown();
    }

}

class SleepBlocked implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.MINUTES.sleep(4);    //可被中断
        } catch (InterruptedException e) {
            System.out.println("sleep interruption!!");
        }
        System.out.println("exiting SleepBlocked-------------------------");
    }
}

class IOBlocked implements Runnable {
    private InputStream in;

    public IOBlocked(InputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            System.out.println("prepare to read:::::::::");
            in.read();        //IO阻塞，不可被线程中断
        } catch (IOException e) {
            //e.printStackTrace();
            if (Thread.interrupted()) {
                System.out.println("ioblocked is interrupted!!!");
            } else {
                System.out.println("ioblocked is not interrupted, just io exception");
                throw new RuntimeException();
            }
        }
        System.out.println("exiting IOBlocked---------------------");
    }
}

class SynchronizedBlocked implements Runnable {

    public synchronized void f() {
        while (true)
            Thread.yield();
    }

    public void run() {
        System.out.println("trying to call method");
        f();    //synchronized锁无法被线程中断
        System.out.println("exiting SynchronizedBlocked------------------------");
    }
}
