package com.omgcms.prop.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.omgcms.admin.util.Config;


public class SystemConfigPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	
    @Override
    protected void processProperties(
            ConfigurableListableBeanFactory beanFactoryToProcess,
            Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        
        logger.debug("Load SystemConfigPropertyPlaceholderConfigurer from property file.");
        
        Map<String,Object> propertiesMap = new HashMap<String, Object>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            propertiesMap.put(keyStr, value);
        }
        
        Config.initConfig(propertiesMap);
    }
    
}