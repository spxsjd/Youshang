/**
 * 
 */
package com.zoo.youshang.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sunpeng
 * 
 */
public class TaskMock {

	private static final Logger logger = LoggerFactory
			.getLogger(TaskMock.class);

	public void execute() {
		logger.info("To do mock taks......");
	}
}
