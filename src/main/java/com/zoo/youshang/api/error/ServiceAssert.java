/**
 * 
 */
package com.zoo.youshang.api.error;

import com.zoo.youshang.api.data.ServiceCode;

/**
 * @author sunpeng
 * 
 */
public abstract class ServiceAssert {

	public static void notNull(Object obj, ServiceCode code) {
		if (obj == null) {
			throw new ServiceBizException(code);
		}
	}
}
