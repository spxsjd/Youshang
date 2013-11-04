/**
 * 
 */
package com.zoo.youshang.task.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zoo.youshang.config.ConfigurationItem;
import com.zoo.youshang.config.Initializer;
import com.zoo.youshang.config.LogConfigInitializer;

/**
 * @author sunpeng.peng
 * 
 */
public class TaskLauncher implements Initializer {

	private static final Logger logger = LoggerFactory
			.getLogger(TaskLauncher.class);
	private static final String component_config_file = "classpath:/youshang-component.xml";
	private static final String component_context_name = "defaultCommonContext";
	private static final String task_config_file = "classpath:/youshang-task.xml";

	@Override
	public void init() {
		String taskEnabled = ConfigurationItem.Task.getConfigurationValue(
				"task.enabled", Boolean.TRUE.toString());
		if (!Boolean.parseBoolean(taskEnabled)) {
			logger.info("Don't start task hanlde......");
			return;
		}
		launch();
	}

	protected void launch() {
		logger.info("Start task......");
		ApplicationContext parentContext = TaskLauncher.getComponentFactory();
		new ClassPathXmlApplicationContext(new String[] { task_config_file },
				parentContext);
	}

	protected static ApplicationContext getComponentFactory() {

		BeanFactoryLocator locator = ContextSingletonBeanFactoryLocator
				.getInstance(component_config_file);
		return (ApplicationContext) locator.useBeanFactory(
				component_context_name).getFactory();

	}

	public static void main(String[] args) {
		LogConfigInitializer logConfig = new LogConfigInitializer();
		logConfig.init();
		TaskLauncher lanucher = new TaskLauncher();
		lanucher.launch();

	}
}
