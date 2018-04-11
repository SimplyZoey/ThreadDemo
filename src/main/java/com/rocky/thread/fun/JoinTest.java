package com.rocky.thread.fun;

/**
 * @Author: shtian
 * @Description:
 * @Date: Create in 2018/4/11 21:00
 */
public class JoinTest {

    public static void main(String[] args) throws Exception{
        Thread t1 = new Thread(new ThreadC());
        t1.start();
        t1.join();
        System.out.println("main thread finished.....");
    }
}

class ThreadC implements Runnable {

    private void dotask() throws Exception {
        System.out.println(Thread.currentThread().getName() + " : starting sleep 3s.....");
        for (int i = 3; i > 0; i--) {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " : count down " + i);
        }
        System.out.println(Thread.currentThread().getName() + " : sleep finished.....");
    }

    public void run() {
        try {
            dotask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
