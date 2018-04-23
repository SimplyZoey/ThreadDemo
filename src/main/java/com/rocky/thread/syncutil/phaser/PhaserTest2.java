package com.rocky.thread.syncutil.phaser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * 所有线程到达屏障后，需要一个条件线程来唤醒其他线程继续执行操作
 */
public class PhaserTest2 {

    public static void main(String[] args) {
        Phaser phaser = new Phaser(6);
        ExecutorService pool = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 5; i++) {
            pool.execute(new Task(phaser, i));
        }
        //满足该线程设置的条件时，执行其他所有的线程，然后把自己注销
        pool.execute(new Conditions(phaser));
        pool.shutdown();

    }

    static class Task implements Runnable {

        private Phaser phaser;
        private int id;

        public Task(Phaser phaser, int id) {
            this.phaser = phaser;
            this.id = id;
        }

        @Override
        public void run() {
            phaser.arriveAndAwaitAdvance();
            System.out.println("in task run():phaser:" + phaser.getPhase() + ",id:" + id);
        }

    }

    static class Conditions implements Runnable {

        private Phaser phaser;

        public Conditions(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("start all threads...");
            phaser.arriveAndDeregister();
        }

    }
}
