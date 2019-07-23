package com.goodforgoodbusiness.shared.executor;

import static com.goodforgoodbusiness.shared.executor.PrioritizedTask.Priority.NORMAL;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

/**
 * A {@link ThreadPoolExecutor} using a priority queue
 * Also adds some other functionality such as error logging and safe stop.
 * @author ijmad
 *
 */
public class PrioritizedExecutor extends ThreadPoolExecutor {
	private static final Logger log = Logger.getLogger(PrioritizedExecutor.class);
	
	/**
	 * Assess the priority of Runnable tasks
	 */
	private static int getPriority(Runnable r) {
		return (r instanceof PrioritizedTask ? ((PrioritizedTask)r).getPriority() : NORMAL).ordinal;
	}
	
	private static final int INITIAL_CAPACITY = 10;
	
	// record runtimes for tasks
	private final ConcurrentHashMap<Class<? extends Runnable>, AtomicLong> totalTaskTimings = new ConcurrentHashMap<>();
	private final ThreadLocal<Long> currentTaskStarted = new ThreadLocal<>();
	
	public PrioritizedExecutor( int poolSize) {
		// use a priority queue to decide the order of task execution
		super(
			poolSize,
			poolSize,
			1, MINUTES,
			new PriorityBlockingQueue<>(
				INITIAL_CAPACITY,
				(a, b) -> getPriority(b) - getPriority(a)
			)
		);
	}
	
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		log.info("ExecutorService queue size = " + getQueue().size());
		currentTaskStarted.set(System.currentTimeMillis());
		super.beforeExecute(t, r);
	}
	
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		
		if (currentTaskStarted.get() != null) {
			long elapsed = System.currentTimeMillis() - currentTaskStarted.get();
			currentTaskStarted.remove();
			
			totalTaskTimings.computeIfAbsent(r.getClass(), c -> new AtomicLong(0));
			totalTaskTimings.get(r.getClass()).addAndGet(elapsed);
		}
		
		var cause = t;
        if (t == null && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get();
                }
            }
            catch (CancellationException ce) {
                cause = ce;
            }
            catch (ExecutionException ee) {
                cause = ee.getCause();
            }
            catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        
        if (cause != null) {
            log.error("Error from task", cause);
        }
        
        log.info("ExecutorService queue size = " + getQueue().size());
	}
	
	public void safeStop() {
		shutdown();
		
		// flush + await safe stop 
		while (!isTerminated()) {
			try {
				log.info("Awaiting ExecutorService termination...");
				awaitTermination(1, TimeUnit.SECONDS);
			}
			catch (InterruptedException e) {
			}
		}
		
		log.info("ExecutorService has terminated");
	}
}
