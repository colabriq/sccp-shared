package com.colabriq.shared;

import org.apache.commons.configuration2.ex.ConfigurationException;

public class ConfigLoaderTest {
	public static void main(String[] args) throws ConfigurationException {
		var config = ConfigLoader.loadConfig(ConfigLoaderTest.class, "config-test.properties");
		
		boolean b1 = config.getBoolean("test.boolean");
		System.out.println(b1);
		
		int i1 = config.getInt("test.integer");
		System.out.println(i1);
		
		String s1 = config.getString("test.string");
		System.out.println(s1);
		
		String s2 = config.getString("unbound");
		System.out.println(s2);
		
		String s3 = config.getString("envfail");
		System.out.println(s3);
	}
}
