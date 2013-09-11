/**
 * 
 */
package com.zoo.youshang.api.error;


/**
 * @author sunpeng
 * 
 */
public class UnkownServiceException extends RuntimeException {

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

}
