package com.omgcms.admin.util;

import java.util.Map;

public class Config {
	
	private static Map<String, Object> configMap;

	private static Config config;

	private Config() {
	}

	public static Config getInstance() {

		if (config == null) {
			config = new Config();
		}

		return config;
	}

	public static String get(String key) {

		return (String) configMap.get(key);
	}
	
	public static void initConfig(Map<String, Object> propertiesMap) {
		configMap = propertiesMap;
	}
	
	public static Map<String, Object> getConfigMap() {
		return configMap;
	}
	
}
