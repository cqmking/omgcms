package com.omgcms.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmsServletListener implements ServletContextListener {

	private Logger logger = LoggerFactory.getLogger(CmsServletListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		logger.debug("Initialize data."); 
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
