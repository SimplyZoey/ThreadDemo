package com.rocky.thread.forkjoin.recursiveaction;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class PrintNumber {

    static class PrintTask extends RecursiveAction {
        private static final int MAX_PRINT = 2;
        private int start;
        private int end;

        public PrintTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if ((end - start) < MAX_PRINT) {
                for (int i = start; i < end; i++) {
                    System.out.println(Thread.currentThread().getName() + "的i值：" + i);
                }
            } else {
                int middle = (start + end) / 2;
                PrintTask left = new PrintTask(start, middle);
                PrintTask right = new PrintTask(middle, end);
                left.fork();
                right.fork();
            }

        }

    }

    public static void main(String[] args) throws Exception {
        // 创建包含Runtime.getRuntime().availableProcessors()返回值作为个数的并行线程的ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(new PrintTask(0, 10));
        pool.awaitTermination(3, TimeUnit.SECONDS);
        pool.shutdown();

    }

}
