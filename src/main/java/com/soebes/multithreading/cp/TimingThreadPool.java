package com.soebes.multithreading.cp;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

/**
 * @author Karl Heinz Marbaise
 */
public class TimingThreadPool extends ThreadPoolExecutor {
    private final Logger LOGGER = Logger.getLogger(TimingThreadPool.class);

    private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();
    
    /**
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     */
    public TimingThreadPool(int corePoolSize, int maximumPoolSize,
	    long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
	super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    protected void beforeExecute(Thread t, Runnable r) {
	super.beforeExecute(t, r);
	LOGGER.info(String.format("Thread %s: start %s", t, r));
	startTime.set(System.nanoTime());
    }

    protected void afterExecute(Runnable r, Throwable t) {
	try {
	    long endTime = System.nanoTime();
	    long taskTime = endTime - startTime.get();
	    numTasks.incrementAndGet();
	    totalTime.addAndGet(taskTime);
	    LOGGER.info(String.format("Thread %s: end %s, time=%dns", t, r,
		    taskTime));
	} finally {
	    super.afterExecute(r, t);
	}
    }

    protected void terminated() {
	try {
	    LOGGER.info(String.format("Terminated: avg time=%dns", totalTime.get()
		    / numTasks.get()));
	} finally {
	    super.terminated();
	}
    }
}