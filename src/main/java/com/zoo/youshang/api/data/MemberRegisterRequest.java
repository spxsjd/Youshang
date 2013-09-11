/**
 * 
 */
package com.zoo.youshang.api.data;

import javax.ws.rs.QueryParam;

import com.google.gson.Gson;

/**
 * @author sunpeng
 * 
 */
public class MemberRegisterRequest {

	@QueryParam("mobile")
	private String mobile;
	@QueryParam("password")
	private String password;
	@QueryParam("weibo")
	private String weibo;
	@QueryParam("username")
	private String username;
	@QueryParam("name")
	private String name;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return (new Gson()).toJson(this);
	}

	
}
