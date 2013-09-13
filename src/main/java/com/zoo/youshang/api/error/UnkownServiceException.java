/**
 * 
 */
package com.zoo.youshang.api.error;

import com.zoo.youshang.api.data.Codes;
import com.zoo.youshang.api.data.ServiceCode;


/**
 * @author sunpeng
 * 
 */
public class UnkownServiceException extends AbstractServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7182562075829805331L;

	public UnkownServiceException(Exception e) {
		super(e);

	}

	@Override
	public String getMessage() {
		return "The Service unkown error: "+ super.getMessage();
	}

	@Override
	public ServiceCode getServiceCode() {
		return Codes.UnkownError;
	}

}
