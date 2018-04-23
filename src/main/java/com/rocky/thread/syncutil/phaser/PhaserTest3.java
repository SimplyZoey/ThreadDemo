package com.rocky.thread.syncutil.phaser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class PhaserTest3 {

    public static void main(String[] args) {
        final int phaseToTerminate = 4;
        Phaser phaser = new Phaser(5) {

            // 重载onadvance，这样phaser每阶段结束后，都会执行该方法
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("====== " + phase + " ======");
                return phase >= phaseToTerminate || registeredParties == 0;
            }
        };
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            pool.execute(new Task(phaser, i));
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
            while (!phaser.isTerminated()) {
                phaser.arriveAndAwaitAdvance();
                System.out.println("in task run():phaser:" + phaser.getPhase() + ",id:" + id);
            }

        }

    }
}
