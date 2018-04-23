package com.rocky.thread.syncutil.phaser.student;

import java.util.concurrent.Phaser;


public class StudentTest {


    public static void main(String[] args) {
        Phaser phaser = new MyPhaser(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new Student(phaser)).start();
        }

    }

}
