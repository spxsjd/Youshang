/**
 * 
 */
package com.zoo.youshang.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zoo.youshang.persistence.TaskProfileMapper;

/**
 * @author sunpeng
 * 
 */
public class TaskExpirationProcessor {

	private static final Logger logger = LoggerFactory
			.getLogger(TaskExpirationProcessor.class);

	@Autowired
	private TaskProfileMapper taskProfileMapper;
	
	public void execute() {
		logger.info("Task to do expired tasks......");
		
	}
}
