package com.rocky.thread.syncutil.countdownlatch;

public class ConferenceTest {

    public static void main(String[] args) {
        VideoConference conference = new VideoConference(10);
        Thread video = new Thread(conference);
        video.start();
        for (int i = 0; i < 10; i++) {
            new Thread(new Participant("Participant" + i, conference)).start();
        }

    }

}
