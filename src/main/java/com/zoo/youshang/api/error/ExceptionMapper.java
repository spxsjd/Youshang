/**
 * 
 */
package com.zoo.youshang.api.error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zoo.youshang.api.data.Codes;
import com.zoo.youshang.api.data.ServiceEntity;

/**
 * @author sunpeng.peng
 * 
 */
@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Throwable> {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionMapper.class);

	@Override
	
	public Response toResponse(Throwable exception) {
		ServerResponse response = new ServerResponse();
		logger.error("Service exception[type:" + exception.getClass() + ",message:" + exception.getMessage() + "]");
		if (exception instanceof ServiceBizException) {
			response.setEntity(new ServiceEntity(((ServiceBizException) exception).getServiceCode()));
		} else {
			response.setEntity(new ServiceEntity(Codes.UnkownError));
		}
		response.setStatus(400);
		return response;
	}

}
