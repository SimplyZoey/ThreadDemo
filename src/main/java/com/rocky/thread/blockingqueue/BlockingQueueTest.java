/**
 * Author:   shitian
 * Date:     2018/4/23 10:59
 * Description:
 */
package com.rocky.thread.blockingqueue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 〈〉
 *
 * @author shitian
 * @create 2018/4/23
 * @since 1.0.0
 */
public class BlockingQueueTest {
    //将count换成int型则不行
    private static volatile AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        BlockingQueue queue = new BlockingQueue();
        ExecutorService pool = Executors.newFixedThreadPool(4);
        pool.submit(new Producer(queue, "producer1", count));
        pool.submit(new Producer(queue, "producer2", count));
        pool.submit(new Consumer(queue, "consumer1"));
        pool.submit(new Consumer(queue, "consumer2"));
        pool.shutdown();
    }
}

class Producer implements Runnable {
    private BlockingQueue queue;
    private String name;
    private AtomicInteger count;

    public Producer(BlockingQueue queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    public Producer(BlockingQueue queue, String name, AtomicInteger count) {
        this.queue = queue;
        this.name = name;
        this.count = count;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String item = "item" + count.incrementAndGet();
//                queue.enqueue(item);
                queue.enqueueLock(item);
                System.out.println(this.name + " put : " + item);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private BlockingQueue queue;
    private String name;

    public Consumer(BlockingQueue queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (true) {
//                String item = queue.dequeue().toString();
                String item = queue.dequeueLock().toString();
                System.out.println(this.name + " get : " + item);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class BlockingQueue {

    private List queue = new LinkedList();

    private int limit = 10;

    final ReentrantLock lock;
    final Condition notEmpty;
    final Condition notFull;

    public BlockingQueue() {
        this(10);
    }

    public BlockingQueue(int limit) {
        this.limit = limit;
        lock = new ReentrantLock(true);
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    public synchronized void enqueue(Object item) throws InterruptedException {
        while (this.queue.size() == this.limit) {
            wait();
        }
        if (this.queue.size() == 0) {

        }
        this.queue.add(item);
    }

    public void enqueueLock(Object item) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (this.queue.size() == this.limit) {
                notFull.await();
            }
            this.queue.add(item);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public synchronized Object dequeue() throws InterruptedException {
        while (this.queue.size() == 0) {
            wait();
        }
        if (this.queue.size() == this.limit) {
            notifyAll();
        }
        return this.queue.remove(0);
    }

    public Object dequeueLock() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (this.queue.size() == 0) {
                notEmpty.await();
            }
            Object result = this.queue.remove(0);
            notFull.signal();
            return result;
        } finally {
            lock.unlock();
        }
    }

}
