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
}
