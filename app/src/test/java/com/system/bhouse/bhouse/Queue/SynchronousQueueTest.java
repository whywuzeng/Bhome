package com.system.bhouse.bhouse.Queue;

import org.junit.Test;

import java.util.concurrent.SynchronousQueue;

/**
 * Created by wz on 2018-11-11.
 */

public class SynchronousQueueTest {

    private static final int QUEUESIZE= 1;
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
            synchronized (SynchronousQueueTest.this) {
            while (true)
            {
                if (isConsumer) {
                    System.out.println("消费元素:" + 1);
                    isConsumer = !isConsumer;
                    SynchronousQueueTest.this.notify();
                }else {
                    try {
                        SynchronousQueueTest.this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            }
        }
    }

    class Producer extends Thread{
        @Override
        public void run() {
            super.run();
            synchronized (SynchronousQueueTest.this) {
            while (true)
            {
                if (!isConsumer) {
                    System.out.println("生产元素int 0");
                    isConsumer = !isConsumer;
                    SynchronousQueueTest.this.notify();
                }else {
                    try {
                        SynchronousQueueTest.this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                }
            }
        }
    }
}
