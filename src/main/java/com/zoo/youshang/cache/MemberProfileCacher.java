package com.zoo.youshang.cache;

public interface MemberProfileCacher {

	public abstract void saveAuthcode(String mobile, String authcode);

	public abstract String getAucthCode(String mobile);

	public abstract Long deleteAucthCode(final String mobile);

}