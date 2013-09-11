/**
 * 
 */
package com.zoo.youshang.config;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sunpeng.peng
 * 
 */
public class ConfigurationInitializer implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationInitializer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		List<ShutdownHooker> shutdownHookers = SystemHookerRegistry.getShutdownHookers();
		for (ShutdownHooker hooker : shutdownHookers) {
			try {
				hooker.onShutdown();
			} catch (Exception e) {
				logger.error("Handle the shutdown hook error: " + e.getMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("Initalize the system config......");
		ServletContext servletContext = event.getServletContext();
		String param = servletContext.getInitParameter("initializers");
		try {
			Configuration.init();
			String[] initializers = param.split(",");
			for (String className : initializers) {
				Class<?> clazz = Class.forName(className);
				Initializer initializer = (Initializer) clazz.newInstance();
				initializer.init();
			}
		} catch (Exception e) {
			logger.error("Configureation error.", e);
			throw new RuntimeException("Configureation error.", e);
		}

	}

}
