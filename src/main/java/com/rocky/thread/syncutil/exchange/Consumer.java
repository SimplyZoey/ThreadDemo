package com.rocky.thread.syncutil.exchange;

import java.util.List;
import java.util.concurrent.Exchanger;

public class Consumer implements Runnable {

    private List<String> buffer;
    private final Exchanger<List<String>> exchange;

    public Consumer(List<String> buffer, Exchanger<List<String>> exchange) {
        this.buffer = buffer;
        this.exchange = exchange;
    }

    @Override
    public void run() {
        int cycle = 1;
        for (int i = 0; i < 2; i++) {
            System.out.println("Consumer:Cycle " + cycle);
            try {
                buffer = exchange.exchange(buffer);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            for (int j = 0; j < 10; j++) {
                String message = buffer.get(0);
                System.out.println("Consumer:" + message);
                buffer.remove(0);
            }

            System.out.println("Consumer:" + buffer.size());
            cycle++;
        }

    }

}
