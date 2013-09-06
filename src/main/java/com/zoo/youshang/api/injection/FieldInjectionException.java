package com.zoo.youshang.api.injection;

public class FieldInjectionException extends RuntimeException {
	private static final long serialVersionUID = 4641607837687405354L;
	private String field;
	private String type;
	private String value;

	public FieldInjectionException(String field, String type, String value) {
		this.field = field;
		this.type = type;
		this.value = value;
	}

	@Override
	public String getMessage() {
		return "The value (" + this.value + ") for Field " + field + " is not a valid " + type + " type.";
	}


}
