package com.zoo.youshang.api;

import java.util.Arrays;

import org.junit.Test;

import com.zoo.youshang.entity.MemberProfile;


public class MemberServiceTest extends AbstractServiceTest {

	@Test
	public void testQuery() {
		System.out.println("-------" + Arrays.toString(super.getApplicationContext().getBeanDefinitionNames()));

		MemberService service = super.getBean(MemberService.class);
		MemberProfile member = service.query(1l);
		System.out.println("==========" + (member == null));
	}

}
