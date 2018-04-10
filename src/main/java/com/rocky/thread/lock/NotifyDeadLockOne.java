/**
 * Author:   shitian
 * Date:     2018/4/10 15:18
 * Description:
 */
package com.rocky.thread.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 〈notify导致死锁场景〉
 *
 * @author shitian
 * @create 2018/4/10
 * @since 1.0.0
 */
public class NotifyDeadLockOne {
    public static void main(String[] args){
//        SingleCall();
        multiCall();
    }

    //一个生产，一个消费
    private static void SingleCall(){
        List<Integer> cache = new ArrayList();
        new Thread(new Consumer(cache)).start();
        new Thread(new Producer(cache)).start();
    }

    //多个生产，多个消费
    private static void multiCall(){
        List<Integer> cache = new ArrayList();
        new Thread(new Consumer(cache)).start();
        new Thread(new Consumer(cache)).start();
        new Thread(new Consumer(cache)).start();

        new Thread(new Producer(cache)).start();
        new Thread(new Producer(cache)).start();
        new Thread(new Producer(cache)).start();
    }

}

class Producer implements Runnable {
    List<Integer> cache;

    public Producer(List<Integer> cache) {
        this.cache = cache;
    }

    public void run() {
        while (true) {
            produce();
        }
    }

    private void produce() {
        synchronized (cache) {
            try {
//                while (cache.size() == 1) {
//                    cache.wait();
//                }
                if (cache.size() == 1) {
                    cache.wait();
                }

                // 模拟一秒生产一条消息
                Thread.sleep(1000);
                cache.add(new Random().nextInt());

                cache.notify();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


class Consumer implements Runnable {
    List<Integer> cache;

    public Consumer(List<Integer> cache) {
        this.cache = cache;
    }

    public void run() {
        while (true) {
            consume();
        }
    }

    private void consume() {
        synchronized (cache) {
            try {
//                while (cache.isEmpty()) {
//                    cache.wait();
//                }
                if (cache.isEmpty()) {
                    cache.wait();
                }

                System.out.println("Consumer consumed [" + cache.remove(0) + "]");
                cache.notify();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
