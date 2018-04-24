package com.rocky.thread.threadlocal;

public class SafeCountThread implements Runnable {

    private ThreadLocal<Integer> counter = new ThreadLocal<Integer>() {
        //初始化
        protected Integer initialValue() {
            return 0;
        }
    };

    @Override
    public void run() {
        //如果threadlocal不初始化，这里会空指针
        int temp = counter.get();
        for (int i = 0; i < 10; i++) {
            temp++;
            System.out.println(Thread.currentThread().getName() + ":" + temp);
        }

    }

}
