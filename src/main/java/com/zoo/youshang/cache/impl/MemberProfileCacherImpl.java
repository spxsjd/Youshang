/**
 * 
 */
package com.zoo.youshang.cache.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.zoo.youshang.cache.MemberProfileCacher;

/**
 * @author sunpeng
 * 
 */
@Repository("memberProfileCacher")
public class MemberProfileCacherImpl implements MemberProfileCacher {
	private static final String USER_MOBILE_KEY_PREFIX = "user.mobile.";
	private static final Long USER_MOBILE_KEY_PERIOD = 120l;
	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zoo.youshang.cache.MemberProfileCacher#saveAuthcode(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void saveAuthcode(final String mobile, final String authcode) {
		redisTemplate.execute(new RedisCallback<Void>() {
			@Override
			public Void doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.setEx(
						redisTemplate.getStringSerializer().serialize(
								USER_MOBILE_KEY_PREFIX + mobile),
						USER_MOBILE_KEY_PERIOD, redisTemplate
								.getStringSerializer().serialize(authcode));
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zoo.youshang.cache.MemberProfileCacher#getAucthCode(java.lang.String)
	 */
	@Override
	public String getAucthCode(final String mobile) {
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(
						USER_MOBILE_KEY_PREFIX + mobile);
				byte[] value = connection.get(key);
				return value == null ? null : redisTemplate
						.getStringSerializer().deserialize(value);
				// if (connection.exists(key)) {
				//
				// }
				// return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zoo.youshang.cache.MemberProfileCacher#deleteAucthCode(java.lang.
	 * String)
	 */
	@Override
	public Long deleteAucthCode(final String mobile) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(
						USER_MOBILE_KEY_PREFIX + mobile);
				return connection.del(key);
			}
		});
	}
}
