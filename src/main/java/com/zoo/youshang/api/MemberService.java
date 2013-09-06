/**
 * 
 */
package com.zoo.youshang.api;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zoo.youshang.api.data.Codes;
import com.zoo.youshang.api.data.MemberRegisterRequest;
import com.zoo.youshang.api.error.ServiceAssert;
import com.zoo.youshang.entity.MemberProfile;
import com.zoo.youshang.persistence.MemberProfileMapper;
import com.zoo.youshang.util.MD5Util;

/**
 * @author sunpeng
 * 
 */
@Path("/member")
public class MemberService {

	private static final Logger logger = LoggerFactory
			.getLogger(MemberService.class);
	@Autowired
	private MemberProfileMapper memberProfileMapper;

	@POST
	public MemberProfile register(MemberRegisterRequest request) {
		Date current = new Date();
		MemberProfile profile = new MemberProfile();
		profile.setAvatarPath("");
		profile.setCreateTime(current);
		profile.setLastLoginTime(current);
		profile.setMobile(request.getMobile());
		profile.setName(request.getName());
		profile.setPassword(MD5Util.encode(request.getPassword()));
		profile.setUsername(request.getUsername());
		profile.setWeibo(request.getMobile());
		memberProfileMapper.insert(profile);
		return profile;
	}

	@GET
	public MemberProfile query(@QueryParam("id") Long id) {
		logger.debug("Query member profile......");
		MemberProfile profile = memberProfileMapper.selectByPrimaryKey(id);
		ServiceAssert.notNull(profile, Codes.MemberNotFound);
		return profile;
	}

	@PUT
	@Path("/login")
	public void login(@QueryParam("mobile") String mobile,
			@QueryParam("password") String password) {

	}

	@PUT
	@Path("/logout")
	public void logout(@QueryParam("id") String id) {

	}

	@POST
	@Path("/avatar")
	public void modifyAvatar() {

	}

	@GET
	@Path("/mobile")
	public void prepareMobile(@QueryParam("mobile") String mobile) {

	}

	@PUT
	@Path("/mobile")
	public void verifyMobile(@QueryParam("mobile") String mobile,
			@QueryParam("code") String authCode) {

	}

	@POST
	@Path("/mobile")
	public void bindMobile(@QueryParam("id") Long id,
			@QueryParam("mobile") String mobile,
			@QueryParam("code") String authCode) {

	}

}
