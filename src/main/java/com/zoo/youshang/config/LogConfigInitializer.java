/**
 * 
 */
package com.zoo.youshang.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * @author sunpeng.peng
 * 
 */
public class LogConfigInitializer implements Initializer {

	private static final Logger logger = LoggerFactory.getLogger(LogConfigInitializer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snda.cloud.api.Initializer#init()
	 */
	@Override
	public void init() {
		String logConfigFileName = ConfigurationItem.Log.getConfigFullPath();
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		loggerContext.reset();
		JoranConfigurator joranConfigurator = new JoranConfigurator();
		joranConfigurator.setContext(loggerContext);
		try {
			joranConfigurator.doConfigure(logConfigFileName);
			StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext);
		} catch (JoranException e) {
			throw new RuntimeException("Configuration Log error.", e);
		}
		logger.debug("loaded slf4j configure file from {}", logConfigFileName);

	}

}
