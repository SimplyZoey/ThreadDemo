package com.rocky.thread.threadlocal;

public class CountThread implements Runnable {

    private int counter = 0;

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            counter++;
            System.out.println(Thread.currentThread().getName() + ":" + counter);
        }

    }

}
