package com.system.bhouse.bhouse.Queue;

import org.junit.Test;

import java.util.PriorityQueue;

/**
 * Created by wz on 2018-11-11.
 */

public class QueueTest  {

    private static final int queueSize=10;
    private PriorityQueue<Object> queue;

    @Test
    public void queueTest(){
        /**
         * 观其名字前半部分的单词Priority是优先的意思，实际上这个队列就是具有“优先级”。既然具有优先级的特性，那么就得有个前后排序的“规则”。所以其接受的类需要实现Comparable 接口。
         */

         queue = new PriorityQueue<>(queueSize);
        Consumer consumer = new Consumer();
        Producer producer = new Producer();
        consumer.start();
        producer.start();
    }

    class Consumer extends Thread{
        @Override
        public void run() {
            super.run();
            //要消费
            while (true)
            {
                synchronized (queue){
                    while (queue.size()==0)
                    {
                        try {
                            //锁等待
                            queue.wait();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                    System.out.println("消费元素:"+queue.poll());
                    queue.notify();
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
                synchronized (queue){
                    while (queue.size()==queueSize)
                    {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                    boolean offer = queue.offer(1);
                    System.out.println("生产元素int 1"+offer);
                    queue.notify();
                }
            }
        }
    }
}
