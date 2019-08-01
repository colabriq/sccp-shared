package com.goodforgoodbusiness.shared;

import com.google.inject.Injector;

/**
 * Convenience methods for Guice
 */
public class GuiceUtil {
	public static <T> T o(Injector injector, Class<T> clazz) {
		return injector.getBinding(clazz).getProvider().get();
	}
}
