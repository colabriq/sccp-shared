package com.colabriq.shared.executor;

import java.util.concurrent.ExecutorService;

import com.colabriq.shared.executor.PrioritizedExecutor;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * Create the {@link ExecutorService} for queries.
 * This is a priority-queue backed threadpool that can assess which tasks to run first.
 */
@Singleton
public class ExecutorProvider implements Provider<PrioritizedExecutor> {
	private final int poolSize;
	
	@Inject
	public ExecutorProvider(@Named("threadpool.size") int threadPoolSize) {
		this.poolSize = threadPoolSize;
	}

	@Override @Singleton
	public PrioritizedExecutor get() {
		return new PrioritizedExecutor(poolSize);
	}
}
