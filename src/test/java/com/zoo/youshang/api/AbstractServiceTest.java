/**
 * 
 */
package com.zoo.youshang.api;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zoo.youshang.config.Configuration;
import com.zoo.youshang.config.LogConfigInitializer;


/**
 * @author sunpeng
 *
 */
public abstract class AbstractServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(AbstractServiceTest.class);

	protected ApplicationContext context;
	
	public <T> T getBean(Class<T> cls) {
		return (T) context.getBean(cls);
	}

	private static final String COMMON_COMPONENTS_CONFIG_LOCATION = "classpath:/youshang-component.xml";
	private static final String COMMON_COMPONENTS_FACTORY_KEY = "defaultCommonContext";

	public static ApplicationContext getComponentFactory() {

		BeanFactoryLocator locator = ContextSingletonBeanFactoryLocator.getInstance(COMMON_COMPONENTS_CONFIG_LOCATION);
		return (ApplicationContext) locator.useBeanFactory(COMMON_COMPONENTS_FACTORY_KEY).getFactory();

	}
	
	@Before
	public void setUp() throws Exception {
		Configuration.init();
		new LogConfigInitializer().init();
		ApplicationContext parentContext = AbstractServiceTest.getComponentFactory();
		context = new ClassPathXmlApplicationContext(getConfigLocations(), parentContext);
		init();
		logger.info("..finish setUp()");
	}
	
	protected void init() {
	}

	protected String[] getConfigLocations() {
		return new String[] { "classpath:/youshang-api.xml" };
	}

}
