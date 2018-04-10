/**
 * Author:   shitian
 * Date:     2018/4/10 15:51
 * Description: notify死锁
 */
package com.rocky.thread.lock;

/**
 * 〈notify死锁〉
 *
 * @author shitian
 * @create 2018/4/10
 * @since 1.0.0
 */
public class NotifyDeadLockTwo {


    public static void main(String[] args)throws  Exception{
        LockObj lock_a = new LockObj("lock_a");
        LockObj lock_b = new LockObj("lock_b");

        Thread t1 = new Thread(new Task1(lock_a,lock_b));
        Thread t2 = new Thread(new Task1(lock_b,lock_a));
        t1.start();
        //睡2秒，等待线程1释放lock_b
        Thread.sleep(2000);
        t2.start();
    }
}

class LockObj {
    private String lockName;

    public LockObj(String name) {
        this.lockName = name;
    }

    public String getLockName() {
        return this.lockName;
    }

}

class Task1 implements Runnable {
    private LockObj lock_a;
    private LockObj lock_b;

    public Task1(LockObj lock_a, LockObj lock_b) {
        this.lock_a = lock_a;
        this.lock_b = lock_b;
    }

    private void dosomething() {
        synchronized (lock_a) {
            System.out.println(Thread.currentThread().getName() + " get lock : " + lock_a.getLockName());
            synchronized (lock_b) {
                System.out.println(Thread.currentThread().getName() + " get lock : " + lock_b.getLockName());
                System.out.println(Thread.currentThread().getName() + " dosomething.......");
                try {
                    System.out.println(Thread.currentThread().getName() + " release lock : " + lock_b.getLockName());
                    lock_b.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void run() {
        dosomething();
    }
}
