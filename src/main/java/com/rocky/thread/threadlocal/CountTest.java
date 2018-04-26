package com.rocky.thread.threadlocal;


public class CountTest {


    public static void main(String[] args) throws Exception {
        Thread thread;
        //由于线程内部变量共享，导致counter计数非安全
		CountThread counter = new CountThread();
        //线程安全，变量为每个线程私有
//        SafeCountThread counter = new SafeCountThread();
        for (int i = 0; i < 2; i++) {
            thread = new Thread(counter);
            thread.start();
        }

    }

}
