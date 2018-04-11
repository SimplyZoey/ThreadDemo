package com.rocky.thread.fun;

/**
 * @Author: shtian
 * @Description:
 * @Date: Create in 2018/4/11 20:54
 */
public class SleepTest {

    public synchronized void doSomething() throws Exception {
        System.out.println(Thread.currentThread().getName() + " sleep 3s.....");
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + " sleep is finish....");
    }

    public static void main(String[] args) {
        SleepTest temp = new SleepTest();
        Thread t1 = new Thread(new ThreadB(temp));
        Thread t2 = new Thread(new ThreadB(temp));
        t1.start();
        t2.start();
    }
}

class ThreadB implements Runnable {
    private SleepTest sleepTest;

    public ThreadB(SleepTest sleepTest) {
        this.sleepTest = sleepTest;
    }

    public void run() {
        try {
            sleepTest.doSomething();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
