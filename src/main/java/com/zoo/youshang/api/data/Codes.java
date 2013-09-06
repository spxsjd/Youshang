/**
 * 
 */
package com.zoo.youshang.api.data;

/**
 * @author sunpeng
 * 
 */

public enum Codes implements ServiceCode {

	Successful(200, "Successful"), UnkownError(-1, "System Error"), MemberNotFound(
			-100, "Member not found");

	private Integer code;
	private String message;

	private Codes(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
