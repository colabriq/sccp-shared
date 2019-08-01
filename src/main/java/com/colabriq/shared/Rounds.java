package com.goodforgoodbusiness.shared;

import java.util.function.Function;

public class Rounds {
	public static <T> T apply(Function<T, T> fn, T input, int rounds) {
		var result = input;
		for (var i = 0; i < rounds; i++) result = fn.apply(result);
		return result;
	}
}
