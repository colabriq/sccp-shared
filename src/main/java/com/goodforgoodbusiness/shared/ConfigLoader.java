package com.goodforgoodbusiness.shared;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class ConfigLoader {
	public static Configuration loadConfig(Class<?> base, String name) throws ConfigurationException {
		var configHelper = new Configurations();
		var propsFileURL = base.getClassLoader().getResource(name);
		
		if (propsFileURL != null) {
		    return configHelper.properties(propsFileURL);
		}
		else {
		    throw new ConfigurationException("Config file not found");
		}
	}
}
