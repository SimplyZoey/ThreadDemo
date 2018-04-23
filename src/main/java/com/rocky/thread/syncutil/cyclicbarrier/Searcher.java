package com.rocky.thread.syncutil.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Searcher implements Runnable {

    private int firstRow;
    private int lastRow;
    private MatrixMock mock;
    private Result result;
    private int number;
    private final CyclicBarrier barrier;

    public Searcher(int firstRow, int lastRow, MatrixMock mock, Result result, int number, CyclicBarrier barrier) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.mock = mock;
        this.result = result;
        this.number = number;
        this.barrier = barrier;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + " Processing lines from " + firstRow + " to " + lastRow + ".");
        for (int i = firstRow; i < lastRow; i++) {
            int counter = 0;
            int row[] = mock.getRow(i);
            for (int j = 0; j < row.length; j++) {
                if (row[j] == number) {
                    counter++;
                }
            }
            result.setData(i, counter);
        }
        System.out.println(Thread.currentThread().getName() + " Lines processed.");
        try {
//			if (firstRow == 2) {
//				System.out.println(Thread.currentThread().getName() + "....................");
//				barrier.reset();//reset causes any currently waiting threads to throw a BrokenBarrierException and wake immediately
//			}
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//			System.out.println(Thread.currentThread().getName()+"开始重跑");
//			run();
        }

    }

}
