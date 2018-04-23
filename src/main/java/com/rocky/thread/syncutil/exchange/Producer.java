package com.rocky.thread.syncutil.exchange;

import java.util.List;
import java.util.concurrent.Exchanger;

public class Producer implements Runnable {

    private List<String> buffer;
    private final Exchanger<List<String>> exchange;

    public Producer(List<String> buffer, Exchanger<List<String>> exchange) {
        this.buffer = buffer;
        this.exchange = exchange;
    }

    @Override
    public void run() {
        int cycle = 1;
        for (int i = 0; i < 2; i++) {
            System.out.println("Producer:Cycle " + cycle);
            for (int j = 0; j < 10; j++) {
                String message = "Event " + ((i * 10) + j);
                System.out.println("Producer:" + message);
                buffer.add(message);
            }
            try {
                //阻塞
                buffer = exchange.exchange(buffer);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("Producer:" + buffer.size());
            cycle++;
        }

    }

}
