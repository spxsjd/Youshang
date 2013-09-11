package com.zoo.youshang.persistence;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.zoo.youshang.AbstractTest;

/**
 * @author chenquanzhao
 */
public abstract class AbstractMapperTest extends AbstractTest{

	protected String[] getConfigLocations() {
		return new String[] { "classpath:/youshang-persistence.xml" };
	}

}
