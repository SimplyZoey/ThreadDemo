package com.rocky.thread.syncutil.semaphore;

import java.util.concurrent.Semaphore;

/**
 * 这个线程组可以用来管理一组线程，通过activeCount() 来查看活动线程的数量。其他没有什么大的用处
 */
public class SemaphoreTest2 {

    public static void main(String[] args) throws Exception{
        Semaphore semaphore = new Semaphore(1);
        ThreadGroup group = new ThreadGroup("semaphore");
        Work work = new Work(semaphore);
        Thread t;
        for (int i = 0; i < 5; i++) {
            t = new Thread(group, work);
            t.start();
        }
        while(true){
            Thread.sleep(1000);
            System.out.println(group.activeCount());
        }

    }

    static class Work implements Runnable {

        private Semaphore semaphore;

        public Work(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                long duration = (long) (Math.random() * 10000);
                System.out.println(Thread.currentThread().getName() + ": sleep " + duration / 1000 + " seconds.");
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                semaphore.release();
            }

        }

    }

}
