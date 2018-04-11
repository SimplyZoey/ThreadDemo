package com.rocky.thread.fun;

/**
 * @Author: shtian
 * @Description:
 * @Date: Create in 2018/4/11 21:19
 */
public class InterruptTest {

    /**
     * (01) 主线程main中通过new MyThread("t1")创建线程t1，之后通过t1.start()启动线程t1。
     * (02) t1启动之后，会不断的检查它的中断标记，如果中断标记为“false”；则休眠100ms。
     * (03) t1休眠之后，会切换到主线程main；主线程再次运行时，会执行t1.interrupt()中断线程t1。
     * t1收到中断指令之后，会将t1的中断标记设置“false”，而且会抛出InterruptedException异常。
     * 在t1的run()方法中，是在循环体while之外捕获的异常；因此循环被终止。
     * <p>
     * 注意：如果线程的try/catch在while循环内部，则必须手工break，否则会死循环。
     *
     * @throws InterruptedException
     */
    private static void interruptBlockThread() throws InterruptedException {
        Thread t1 = new TrheadD("t1");  // 新建“线程t1”
        System.out.println(t1.getName() + " (" + t1.getState() + ") is new.");

        t1.start();                      // 启动“线程t1”
        System.out.println(t1.getName() + " (" + t1.getState() + ") is started.");

        // 主线程休眠300ms，然后主线程给t1发“中断”指令。
        Thread.sleep(300);
        t1.interrupt();
        System.out.println(t1.getName() + " (" + t1.getState() + ") is interrupted.");

        // 主线程休眠300ms，然后查看t1的状态。
        Thread.sleep(300);
        System.out.println(t1.getName() + " (" + t1.getState() + ") is interrupted now.");
    }

    private static void interruptThread() throws InterruptedException {
        Thread t2 = new TrheadE("t2");  // 新建“线程t1”
        System.out.println(t2.getName() + " (" + t2.getState() + ") is new.");

        t2.start();                      // 启动“线程t1”
        System.out.println(t2.getName() + " (" + t2.getState() + ") is started.");

        // 主线程休眠10ms，然后主线程给t1发“中断”指令。
        Thread.sleep(10);
        t2.interrupt();
        System.out.println(t2.getName() + " (" + t2.getState() + ") is interrupted.");

        // 主线程休眠300ms，然后查看t1的状态。
        Thread.sleep(300);
        System.out.println(t2.getName() + " (" + t2.getState() + ") is interrupted now.");
    }

    private static void interruptThreadWithFlag() throws InterruptedException {
        TrheadF t3 = new TrheadF("t3");  // 新建“线程t1”
        System.out.println(t3.getName() + " (" + t3.getState() + ") is new.");

        t3.start();                      // 启动“线程t1”
        System.out.println(t3.getName() + " (" + t3.getState() + ") is started.");

        // 主线程休眠10ms，然后主线程给t3发“中断”指令。
        Thread.sleep(10);
        t3.stopTask();
        System.out.println(t3.getName() + " (" + t3.getState() + ") is interrupted.");

        // 主线程休眠300ms，然后查看t3的状态。
        Thread.sleep(300);
        System.out.println(t3.getName() + " (" + t3.getState() + ") is interrupted now.");
    }


    public static void main(String[] args) {
        try {
            //通过interrupt来中断处于阻塞状态的线程
//            interruptBlockThread();
//            interruptThread();
            interruptThreadWithFlag();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class TrheadD extends Thread {

    public TrheadD(String name) {
        super(name);
    }

    public void run() {
        try {
            int i = 0;
            while (!isInterrupted()) {
                Thread.sleep(100); // 休眠100ms
                i++;
                System.out.println(Thread.currentThread().getName() + " (" + this.getState() + ") loop " + i);
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " (" + this.getState() + ") catch InterruptedException.");
        }
    }
}

class TrheadE extends Thread {

    public TrheadE(String name) {
        super(name);
    }

    public void run() {
        int i = 0;
        while (!isInterrupted()) {
            i++;
            System.out.println(Thread.currentThread().getName() + " (" + this.getState() + ") loop " + i);
        }
    }
}

class TrheadF extends Thread {
    private volatile boolean flag = true;

    public void stopTask() {
        flag = false;
    }


    public TrheadF(String name) {
        super(name);
    }

    private synchronized void dotask() {
        int i = 0;
        try {
            while (flag) {
                Thread.sleep(100);
                i++;
                System.out.println(Thread.currentThread().getName() + " (" + this.getState() + ") loop " + i);
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " (" + this.getState() + ") catch InterruptedException.");
        }
    }

    public void run() {
        dotask();
    }
}


