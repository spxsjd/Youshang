/**
 * 
 */
package com.zoo.youshang.sms;

import java.io.UnsupportedEncodingException;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.sp.sms.schedule.ShortMessage;

/**
 * @author sunpeng
 * 
 */
public class MessageSerializer implements RedisSerializer<ShortMessage> {

	@Override
	public byte[] serialize(ShortMessage t) throws SerializationException {
		StringBuilder builder = new StringBuilder();
		String[] targets = t.getTargets();
		int i = 0, len = targets.length;
		for (; i < len; i++) {
			if (i > 0) {
				builder.append(',');
			}
			builder.append(targets[i]);
		}
		builder.append(':');
		builder.append(t.getContent());
		try {
			return builder.toString().getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new SerializationException("Get bytes error.", e);
		}
	}

	@Override
	public ShortMessage deserialize(byte[] bytes) throws SerializationException {
		String message = null;
		try {
			message = new String(bytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new SerializationException("Create the message error.", e);
		}
		String[] messageParts = message.split(":", 1);
		ShortMessage shortMessage = new ShortMessage();
		shortMessage.setTargets(messageParts[0].split(","));
		shortMessage.setContent(messageParts[1]);
		return shortMessage;
	}

}
