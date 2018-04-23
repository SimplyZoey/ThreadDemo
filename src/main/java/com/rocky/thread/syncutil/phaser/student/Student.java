package com.rocky.thread.syncutil.phaser.student;

import java.util.Date;
import java.util.concurrent.Phaser;

public class Student implements Runnable {

    private Phaser phaser;

    public Student(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " has arrived to do the exam." + new Date());
        phaser.arriveAndAwaitAdvance();
        System.out.println(Thread.currentThread().getName() + " it's going to do the first exerise." + new Date());
        doExam(1);
        System.out.println(Thread.currentThread().getName() + " has done the first exerise." + new Date());
        phaser.arriveAndAwaitAdvance();
        System.out.println(Thread.currentThread().getName() + " it's going to do the second exerise." + new Date());
        doExam(2);
        System.out.println(Thread.currentThread().getName() + " has done the second exerise." + new Date());
        phaser.arriveAndAwaitAdvance();
        System.out.println(Thread.currentThread().getName() + " it's going to do the third exerise." + new Date());
        doExam(3);
        System.out.println(Thread.currentThread().getName() + " has finished the exam." + new Date());
    }

    private void doExam(int round) {
        long sleepTime = (long) (Math.random() * 10000 * round);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
