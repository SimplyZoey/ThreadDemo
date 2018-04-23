package com.rocky.thread.syncutil.phaser;

import java.util.concurrent.Phaser;

/**
 * 实现主线程的结束可控制
 */
public class PhaserTest4 {

    public static void main(String[] args) throws Exception {
        final int phaseToTerminate = 4;
        Phaser phaser = new Phaser(5) {

            // 重载onadvance，这样phaser每阶段结束后，都会执行该方法
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("====== " + phase + " ======");
                return phase >= phaseToTerminate || registeredParties == 0;
            }
        };
        for (int i = 0; i < 5; i++) {
            new Thread(new Task(phaser, i)).start();
        }

        // 除了使用join来控制主线程晚于子线程结束，还可以使用该方法
//		phaser.register();// phaser加入main线程管理
//		while (!phaser.isTerminated()) {
//			phaser.arriveAndAwaitAdvance();
//		}
//		System.out.println("done....");

        //控制主线程在第三阶段结束
        awaitPhase(phaser, 3);

    }

    private static void awaitPhase(Phaser phaser, int phase) {
        int p = phaser.register();//为了获取当前phaser的阶段，新注册一个party，然后返回已经处理到第几阶段了
        System.out.println(p);
        while (p < phase) {
            if (phaser.isTerminated()) {
                break;
            } else {
                p = phaser.arriveAndAwaitAdvance();//返回当前的phaser阶段
            }
        }
        System.out.println(phaser.arriveAndDeregister());
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
