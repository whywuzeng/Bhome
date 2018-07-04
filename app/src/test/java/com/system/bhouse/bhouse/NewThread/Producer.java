package com.system.bhouse.bhouse.NewThread;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2018-07-04.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.NewThread
 */

public class Producer implements Runnable{

    private SynchronousQueue queue;

    public volatile boolean isRunning=true;

    private static AtomicInteger count=new AtomicInteger();

    public static final int DEFAULT_RANGE_FOR_SLEEP=1000;

    private Object mObject;
    private boolean isWait=true;


    public Producer(SynchronousQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        String data=null;
        Service service = new Service();

        System.out.println("启动生产者线程");
            try {
                while (isRunning) {
                    service.testMethod(queue);
                    Thread.sleep(DEFAULT_RANGE_FOR_SLEEP);

                    //生产数据
                    data = "data" + count.incrementAndGet();

                    System.out.println("生产者生产数据:" + data);

                    if (!queue.offer(data, 1, TimeUnit.SECONDS)) {

                        System.out.println("加入数据失败");

                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("生产线程结束");
            }

    }

    public void stop() {
        isRunning = false;
    }

}
