package com.goodforgoodbusiness.shared;

import java.util.concurrent.ExecutionException;

public class Retry {
	public interface RetryableFunction<T> {
		public T apply() throws Exception;
	}

	
	/**
	 * Tries a function several times, repeating if it throws an Exception.
	 * If the final try still produces an Exception, throw an {@link ExecutionException}.
	 * You should only use this for repeatable functions that have no side effects.
	 */
	public static <T> T retry(RetryableFunction<T> fn, int tries) throws ExecutionException {
		for (var x = tries; x > 1; x--) {
			try {
				return fn.apply();
			}
			catch (Exception e) {
				continue;
			}
		}
		
		try {
			return fn.apply();
		}
		catch (Exception e) {
			throw new ExecutionException(e);
		}
	}
	
	/** Shortcut of retry(..., 2) **/
	public static <T> T twice(RetryableFunction<T> fn) throws ExecutionException {
		return retry(fn, 2);
	}
	
	/** Shortcut of retry(..., 3) **/
	public static <T> T thrice(RetryableFunction<T> fn) throws ExecutionException {
		return retry(fn, 2);
	}
	
	public static void main(String[] args) throws Exception {
		Retry.twice(() -> { throw new Exception("foo"); });
	}
}
