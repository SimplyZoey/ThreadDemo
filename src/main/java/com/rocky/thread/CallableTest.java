package com.rocky.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author: shtian
 * @Description:
 * @Date: Create in 2018/4/10 20:15
 */
public class CallableTest implements Callable<Map<String, String>> {
    @Override
    public Map call() throws Exception {
        Map<String, String> result = new HashMap<>();
        result.put("key", "value");
        Thread.sleep(3000);
        return result;
    }

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Future<Map<String, String>> future = pool.submit(new CallableTest());
        System.out.println("is done : " + future.isDone());
//        future.cancel(true);
        System.out.println("is cancelled:" + future.isCancelled());
        Map<String,String> result = future.get();//会一直阻塞，直到线程运行结束
        System.out.println(result.get("key"));
        pool.shutdown();
    }
}
