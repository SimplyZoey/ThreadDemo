package com.rocky.thread.syncutil.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintQueue {

    private boolean freePrinters[];
    private Lock lockPrinters;
    private final Semaphore semaphore;

    public PrintQueue() {
        // 限制每次有多少个线程可以跑
        this.semaphore = new Semaphore(3);
        this.freePrinters = new boolean[3];
        for (int i = 0; i < 3; i++) {
            freePrinters[i] = true;
        }
        this.lockPrinters = new ReentrantLock();
    }

    public void printJob(Object document) {
        try {
            semaphore.acquire();
            // 获取分配的打印机
            int assignedPrinter = getPrinter();
            int duration = (int) (Math.random() * 10000);
            System.out.println(Thread.currentThread().getName() + " get " + assignedPrinter + " printer." + " print job during " + duration / 1000 + " second.");
            Thread.sleep(duration);
            // 设置对应的打印机为空闲
            this.freePrinters[assignedPrinter] = true;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            semaphore.release();
        }

    }

    private int getPrinter() {
        int ret = -1;
        try {
            lockPrinters.lock();
            for (int i = 0, len = freePrinters.length; i < len; i++) {
                if (freePrinters[i]) {
                    ret = i;
                    freePrinters[i] = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lockPrinters.unlock();
        }

        return ret;
    }
}
