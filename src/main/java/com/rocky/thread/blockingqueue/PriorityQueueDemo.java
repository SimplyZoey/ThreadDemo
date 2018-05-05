package com.rocky.thread.blockingqueue;

import java.util.PriorityQueue;

/**
 * @Author: shtian
 * @Description:
 * @Date: Create in 2018/5/5 17:46
 */
public class PriorityQueueDemo {

    public static void main(String args[]) {
        // create priority queue
        PriorityQueue<Integer> prq = new PriorityQueue<Integer>();

        // insert values in the queue
        for (int i = 3; i < 10; i++) {
            prq.add(new Integer(i));
        }

        System.out.println("Initial priority queue values are: " + prq);
        for (int i = 0; i < 7; i++) {
            // get the head from the queue
            Integer head = prq.poll();

            System.out.println("Head of the queue is: " + head);

            System.out.println("Priority queue values after poll: " + prq);
        }
    }
}
