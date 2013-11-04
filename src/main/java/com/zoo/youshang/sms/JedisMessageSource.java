/**
 * 
 */
package com.zoo.youshang.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;

import com.sp.sms.schedule.MessageSource;
import com.sp.sms.schedule.ShortMessage;

/**
 * @author sunpeng
 * 
 */
//@Component("smsMessageSource")
public class JedisMessageSource extends AbstractMessageHandler implements
		MessageSource {
	private static final Logger logger = LoggerFactory
			.getLogger(JedisMessageSource.class);
	private RedisCallback<ShortMessage> redisCallback;

	public JedisMessageSource() {
		logger.info("Create JedisMessageSource.....");
		this.redisCallback = new RedisCallback<ShortMessage>() {
			@Override
			public ShortMessage doInRedis(RedisConnection connection)
					throws DataAccessException {

				byte[] value = connection.lPop(messageKey);
				return value == null ? null : messageSerializer
						.deserialize(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sp.sms.schedule.MessageSource#close()
	 */
	@Override
	public void close() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sp.sms.schedule.MessageSource#get()
	 */
	@Override
	public ShortMessage get() {
		return redisTemplate.execute(this.redisCallback);
	}

}
