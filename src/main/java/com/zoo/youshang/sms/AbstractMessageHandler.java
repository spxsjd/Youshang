/**
 * 
 */
package com.zoo.youshang.sms;

import java.io.Serializable;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.sp.sms.schedule.ShortMessage;

/**
 * @author sunpeng
 * 
 */
public abstract class AbstractMessageHandler implements InitializingBean {

	public static final String jedisQueueKey = "sms_messages";

	@Autowired
	protected RedisTemplate<Serializable, Serializable> redisTemplate;
	protected RedisSerializer<ShortMessage> messageSerializer =new MessageSerializer();
	protected byte[] messageKey;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.messageKey = redisTemplate.getStringSerializer().serialize(
				jedisQueueKey);
	}

}
