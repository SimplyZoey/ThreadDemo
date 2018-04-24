package com.rocky.thread.threadlocal;


public class InheritableTest {


    public static void main(String[] args) {
        Thread thread = new Thread(new InheritableThreadLocalCount());
        thread.start();

    }

}
