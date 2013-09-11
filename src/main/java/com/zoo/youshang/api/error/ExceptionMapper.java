/**
 * 
 */
package com.zoo.youshang.api.error;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zoo.youshang.api.data.Codes;
import com.zoo.youshang.api.data.ServiceEntity;

/**
 * @author sunpeng.peng
 * 
 */
@Provider
public class ExceptionMapper implements
		javax.ws.rs.ext.ExceptionMapper<Throwable> {

	private static final Logger logger = LoggerFactory
			.getLogger(ExceptionMapper.class);

	@Override
	public Response toResponse(Throwable exception) {
		ServiceEntity entity;
		if (exception instanceof ServiceBizException) {
			entity = new ServiceEntity(
					((ServiceBizException) exception).getServiceCode());
		} else {
			logger.error("Service exception[type => " + exception.getClass()
					+ ", message => " + exception.getMessage() + "]: ",
					exception);
			entity = new ServiceEntity(Codes.UnkownError);
		}
		return Response.status(Status.BAD_REQUEST).entity(entity)
				.type(MediaType.APPLICATION_JSON).build();
	}

}
