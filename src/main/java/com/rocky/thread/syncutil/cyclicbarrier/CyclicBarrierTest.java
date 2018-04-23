package com.rocky.thread.syncutil.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new BarrierAction());
        for (int i = 0; i < 3; i++) {
            new Thread(new Work(cyclicBarrier, i)).start();
        }

    }

    static class Work implements Runnable {

        private CyclicBarrier barrier;
        private int sleepSecond;

        public Work(CyclicBarrier barrier, int sleepSecond) {
            this.barrier = barrier;
            this.sleepSecond = sleepSecond;
        }

        @Override
        public void run() {
            try {
                System.out.println("开始第一轮处理");
                Thread.sleep(sleepSecond * 1000);
                System.out.println(Thread.currentThread().getName() + "即将到达屏障，已有" + barrier.getNumberWaiting() + "个线程在等待......");
                barrier.await();

                System.out.println("开始第二轮处理");
                Thread.sleep(sleepSecond * 1000);
                System.out.println(Thread.currentThread().getName() + "即将到达屏障，已有" + barrier.getNumberWaiting() + "个线程在等待......");
                barrier.await();

                System.out.println("开始第三轮处理");
                Thread.sleep(sleepSecond * 1000);
                System.out.println(Thread.currentThread().getName() + "即将到达屏障，已有" + barrier.getNumberWaiting() + "个线程在等待......");
                barrier.await();
                System.out.println("所有任务处理结束");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    static class BarrierAction implements Runnable {

        @Override
        public void run() {
            System.out.println("开始处理公共数据！");

        }

    }

}
