package com.system.bhouse.bhouse.Queue;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by wz on 2018-11-11.
 */

public class BlockQueueTest {

    private static final int QUEUESIZE= 1;
    private ArrayBlockingQueue<Integer> integers;

    @Test
    public void BlockQueueTest(){
        integers = new ArrayBlockingQueue<>(QUEUESIZE);
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
                try {
                    Integer take = integers.take();
                    System.out.println("消费元素:"+take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Producer extends Thread{
        @Override
        public void run() {
            super.run();
            while (true)
            {
                try {
                    integers.put(1);
                    System.out.println("生产元素int 1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
