package com.rocky.thread.syncutil.semaphore;

/**
 * 排队等待获取共享资源
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        PrintQueue queue = new PrintQueue();
        Thread[] jobs = new Thread[10];
        for (int i = 0; i < 10; i++) {
            jobs[i] = new Thread(new Job(queue), "Job:" + i);
        }
        for (int i = 0; i < 10; i++) {
            jobs[i].start();
        }

    }

}
