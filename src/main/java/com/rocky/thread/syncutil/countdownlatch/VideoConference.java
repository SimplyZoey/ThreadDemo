package com.rocky.thread.syncutil.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class VideoConference implements Runnable {

    private final CountDownLatch controller;

    public VideoConference(int num) {
        this.controller = new CountDownLatch(num);
    }

    @Override
    public void run() {
        System.out.println("Videoconference:Initialzation: " + controller.getCount() + " participants.");
        try {
            controller.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Videoconference:Start.");
    }

    public void arrive(String name) {
        System.out.println(name + " has arrived.");
        controller.countDown();
        System.out.println("Videoconference:Waiting for " + controller.getCount() + " participants.");
    }

}
