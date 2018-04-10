package com.rocky.thread.lock;

/**
 * @Author: shtian
 * @Description:
 * @Date: Create in 2018/4/9 21:31
 */
public class DeadLock {

    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();
        System.out.println("lock a is : " + a.toString());
        System.out.println("lock b is : " + b.toString());
        Thread thread1 = new Thread(new Task(a, b));
        Thread thread2 = new Thread(new Task(b, a));
        thread1.start();
        thread2.start();
    }

}

class Task implements Runnable {
    private Object lock_a;
    private Object lock_b;

    public Task(Object a, Object b) {
        this.lock_a = a;
        this.lock_b = b;
    }

    public void doSomething() {
        synchronized (lock_a) {
            System.out.println(Thread.currentThread().getName() + " get lock :" + lock_a.toString() + " want get lock :" + lock_b.toString());
            synchronized (lock_b) {
                System.out.println(Thread.currentThread().getName() + "dosomething.......");
            }
        }
    }

    public void run() {
        doSomething();
    }
}
