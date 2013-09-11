/**
 * 
 */
package com.zoo.youshang.api;

import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;

import com.zoo.youshang.AbstractTest;



/**
 * @author sunpeng
 *
 */
public abstract class AbstractServiceTest extends AbstractTest{

	private static final String COMMON_COMPONENTS_CONFIG_LOCATION = "classpath:/youshang-component.xml";
	private static final String COMMON_COMPONENTS_FACTORY_KEY = "defaultCommonContext";

	@Override
	protected ApplicationContext getComponentFactory() {

		BeanFactoryLocator locator = ContextSingletonBeanFactoryLocator.getInstance(COMMON_COMPONENTS_CONFIG_LOCATION);
		return (ApplicationContext) locator.useBeanFactory(COMMON_COMPONENTS_FACTORY_KEY).getFactory();

	}
	
	protected String[] getConfigLocations() {
		
		return new String[] { "classpath:/youshang-api.xml" };
	}

}
