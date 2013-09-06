/**
 * 
 */
package com.zoo.youshang.persistence;

import org.junit.Test;

import com.zoo.youshang.config.ConfigurationItem;
import com.zoo.youshang.entity.MemberProfile;

/**
 * @author sunpeng
 * 
 */
public class MemberProfileMapperTest extends AbstractMapperTest {

	@Test
	public void test() {

		MemberProfileMapper mapper = super.getMapper(MemberProfileMapper.class);
		System.out.println("-------" + mapper.getClass());
		System.out.println("-------" + ConfigurationItem.DataSource.getConfigFullPath());
		MemberProfile member = mapper.selectByPrimaryKey(1l);
		System.out.println("-------" + (member == null));
	}

}
