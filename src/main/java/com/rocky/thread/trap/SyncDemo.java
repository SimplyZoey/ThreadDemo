/**
 * Author:   shitian
 * Date:     2018/4/24 11:03
 * Description: 并发读多写少使用sync影响性能
 */
package com.rocky.thread.trap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 〈并发读多写少使用sync影响性能〉
 *
 * @author shitian
 * @create 2018/4/24
 * @since 1.0.0
 */
public class SyncDemo {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception {
        Data data = new Data();
        Worker t1 = new Worker(data, true, "t1");
        Worker t2 = new Worker(data, true, "t2");
        t1.start();

        t2.start();
    }

    static class Worker extends Thread {
        Data data;
        boolean read;

        public Worker(Data data, boolean read, String threadName) {
            super(threadName);
            this.data = data;
            this.read = read;
        }

        public void run() {
            if (read) {
                data.get();
//                data.getWithLock();
            } else {
                data.set();
//                data.setWithLock();
            }
        }
    }

    /**
     * 数据类
     */
    static class Data {
        //使用读写锁，实现并发读,同步写
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Lock read = lock.readLock();
        Lock write = lock.writeLock();

        /**
         * 写数据
         */
        public void setWithLock() {
            write.lock();
            System.out.println(Thread.currentThread().getName()
                    + " set:begin " + sdf.format(new Date()));
            try {
                Thread.sleep(5000);
            } catch (Exception e) {

            } finally {
                System.out.println(Thread.currentThread().getName()
                        + " set:end " + sdf.format(new Date()));
                write.unlock();
            }

        }

        /**
         * 读数据
         */
        public int getWithLock() {
            read.lock();
            System.out.println(Thread.currentThread().getName()
                    + " get :begin " + sdf.format(new Date()));
            try {
                Thread.sleep(5000);
            } catch (Exception e) {

            } finally {
                System.out.println(Thread.currentThread().getName()
                        + " get :end " + sdf.format(new Date()));
                read.unlock();
            }
            return 1;
        }

        /**
         * 写数据，sync同步读，影响性能
         */
        public synchronized void set() {
            System.out.println(Thread.currentThread().getName() + " set:begin "
                    + sdf.format(new Date()));
            try {
                Thread.sleep(5000);
            } catch (Exception e) {

            } finally {
                System.out.println(Thread.currentThread().getName()
                        + " set:end " + sdf.format(new Date()));
            }

        }

        /**
         * 读数据
         */
        public synchronized int get() {
            System.out.println(Thread.currentThread().getName()
                    + " get :begin " + sdf.format(new Date()));
            try {
                Thread.sleep(5000);
            } catch (Exception e) {

            } finally {
                System.out.println(Thread.currentThread().getName()
                        + " get :end " + sdf.format(new Date()));
            }
            return 1;
        }
    }
}
