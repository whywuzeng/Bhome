package com.system.bhouse.bhouse.Queue;

import org.junit.Test;

import java.util.concurrent.SynchronousQueue;

/**
 * Created by wz on 2018-11-11.
 */

public class SynchronousQueueTest2 {

    private SynchronousQueue<Integer> integers;
    private volatile boolean isConsumer = false;

    @Test
    public void BlockQueueTest(){
        integers = new SynchronousQueue<>();
        Consumer consumer = new Consumer();
        Producer producer = new Producer();
        consumer.start();
        producer.start();
    }

    class Consumer extends Thread{
        @Override
        public void run() {
            super.run();
            while (true)
            {
                if (isConsumer) {
                    try {
                        Integer take = integers.take();
                        System.out.println(take);
                        isConsumer=!isConsumer;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class Producer extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                if (!isConsumer) {
                    try {
                        isConsumer = !isConsumer;
                        integers.put(1);
                        System.out.println(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
