package com.rocky.thread.forkjoin.recursivetask;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class CountNumber {
    static class CountTask extends RecursiveTask<Integer> {
        private static final int MAX_NUM = 10;
        private int start;
        private int end;
        private int[] arry;

        public CountTask(int start, int end, int arry[]) {
            this.start = start;
            this.end = end;
            this.arry = arry;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            if ((end - start) < MAX_NUM) {
                for (int i = start; i < end; i++) {
                    sum += arry[i];
                }
                return sum;
            } else {
                System.out.println("==========分解任务========");
                int middle = (start + end) / 2;
                CountTask left = new CountTask(start, middle, arry);
                CountTask right = new CountTask(middle, end, arry);
                left.fork();
                right.fork();
                return left.join() + right.join();
            }
        }

    }

    public static void main(String[] args) throws Exception {
        int[] arry = new int[100];
        Random random = new Random();
        int total = 0;
        for (int i = 0; i < 100; i++) {
            arry[i] = random.nextInt(100);
            total += arry[i];
        }
        System.out.println("=======初始总和===========" + String.valueOf(total));
        ForkJoinPool pool = new ForkJoinPool();
        Future<Integer> future = pool.submit(new CountTask(0, 100, arry));
        System.out.println("======计算总和======" + String.valueOf(future.get()));
        pool.shutdown();
    }

}
