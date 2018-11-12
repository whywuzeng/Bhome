package com.system.bhouse.bhouse.Queue;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.internal.Util;

/**
 * Created by wz on 2018-11-11.
 */

public class CacheTheadPool {

    private static ThreadPoolExecutor executorService;

    @Test
    public void cacheTheadPool(){
//        for (int i=0;i<100;i++) {
//            newCachedThreadPool().execute(new AsyncCall("thread"+i));
//        }

        newCachedThreadPool().execute(new AsyncCall("thread"+1));
        newCachedThreadPool().execute(new AsyncCall("thread"+2));
        newCachedThreadPool().execute(new AsyncCall("thread"+3));

        System.out.println("先开3个，按书上讲会有3个是新建线程");
        System.out.println("线程池核心："+executorService.getCorePoolSize());
        System.out.println("线程池数目:"+executorService.getPoolSize());
        System.out.println("队列任务数目:"+executorService.getQueue().size());

        //让上面的用完
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        newCachedThreadPool().execute(new AsyncCall("thread"+4));
        newCachedThreadPool().execute(new AsyncCall("thread"+5));
        newCachedThreadPool().execute(new AsyncCall("thread"+6));

        System.out.println("再开3个，按书上讲会有3个是,看看是不是复用");
        System.out.println("线程池核心："+executorService.getCorePoolSize());
        System.out.println("线程池数目:"+executorService.getPoolSize());
        System.out.println("队列任务数目:"+executorService.getQueue().size());

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        newCachedThreadPool().execute(new AsyncCallSleep("thread"+7));
        newCachedThreadPool().execute(new AsyncCallSleep("thread"+8));
        newCachedThreadPool().execute(new AsyncCallSleep("thread"+9));

        System.out.println("再开3个，按书上讲会有3个是,看看是不是新建");
        System.out.println("线程池核心："+executorService.getCorePoolSize());
        System.out.println("线程池数目:"+executorService.getPoolSize());
        System.out.println("队列任务数目:"+executorService.getQueue().size());
    }

    /**
     * 建立的都是 用户线程  优先级比较高.
     * @return
     */
    public synchronized  ExecutorService newCachedThreadPool(){
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, 6, 5, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp Dispatcher", false));
        }
        return executorService;
    }


    final class AsyncCall extends NamedRunnable {

        private AsyncCall(Object... arg){
            super("OkHttp %s",arg);
        }

        @Override
        protected void execute() {
            String name = Thread.currentThread().getName();
            System.out.println("当前处理的线程名是:"+name);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    final class AsyncCallSleep extends NamedRunnable {

        private AsyncCallSleep(Object... arg){
            super("OkHttpSleep %s",arg);
        }

        @Override
        protected void execute() {
            String name = Thread.currentThread().getName();
            System.out.println("当前处理的随眠线程名是:"+name);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract class NamedRunnable implements Runnable {
        protected final String name;

        public NamedRunnable(String format, Object... args) {
            this.name = String.format(format, args);
        }

        @Override public final void run() {
            String oldName = Thread.currentThread().getName();
            Thread.currentThread().setName(name);
            try {
                execute();
            } finally {
                Thread.currentThread().setName(oldName);
            }
        }

        protected abstract void execute();
    }
}
