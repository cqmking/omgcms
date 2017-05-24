package com.omgcms.prop.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class NavigationMenuPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private Logger logger = LoggerFactory.getLogger(NavigationMenuPropertyPlaceholderConfigurer.class);

	private static Map<String, Object> ctxPropertiesMap;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		
		logger.debug("Load admin navigations and menus from property file.");
		
		super.processProperties(beanFactoryToProcess, props);
		ctxPropertiesMap = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
		
	}

	public static Object getContextProperty(String name) {
		return ctxPropertiesMap.get(name);

	}

	public static Map<String, Object> getCtxPropertiesMap() {
		return ctxPropertiesMap;
	}

}
