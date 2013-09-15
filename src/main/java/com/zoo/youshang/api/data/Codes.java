/**
 * 
 */
package com.zoo.youshang.api.data;

/**
 * @author sunpeng
 * 
 */

public enum Codes implements ServiceCode {

	Successful(200, "Successful"), UnkownError(-1, "System Error"), RequestParamInvalid(
			-10, "Request parameter invalid"), MemberNotFound(-100,
			"Member not found"), MemberIdentityHasExist(-110,
			"Mobile or Weibo has exist"), MemberPasswordError(-120,
			"Requested password error"), MobileAuthCodeNotFound(-130,
			"Mobile authcode not found"), MobileAuthCodeError(-140,
			"Mobile authcode error"), AvatorTypeInvalid(-150,
			"Avator type invalid"), AvatorSaveFailure(-160,
			"Avator save failure"), AvatorNotExist(-180, "Avator not found"), AvatorNotUpload(
			-170, "Not upload the avator file"), MobileOkButMemberNotFound(-190, "Mobile valid, But Member not found"), TaskNotFound(-220,
			"Task not found"), TaskMediumTypeInvalid(-270,
			"Task medium file type invalid"), TaskMediumSaveFailure(-280,
			"Task medium file save failure"), TaskHasBeenAdopted(-250,
			"Task has bean adopted"), TaskStatusNotSupportAction(-230,
					"Task status not support action");

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
