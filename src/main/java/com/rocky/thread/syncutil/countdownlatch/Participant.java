package com.rocky.thread.syncutil.countdownlatch;


public class Participant implements Runnable {
    private String name;
    private VideoConference conference;

    public Participant(String name, VideoConference conference) {
        this.name = name;
        this.conference = conference;
    }

    @Override
    public void run() {
        long duration = (long) (Math.random() * 10000);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        conference.arrive(name);

    }

}
