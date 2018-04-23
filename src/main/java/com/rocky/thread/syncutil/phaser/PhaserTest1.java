package com.rocky.thread.syncutil.phaser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * 使用phaser实现所有线程到达同一屏障后，再执行后续任务
 */
public class PhaserTest1 {

    public static void main(String[] args) throws Exception {
        Phaser phaser = new Phaser(5);
//		for (int i = 0; i < 5; i++) {
//			new Thread(new Task(phaser, i)).start();
//			//虽然线程的启动有时间间隔，但是线程执行的任务最后是同时开始的
//			Thread.sleep(1000);
//		}
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            pool.execute(new Task(phaser, i));
            Thread.sleep(1000);
        }
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
            //每个线程到了这里就等待，直到所有的线程都到齐，一起执行后面的操作
            phaser.arriveAndAwaitAdvance();
            System.out.println("in task run():phaser:" + phaser.getPhase() + ",id:" + id);

        }

    }

}
