package com.rocky.thread.syncutil.exchange;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/*
 * 只支持两个线程之间交换数据，数据结构必须一样
 */
public class ExchangeTest {


    public static void main(String[] args) {
        List<String> buffer1 = new ArrayList<>();
        List<String> buffer2 = new ArrayList<>();

        Exchanger<List<String>> exchanger = new Exchanger<>();

        Producer producer = new Producer(buffer1, exchanger);
        Consumer consumer = new Consumer(buffer2, exchanger);

        new Thread(producer).start();
        new Thread(consumer).start();

    }

}
