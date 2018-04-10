package com.rocky.thread.lock;

/**
 * @Author: shtian
 * @Description:
 * @Date: Create in 2018/4/10 19:19
 */
public class SyncMethod {

    public static synchronized void SyncStaticMethod() throws Exception {
        System.out.println(Thread.currentThread().getName() + " calling SyncStaticMethod......");
        for (int i = 3; i > 0; i--) {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " count down : " + i);
        }

    }

    public synchronized void SyncMethod() throws Exception {
        System.out.println(Thread.currentThread().getName() + " calling SyncMethod......");
        for (int i = 3; i > 0; i--) {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " count down : " + i);
        }
    }

    public void SyncObjBlockMethod() throws Exception {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " calling SyncObjBlockMethod......");
            for (int i = 3; i > 0; i--) {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " count down : " + i);
            }
        }
    }

    public void SyncClassBlockMethod() throws Exception {
        synchronized (SyncMethod.class) {
            System.out.println(Thread.currentThread().getName() + " calling SyncClassBlockMethod......");
            for (int i = 3; i > 0; i--) {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " count down : " + i);
            }
        }
    }

    public static void SyncStaticClassBlockMethod() throws Exception {
        synchronized (SyncMethod.class) {//静态方法中不能引用this，this不是静态变量
            System.out.println(Thread.currentThread().getName() + " calling SyncStaticClassBlockMethod......");
            for (int i = 3; i > 0; i--) {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " count down : " + i);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //同时访问静态同步方法
//        callStaticSync();
        //同时访问同步方法
//        callSync();
        //同时访问锁类的同步代码块
//        callSyncClassBlock();
        //同时访问锁对象的同步代码块
//        callSyncObjBlock();
        //分别调用对象锁和类锁的两个同步方法
        callObjAndClassLock();
    }

    private static void callObjAndClassLock(){
        SyncMethod obj = new SyncMethod();
        Thread t1 = new Thread(new Task3(obj));//相同对象的类锁同步代码块
        Thread t2 = new Thread(new Task2(obj));//相同对象的同步方法
        t1.start();
        t2.start();
    }

    private static void callSyncObjBlock() {
        SyncMethod obj = new SyncMethod();
        SyncMethod obj1 = new SyncMethod();
        Thread t1 = new Thread(new Task4(obj));
        Thread t2 = new Thread(new Task4(obj1));//不同对象不用等待
        t1.start();
        t2.start();
    }

    private static void callSyncClassBlock() {
        SyncMethod obj = new SyncMethod();
        SyncMethod obj1 = new SyncMethod();
        Thread t1 = new Thread(new Task3(obj));
        Thread t2 = new Thread(new Task3(obj1));//不同对象也要等待
        t1.start();
        t2.start();
    }

    private static void callSync() {
        SyncMethod obj = new SyncMethod();
        SyncMethod obj1 = new SyncMethod();
        Thread t1 = new Thread(new Task2(obj));
        Thread t2 = new Thread(new Task2(obj1));//不同对象互不影响
        t1.start();
        t2.start();
    }

    private static void callStaticSync() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SyncMethod.SyncStaticMethod();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SyncMethod.SyncStaticMethod();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
    }
}

class Task2 implements Runnable {
    private SyncMethod obj;

    public Task2(SyncMethod obj) {
        this.obj = obj;
    }

    public void run() {
        try {
            obj.SyncMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Task3 implements Runnable {
    private SyncMethod obj;

    public Task3(SyncMethod obj) {
        this.obj = obj;
    }

    public void run() {
        try {
            obj.SyncClassBlockMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Task4 implements Runnable {
    private SyncMethod obj;

    public Task4(SyncMethod obj) {
        this.obj = obj;
    }

    public void run() {
        try {
            obj.SyncObjBlockMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
