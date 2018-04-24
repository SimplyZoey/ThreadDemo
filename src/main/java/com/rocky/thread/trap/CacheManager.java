package com.rocky.thread.trap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: shtian
 * @Description:
 * @Date: Create in 2018/4/9 19:55
 */
public class CacheManager {
    private Map<String, String> cache = new HashMap<>();
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public static int THREAD_COUNT = 2;

    public void fresh() {
        synchronized (cache) {
            cache.clear();
        }
    }

    public void put(String key, String value) {
//        synchronized (cache) {
        lock.writeLock().lock();
        cache.put(key, value);
        System.out.println(Thread.currentThread().getName() + " cache.put----" + value);
        lock.writeLock().unlock();
//        }
    }

    public String get(String key) {
        String result = null;
//        synchronized (cache) {
        lock.readLock().lock();
        result = cache.get(key);
        System.out.println(Thread.currentThread().getName() + " cache.get-----" + result);
        lock.readLock().unlock();
//        }
        return result;
    }

    public static void main(String[] args) {
        final CacheManager manager = new CacheManager();
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    manager.put("test_key", "value_" + j);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    manager.get("test_key");
                }
            });
            threads[i].start();
        }
    }
}
