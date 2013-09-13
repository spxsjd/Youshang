package com.zoo.youshang.api.injection;

import com.zoo.youshang.api.data.Codes;
import com.zoo.youshang.api.data.ServiceCode;
import com.zoo.youshang.api.error.AbstractServiceException;

/**
 * 
 * @author sunpeng
 *
 */
public class FieldInjectionException extends AbstractServiceException {
	private static final long serialVersionUID = 4641607837687405354L;
	private Class<?> type;
	private String value;

	public FieldInjectionException(Class<?> type, String value) {
		this.type = type;
		this.value = value;
	}


	public FieldInjectionException(Class<?> type, String value,Exception e) {
		super(e);
		this.type = type;
		this.value = value;
	}
	
	@Override
	public String getMessage() {
		return "The value (" + this.value + ")  is not a valid " + type.getName() + " type.";
	}


	@Override
	public ServiceCode getServiceCode() {
		return Codes.RequestParamInvalid;
	}


}
