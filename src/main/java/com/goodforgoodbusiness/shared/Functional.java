package com.goodforgoodbusiness.shared;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class Functional {
	public static <O, T> Function<O, Optional<T>> castOptional(Class<T> clazz) {
		return o -> Optional.of(o)
			.filter(clazz::isInstance)
			.map(clazz::cast)
		;
	}
	
	public static <O, T> Function<O, Stream<T>> castStream(Class<T> clazz) {
		return o -> Optional.of(o)
			.filter(clazz::isInstance)
			.map(clazz::cast)
			.stream()
		;
	}
	
//	public static interface FunctionThatThrows<T, R>{
//		R apply(T t) throws Exception;
//	}
//	
//	public static class TrappedException extends RuntimeException {
//		public TrappedException(Exception e) {
//			super(e);
//		}
//	}
//
//	public static <T, R> Function<T, R> trapEx(FunctionThatThrows<T, R> in) {
//		return x -> {
//			try {
//				return in.apply(x);
//			}
//			catch (Exception e) {
//				throw new TrappedException(e);
//			}
//		};
//	}
}
