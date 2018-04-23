package com.rocky.thread.syncutil.semaphore;

public class Job implements Runnable {

    private PrintQueue printQueue;

    public Job(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":start print job.");
        printQueue.printJob(new Object());
        System.out.println(Thread.currentThread().getName() + ":the document has been printed.");
    }

}
