package com.system.bhouse.bhouse.NewThread;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018-07-04.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.NewThread
 */

public class Consumer implements Runnable {

    private SynchronousQueue<String> mBlockingQueue;

    public static final int DEFAULT_RANGE_SLEEP=1000;

    private Object mObject;
    private boolean isWait=true;

    public Consumer(SynchronousQueue<String> mBlockingQueue) {
        this.mBlockingQueue = mBlockingQueue;
    }

    @Override
    public void run() {

        Random r= new Random();
        Service service = new Service();
        System.out.println("启动消费者线程");
        boolean isrunning=true;
        try {
            while (isrunning)
            {
                service.sysNotifyMethod(mBlockingQueue);
                String data = mBlockingQueue.poll(1, TimeUnit.SECONDS);
                if (null!=data)
                {
//                    System.out.println("拿到数据：" + data);
                    System.out.println("正在消费数据：" + data);
                    Thread.sleep(r.nextInt(DEFAULT_RANGE_SLEEP));
                }else {
                    isrunning=false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("消费者结束");
        }
    }
}
