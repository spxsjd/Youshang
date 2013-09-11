/**
 * 
 */
package com.zoo.youshang.api.protocol;

import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.zoo.youshang.api.data.ServiceEntity;

/**
 * @author sunpeng.peng
 * 
 */
@SuppressWarnings("deprecation")
@Provider
@ServerInterceptor
public class InvocationPostInterceptor implements PostProcessInterceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(InvocationPostInterceptor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.resteasy.spi.interception.PostProcessInterceptor#postProcess
	 * (org.jboss.resteasy.core.ServerResponse)
	 */
	@Override
	public void postProcess(ServerResponse response) {
		logger.debug("......start InvocationPostInterceptor.......");
		try {
			Object entity = response.getEntity();
			if (entity != null) {
				logger.debug("......start InvocationLogInterceptor.......entity type:"
						+ entity.getClass());
				if (!(entity instanceof ServiceEntity)) {
					ServiceEntity serviceEntity = new ServiceEntity(entity);
					response.setEntity(serviceEntity);
				}
			} else {
				ServiceEntity serviceEntity = new ServiceEntity();
				response.setEntity(serviceEntity);
				response.setStatus(200);
			}
		} catch (Exception e) {
			logger.error("Handle the resule error......", e);
			// InvocationLogger.getInstance().log(response);
		} finally {
			MDC.clear();
			// ServiceContext.remove();
		}
	}

}
