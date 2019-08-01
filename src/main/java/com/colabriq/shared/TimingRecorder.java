package com.colabriq.shared;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

/**
 * Records time taken for various types of operations
 */
public class TimingRecorder {
	private static final Logger log = Logger.getLogger(TimingRecorder.class);
	
	public static enum TimingCategory {
		DHT_ROUTE_SUBMIT,
		DHT_ROUTE_SEARCH,
		DHT_PUBLISH_WARP,
		DHT_PUBLISH_WEFT,
		DHT_PUBLISH,
		DHT_SEARCH,
		
		CONTAINER_FETCH,
		
		RDF_IMPORTING,
		RDF_QUERYING,
		RDF_UPDATING,
		RDF_DATABASE,
		
		RDF_REASONING, 
		
		KPABE_ENCRYPT,
		KPABE_DECRYPT,
		
		SYMMETRIC_ENCRYPT,
		SYMMETRIC_DECRYPT,
		
		SIGNING,
		
		DATABASE,
	}
	
	public static class Timer implements AutoCloseable {
		private final TimingCategory category;
		private final long started;
		
		private Timer(TimingCategory category) {
			this.category = category;
			this.started = System.currentTimeMillis();
		}
		
		@Override
		public void close() {
			var elapsed = System.currentTimeMillis() - started;
			timings.computeIfAbsent(category, k -> new AtomicLong());
			timings.get(category).addAndGet(elapsed);
		}
	}
	
	private static final ConcurrentHashMap<TimingCategory, AtomicLong> timings = new ConcurrentHashMap<>();
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private static final Runnable dump = () -> {
		log.info("----------------------------------------");
		
		for (var c : TimingCategory.values()) {
			var total = Optional.ofNullable(timings.get(c)).map(AtomicLong::get).orElse(0l);
			log.info("TIMINGS: " + c.toString() + " = " + total + "ms");
		}
		
		log.info("----------------------------------------");
	};
	
	public static void startLogging() {
		// the first time the class this is touched
		// it will schedule up & start to dump
		scheduler.scheduleAtFixedRate(dump, 30, 15, SECONDS);
	}
	
	/**
	 * Get a new stopwatch for a particular category
	 */
	public static Timer timer(TimingCategory category) {
		return new Timer(category);
	}
	
	private TimingRecorder() {
	}
}
