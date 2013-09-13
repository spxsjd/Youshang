/**
 * 
 */
package com.zoo.youshang.api.error;

import com.zoo.youshang.api.data.ServiceCode;

/**
 * @author sunpeng
 * 
 */
public class ServiceBizException extends AbstractServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7182562075829805331L;
	private ServiceCode serviceCode;

	public ServiceBizException(ServiceCode code) {
		this.serviceCode = code;

	}

	@Override
	public String getMessage() {
		return "The Service business error: code => "
				+ this.serviceCode.getCode() + ", message => "
				+ this.serviceCode.getMessage() + ".";
	}

	public ServiceCode getServiceCode() {
		return serviceCode;
	}

}
