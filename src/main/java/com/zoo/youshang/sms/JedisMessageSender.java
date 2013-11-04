/**
 * 
 */
package com.zoo.youshang.sms;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;

import com.sp.sms.schedule.ShortMessage;

/**
 * @author sunpeng
 * 
 */
//@Component("smsMessageSender")
public class JedisMessageSender extends AbstractMessageHandler implements
		MessageSender {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zoo.youshang.sms.MessageSender#send(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void send(String content, String target) {
		final ShortMessage shortMessage = new ShortMessage();
		shortMessage.setTargets(new String[] { target });
		shortMessage.setContent(content);

		redisTemplate.execute(new RedisCallback<Void>() {
			@Override
			public Void doInRedis(RedisConnection connection)
					throws DataAccessException {
				redisTemplate.getValueSerializer();
				byte[] value = messageSerializer.serialize(shortMessage);
				connection.lPush(messageKey, value);
				return null;
			}
		});
	}

}
