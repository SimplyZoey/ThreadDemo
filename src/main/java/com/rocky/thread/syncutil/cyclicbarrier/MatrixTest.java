package com.rocky.thread.syncutil.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

/*
 * barrier控制所有的线程在同一个时间点开始执行一件事，入参支持ruunable
 * 即可以实现分治编程，即多个线程处理一个任务的多个子任务，然后通过入参的runnable线程
 * 来进行统计综合处理。
 */
public class MatrixTest {

    public static void main(String[] args) {
        final int size = 10;
        final int length = 10;
        final int searchNum = 5;
        final int participants = 5;
        final int separation = 2;

        MatrixMock mock = new MatrixMock(size, length, searchNum);
        Result result = new Result(size);
        Grouper grouper = new Grouper(result);
        CyclicBarrier barrier = new CyclicBarrier(participants, grouper);
        Searcher[] serachers = new Searcher[participants];
        for (int i = 0; i < participants; i++) {
            serachers[i] = new Searcher(i * separation, i * separation + separation, mock, result, searchNum, barrier);
            Thread t = new Thread(serachers[i]);
            t.start();
        }
        System.out.println("Main thread has finished.");
    }

}
