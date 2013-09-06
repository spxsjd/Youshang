package com.zoo.youshang.persistence;

import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * @author chenquanzhao
 */
public abstract class AbstractMapperTest {
	private final GenericXmlApplicationContext context = new GenericXmlApplicationContext(getConfigLocations());

	@SuppressWarnings("unchecked")
	public <T> T getMapper(Class<?> cls) {
		return (T) context.getBean(cls);
	}

	private String[] getConfigLocations() {
		return new String[] { "classpath:/youshang-persistence.xml" };
	}

}
