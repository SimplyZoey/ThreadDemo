package com.rocky.thread.syncutil.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest2 {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(2, new Grouper());
        new Thread(new Work1(barrier)).start();
        new Thread(new Work2(barrier)).start();

    }

    static class Work1 implements Runnable {

        private CyclicBarrier barrier;

        public Work1(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("开始第一轮");
                Thread.sleep(2000);
                barrier.await();

                System.out.println("开始第二轮");
                Thread.sleep(2000);
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    static class Work2 implements Runnable {

        private CyclicBarrier barrier;

        public Work2(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("开始第一轮");
                Thread.sleep(3000);
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    static class Grouper implements Runnable {

        @Override
        public void run() {
            System.out.println("开始处理公共数据。");

        }

    }

}
