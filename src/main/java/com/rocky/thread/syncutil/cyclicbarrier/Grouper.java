package com.rocky.thread.syncutil.cyclicbarrier;

public class Grouper implements Runnable {

    private Result result;

    public Grouper(Result result) {
        this.result = result;
    }

    @Override
    public void run() {
        int finalResult = 0;
        System.out.println("Grouper: Processing results.");
        int data[] = result.getData();
        for (int num : data) {
            System.out.print(num + " ");
            finalResult += num;
        }
        System.out.println();
        System.out.println("Grouper:Total result:" + finalResult);
    }

}
