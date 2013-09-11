/**
 * 
 */
package com.zoo.youshang.api.protocol;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zoo.youshang.api.data.ServiceEntity;

/**
 * @author sunpeng
 * 
 */
@Provider
@Priority(Priorities.ENTITY_CODER)
public class ResolveResultInterceptor implements ContainerResponseFilter {

	private static final Logger logger = LoggerFactory
			.getLogger(ResolveResultInterceptor.class);


	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		Object entity = responseContext.getEntity();
		if (entity != null) {
			logger.debug("......start ResolveResultInterceptor.......entity:"+ entity.getClass());
			if(entity instanceof StreamingOutput){
				return ;
			}
			if (!(entity instanceof ServiceEntity)) {
				ServiceEntity serviceEntity = new ServiceEntity(entity);
				responseContext.setEntity(serviceEntity);
			}
		} else {
			entity = new ServiceEntity();
			responseContext.setStatus(200);
			responseContext.setEntity(entity, responseContext.getEntityAnnotations(), MediaType.APPLICATION_JSON_TYPE);
			
		}
		logger.debug("......start ResolveResultInterceptor.......entity type:"+ entity.getClass());

	}

}
