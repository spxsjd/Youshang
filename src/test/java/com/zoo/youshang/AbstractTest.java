/**
 * 
 */
package com.zoo.youshang;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zoo.youshang.config.Configuration;
import com.zoo.youshang.config.LogConfigInitializer;

/**
 * @author sunpeng
 * 
 */
public abstract class AbstractTest {
	private static final Logger logger = LoggerFactory
			.getLogger(AbstractTest.class);

	protected ApplicationContext context;

	@Before
	public void setUp() throws Exception {
		Configuration.init();
		new LogConfigInitializer().init();
		ApplicationContext parentContext = this.getComponentFactory();
		context = (parentContext == null ? new ClassPathXmlApplicationContext(
				getConfigLocations()) : new ClassPathXmlApplicationContext(
				getConfigLocations(), parentContext));
		logger.info("...finish setUp()...");
	}

	protected ApplicationContext getComponentFactory() {
		return null;
	}

	protected abstract String[] getConfigLocations();

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<?> cls) {
		return (T) context.getBean(cls);
	}

	public Object getBean(String name) {
		return context.getBean(name);
	}
	
	public ApplicationContext getApplicationContext(){
		return context;
	}
}
