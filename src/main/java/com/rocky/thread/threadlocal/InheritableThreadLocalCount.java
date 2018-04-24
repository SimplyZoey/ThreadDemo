package com.rocky.thread.threadlocal;

import java.util.HashMap;

public class InheritableThreadLocalCount implements Runnable {

    //继承给子线程后，后续父线程对该变量的任何操作，子线程都能获取到
    private InheritableThreadLocal<HashMap<String, String>> name = new InheritableThreadLocal<HashMap<String, String>>();
    //使用ThreadLocal,这个变量不会继承给创建的子线程
    //private ThreadLocal<String> name = new ThreadLocal<String>();

    public void run() {
        HashMap<String, String> result = name.get();
        if (result == null) {
            result = new HashMap<String, String>();
            result.put("parent", "parent");
            name.set(result);
        }
        System.out.println("parent:" + Thread.currentThread().getName() + ":" + name.get().get("parent"));
        new Thread(new Runnable() {

            @Override
            public void run() {
                //打印出从父类继承的threadlocal
                System.out.println("init:" + Thread.currentThread().getName() + ":" + name.get().get("parent"));
                name.get().put("child", "child");
                System.out.println("child:" + Thread.currentThread().getName() + ":" + name.get().get("child"));
                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("child:" + Thread.currentThread().getName() + ":" + name.get().get("parent1"));
                }
            }
        }).start();
        name.get().put("parent1", "parent1");
    }

}
