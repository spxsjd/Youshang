package com.zoo.youshang.cache;


import com.zoo.youshang.AbstractTest;

/**
 * @author chenquanzhao
 */
public abstract class AbstractCacherTest extends AbstractTest{

	protected String[] getConfigLocations() {
		return new String[] { "classpath:/youshang-cache.xml" };
	}

}
