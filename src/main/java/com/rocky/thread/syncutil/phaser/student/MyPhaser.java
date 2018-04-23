package com.rocky.thread.syncutil.phaser.student;

import java.util.concurrent.Phaser;

public class MyPhaser extends Phaser {

    public MyPhaser(int party) {
        super(party);
    }

    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        boolean flag = true;
        switch (phase) {
            case 0:
                flag = studentsArrived();
                break;
            case 1:
                flag = finishFirstExecrise();
                break;
            case 2:
                flag = finishSecondExecrise();
                break;
            case 3:
                flag = finishExam();
                break;
            default:
                break;
        }
        return flag;
    }

    private boolean studentsArrived() {
        System.out.println("Phaser:The exam are going to start.The students are ready.");
        System.out.println("We have " + getRegisteredParties() + " students.");
        return false;
    }

    private boolean finishFirstExecrise() {
        System.out.println("Phaser:All student are finish the frist exam.");
        System.out.println("Phaser:It's time to start second exam.");
        return false;
    }

    private boolean finishSecondExecrise() {
        System.out.println("Phaser:All student are finish the second exam.");
        System.out.println("Phaser:It's time to start third exam.");
        return false;
    }

    private boolean finishExam() {
        System.out.println("Phaser:All student are finish the exam.");
        System.out.println("Phaser:Thank you for your time.");
        return true;
    }

}
