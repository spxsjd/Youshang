/**
 * 
 */
package com.zoo.youshang.api.error;

import com.zoo.youshang.api.data.ServiceCode;

/**
 * @author sunpeng
 * 
 */
public abstract class AbstractServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4180086556123384790L;

	public AbstractServiceException() {
	}

	public AbstractServiceException(Throwable t) {
		super(t);
	}

	public abstract ServiceCode getServiceCode();
}
