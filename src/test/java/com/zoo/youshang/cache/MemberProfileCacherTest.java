/**
 * 
 */
package com.zoo.youshang.cache;

import org.junit.Test;

/**
 * @author sunpeng
 * 
 */
public class MemberProfileCacherTest extends AbstractCacherTest {

	@Test
	public void test() {

		System.out.println("-------" + super.getBean("memberProfileCacher"));
		MemberProfileCacher memberProfileCacher = super
				.getBean(MemberProfileCacher.class);
		System.out.println("-------" + memberProfileCacher.getClass());
		memberProfileCacher.saveAuthcode("13454480552", "899232");
		System.out.println("-------");
	}

}
