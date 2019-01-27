package com.goodforgoodbusiness.shared;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class LogConfigurer {
	public static void init(Class<?> clazz) {
		try (var is = clazz.getClassLoader().getResourceAsStream("log4j.properties")) {
			Properties props = new Properties();
			props.load(is);
			
			PropertyConfigurator.configure(props);
		}
		catch (IOException e) {
			throw new RuntimeException("Unable to configure log4j");
		}
	}
}
