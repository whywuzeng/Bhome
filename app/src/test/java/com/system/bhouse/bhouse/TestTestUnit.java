package com.system.bhouse.bhouse;

import com.system.bhouse.bhouse.NewThread.Consumer;
import com.system.bhouse.bhouse.NewThread.Producer;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Administrator on 2018-07-02.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse
 */

public class TestTestUnit {
    @Test
    public void ste() throws InterruptedException {

//        EchoServer echoServer = new EchoServer(7789);
//        echoServer.run();
//
//        ScoketClient scoketClient = new ScoketClient(null);

        SynchronousQueue<String> strings = new SynchronousQueue<String>();


        Producer producer = new Producer(strings);
        Producer producer1 = new Producer(strings);
        Producer producer2 = new Producer(strings);

        Consumer consumer = new Consumer(strings);


        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(producer);
        executorService.execute(producer1);
        executorService.execute(producer2);

        executorService.execute(consumer);

        Thread.sleep(3*1000);
        producer.stop();
        producer1.stop();
        producer2.stop();

        Thread.sleep(4000);

        executorService.shutdown();

    }


}
