package com.system.bhouse.bhouse.NewThread;

/**
 * Created by Administrator on 2018-07-04.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.NewThread
 */

public class Service {

    public void testMethod(Object lock)
    {
        synchronized (lock)
        {
            try {
                System.out.println("begin wait Name:"+Thread.currentThread().getName());
                lock.wait();
                System.out.println("close wait Name "+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();

            } finally {

            }
        }
    }

    public void sysNotifyMethod(Object lock){

        synchronized (lock) {
            System.out.println("begin notify() ThreadName="
                    + Thread.currentThread().getName() + " time="
                    + System.currentTimeMillis());
            lock.notify();

//                Thread.sleep(5000);

            System.out.println("  end notify() ThreadName="
                    + Thread.currentThread().getName() + " time="
                    + System.currentTimeMillis());
        }
    }
}
