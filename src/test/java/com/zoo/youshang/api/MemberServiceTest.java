package com.zoo.youshang.api;

import org.junit.Test;

import com.zoo.youshang.entity.MemberProfile;


public class MemberServiceTest extends AbstractServiceTest {

	@Test
	public void testQuery() {

		MemberService service = super.getBean(MemberService.class);
		MemberProfile member = service.query(1l);
		System.out.println("==========" + (member == null));
	}

}
