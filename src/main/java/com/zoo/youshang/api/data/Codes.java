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
			-100, "Member not found"), MemberIdentityHasExist(-110,
			"Mobile or Weibo has exist"), MemberPasswordError(-120,
			"Requested password error"), MobileAuthCodeNotFound(-130,
			"Mobile authcode not found"), MobileAuthCodeError(-140,
					"Mobile authcode error"), AvatorTypeInvalid(-150,
							"Avator type invalid"), AvatorSaveFailure(-160,
									"Avator save failure"),AvatorNotExist(-160,
											"Avator not found"),AvatorNotUpload(-170,
													"Not upload the avator file");

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
