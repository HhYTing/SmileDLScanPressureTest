package com.telpo.wxpay.app.api.alipayapi.api;


/**
 * Created by bruce on 2017/12/25.
 */
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池代理类
 */
public class ThreadPoolProxy {

    ThreadPoolExecutor mThreadPoolExecutor;

    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    private ThreadPoolExecutor initExecutor() {
        if (mThreadPoolExecutor == null) {
            synchronized (ThreadPoolProxy.class) {
                if (mThreadPoolExecutor == null) {

                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
                    LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();

                    mThreadPoolExecutor = new ThreadPoolExecutor(
                        corePoolSize,//核心线程数
                        maximumPoolSize,//最大线程数
                        keepAliveTime,//保持时间
                        unit,//保持时间对应的单位
                        workQueue,
                        threadFactory,//线程工厂
                        handler);//异常捕获器
                }
            }
        }

        return mThreadPoolExecutor;
    }

    /**
     * 执行任务
     */
    public void executeTask(Runnable r) {
        initExecutor();
        mThreadPoolExecutor.execute(r);
    }

    /**
     * 提交任务
     */
    public Future<?> commitTask(Runnable r) {
        initExecutor();
        return mThreadPoolExecutor.submit(r);
    }

    /**
     * 删除任务
     */
    public void removeTask(Runnable r) {
        initExecutor();
        mThreadPoolExecutor.remove(r);
    }

}
