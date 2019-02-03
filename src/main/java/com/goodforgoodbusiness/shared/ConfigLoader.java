package com.goodforgoodbusiness.shared;

import static java.lang.System.getProperty;
import static java.lang.System.getenv;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.interpol.Lookup;

import com.google.common.collect.ImmutableMap;

public class ConfigLoader {
	// custom lookups that fail harder if values aren't found in env/sys
	private static final Map<String, Lookup> LOOKUPS = 
			ImmutableMap.<String, Lookup>builder()
				.put("env", k -> Optional.ofNullable(getenv(k)).orElseThrow( () -> new NoSuchElementException(k)) )
				.put("sys", k -> Optional.ofNullable(getProperty(k)).orElseThrow( () -> new NoSuchElementException(k)) )
				.build();
	
	public static Configuration loadConfig(Class<?> base, String name) throws ConfigurationException {
		var propsFileUrl = base.getClassLoader().getResource(name);
		if (propsFileUrl != null) {
			return new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                .configure(
                	new Parameters()
                		.fileBased()
                		.setPrefixLookups(LOOKUPS)
                		.setURL(propsFileUrl)
                )
                .getConfiguration()
            ;
		}
		else {
		    throw new ConfigurationException("Config file not found: " + name);
		}
	}
	
	public static Configuration loadConfig(String filename) throws ConfigurationException {
		return new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
            .configure(
            	new Parameters()
            		.fileBased()
            		.setPrefixLookups(LOOKUPS)
            		.setFileName(filename)
            )
            .getConfiguration()
        ;
	}
}
