package com.system.bhouse.bhouse.Instance;

/**
 * Created by Administrator on 2018-05-15.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Instance
 */

public class Instance {
    private Instance() {
    }

    //县城不安全
    private static Instance mInstance1;

    public static Instance getmInstance() {
        if (mInstance1 == null) {
            mInstance1 = new Instance();
        }
        return mInstance1;
    }

    //这个单例 一直存在  效率很快。这个静态 比县城 还要快
    private static Instance mInstance = new Instance();

    public static Instance getInstance() {
        return mInstance;
    }

    //每次都要等  在多线程的时候,有了 minstance了也要等待
    public synchronized static Instance getInstance1() {
        if (mInstance == null) {
            mInstance = new Instance();
        }
        return mInstance;
    }

    private volatile static Instance mInstance2;

    public static Instance getInstance2() {
        //第一次的时候进入
        if (mInstance2 == null) {
            //锁住这个代码块  这里可能有几个线程进来了。
            synchronized (Instance.class) {
                if (mInstance2 == null) {
                    mInstance2 = new Instance();
                }
            }
        }
        return mInstance2;
    }

}
